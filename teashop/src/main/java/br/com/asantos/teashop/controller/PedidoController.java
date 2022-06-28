package br.com.asantos.teashop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import br.com.asantos.teashop.common.ApiResponse;
import br.com.asantos.teashop.dto.CupomDto;
import br.com.asantos.teashop.dto.pedido.ItemCheckoutDto;
import br.com.asantos.teashop.dto.pedido.ItemPedidoDto;
import br.com.asantos.teashop.dto.pedido.StripeResponse;
import br.com.asantos.teashop.dto.pedido.TrocaItemPedidoDto;
import br.com.asantos.teashop.exceptions.AuthenticationFailException;
import br.com.asantos.teashop.exceptions.PedidoNotFoundException;
import br.com.asantos.teashop.exceptions.ProdutoNotExistException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.Pedido;
import br.com.asantos.teashop.service.CupomService;
import br.com.asantos.teashop.service.PedidoService;
import br.com.asantos.teashop.service.TrocaItemPedidoService;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;

/**
 * Classe responsável por orquestrar os pedidos de compra do cliente a partir
 * dos itens adicionados ao carrinho, cria um pedido em sessão de checkout
 * passando o pagamento para o serviço de Pagamentos Stripe
 * 
 * @author Aline S
 *
 */
@RestController
@RequestMapping("/pedido")
public class PedidoController {

	// refatorar: usar construtor que recebe os services

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private AutenticacaoService autenticacaoService;
	

	
	@Autowired
	private TrocaItemPedidoService trocaItemPedidoService;
	
	@Autowired
	private CupomService cupomService;

	/**
	 * Cria uma sessão de pagamentos via Stripe
	 * @param listaItemCheckoutDto
	 * @return
	 * @throws StripeException
	 */
	@PostMapping("/sessao-checkout")
	public ResponseEntity<StripeResponse> listaItemsDtoParaCheckout(
			@RequestBody List<ItemCheckoutDto> listaItemCheckoutDto) throws StripeException {
		Session sessao = pedidoService.criaSessao(listaItemCheckoutDto);
		StripeResponse stripeResponse = new StripeResponse(sessao.getId());
		return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
	}

	
	
	
	
	
	/**
	 * 
	 * @param token
	 * @param sessaoId
	 * @return
	 * @throws ProdutoNotExistException
	 * @throws AuthenticationFailException
	 */
		
	//------------
	/**
	 * Cria um novo pedido com base nos items do carrinho do Cliente
	 * @param token
	 * @param sessaoId
	 * @return
	 * @throws ProdutoNotExistException
	 * @throws AuthenticationFailException
	 */
	@PostMapping("/novo")
	public ResponseEntity<ApiResponse> criaPedido(@RequestParam("token") String token,
			@RequestParam("sessaoId") String sessaoId) throws ProdutoNotExistException, AuthenticationFailException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

		pedidoService.criaPedido(cliente, sessaoId);
		return new ResponseEntity<>(new ApiResponse(true, "Pedido criado com sucesso"), HttpStatus.CREATED);
	}
	
	@PostMapping("/novo-pedido")
	public ResponseEntity<ApiResponse> criaPedido(@RequestParam("token") String token) throws ProdutoNotExistException, AuthenticationFailException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

		pedidoService.criaNovoPedido(cliente);
		return new ResponseEntity<>(new ApiResponse(true, "Pedido criado com sucesso"), HttpStatus.CREATED);
	}
	
	
	

	/**
	 * Busca e exibe todos os pedidos do Cliente
	 * @param token
	 * @return
	 * @throws AuthenticationFailException
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<Pedido>> listaTodosPedidos(@RequestParam("token") String token)
			throws AuthenticationFailException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

		List<Pedido> listaPedidos = pedidoService.listaPedidos(cliente);

		return new ResponseEntity<>(listaPedidos, HttpStatus.OK);
	}

	@GetMapping("/novo")
	public ResponseEntity<Pedido> buscaNovoPedido(@RequestParam("token") String token)
			throws AuthenticationFailException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

		Pedido pedido = pedidoService.buscaNovoPedido(cliente);

		return new ResponseEntity<Pedido>(pedido, HttpStatus.OK);
	}
	
	
	/**
	 * Busca e exibe um pedido do Cliente
	 * @param id
	 * @param token
	 * @return
	 * @throws AuthenticationFailException
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> listaPedido(@PathVariable("id") Long id, @RequestParam("token") String token)
			throws AuthenticationFailException {
		autenticacaoService.autenticaToken(token);
		try {
			Pedido pedido = pedidoService.buscaPedido(id);
			return new ResponseEntity<>(pedido, HttpStatus.OK);
		} catch (PedidoNotFoundException erro) {
			return new ResponseEntity<>(erro.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping("/troca/{id}")
	public ResponseEntity<ItemPedidoDto> exibeItemPedido(@PathVariable("id") Long id, @RequestParam("token") String token)
	throws AuthenticationFailException{
		//cliente que solicita
		@SuppressWarnings("unused")
		Cliente cliente = autenticacaoService.getCliente(token);
		
		ItemPedidoDto itemPedidoDto =  trocaItemPedidoService.buscaItemPedido(id);
		
		
		return new ResponseEntity<ItemPedidoDto>(itemPedidoDto, HttpStatus.OK);
	}
	
	
	@PostMapping("/troca/{id}")
	public ResponseEntity<TrocaItemPedidoDto> trocaItemPedido(@PathVariable("id") Long id, @RequestParam("token") String token, @RequestBody TrocaItemPedidoDto trocaDto)
	throws AuthenticationFailException{
		//cliente que solicita
		Cliente cliente = autenticacaoService.getCliente(token);
				
		trocaItemPedidoService.criaTroca(id, cliente, trocaDto);
		System.out.println(trocaDto.getMotivo());
		
		return new ResponseEntity<TrocaItemPedidoDto>(HttpStatus.CREATED);
	}
	
	@GetMapping("/trocas")
	public ResponseEntity<List<TrocaItemPedidoDto>> exibirItemParaTroca(){
		
		List<TrocaItemPedidoDto> trocaItemPedidoDto = trocaItemPedidoService.listaDeItemParaTroca();
		
		
		return new ResponseEntity<List<TrocaItemPedidoDto>>(trocaItemPedidoDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/cliente/trocas")
	public ResponseEntity<List<TrocaItemPedidoDto>> exibirTrocasDoCliente(@RequestParam("token") String token){
		Cliente cliente = autenticacaoService.getCliente(token);
		List<TrocaItemPedidoDto> trocaItemPedidoDto = trocaItemPedidoService.listaDeTrocasDoCliente(cliente);
		
		return new ResponseEntity<List<TrocaItemPedidoDto>>(trocaItemPedidoDto, HttpStatus.OK);
		
	}
	
	@GetMapping("/cliente/cupons")
	ResponseEntity<List<CupomDto>> exibirCuponsDoCliente(@RequestParam("token") String token){
		Cliente cliente = autenticacaoService.getCliente(token);
	
		List<CupomDto> cupomDto = cupomService.listaCuponsDoCliente(cliente);
		
		
		return new ResponseEntity<List<CupomDto>>(cupomDto, HttpStatus.OK);
		
	}
}


