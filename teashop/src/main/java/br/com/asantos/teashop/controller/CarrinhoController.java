package br.com.asantos.teashop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.asantos.teashop.common.ApiResponse;
import br.com.asantos.teashop.dto.carrinho.AdicionaAoCarrinhoDto;
import br.com.asantos.teashop.dto.carrinho.CarrinhoDto;
import br.com.asantos.teashop.exceptions.AuthenticationFailException;
import br.com.asantos.teashop.exceptions.CarrinhoItemNotExistException;
import br.com.asantos.teashop.exceptions.ProdutoNotExistException;
import br.com.asantos.teashop.model.Produto;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.service.CarrinhoService;
import br.com.asantos.teashop.service.ProdutoService;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;


/**
 * Essa classe é responsável pelo mapeamento das rotas associadas ao carrinho de
 * compras do cliente cadastrado, considera o cliente logado e verifica por meio
 * de token
 * 
 * @author Aline Santos
 *
 */
@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

	@Autowired
	private CarrinhoService carrinhoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private AutenticacaoService autenticacaoService;

	/**
	 * endpoint para adicionar um item ao carrinho de compra do cliente logado a
	 * partir do token do cliente o produto Id e o Usuário ID ficam vinculados ao
	 * carrinho
	 * 
	 * @param adicionaAoCarrinhoDto
	 * @param token
	 * @return
	 */
	@PostMapping("/adiciona")
	public ResponseEntity<ApiResponse> adicionaAoCarrinho(@RequestBody AdicionaAoCarrinhoDto adicionaAoCarrinhoDto,
			@RequestParam("token") String token) throws AuthenticationFailException, ProdutoNotExistException {

		// verifica o cliente pelo token da sessão
		autenticacaoService.autenticaToken(token);

		// traz o cliente dado o token
		Cliente cliente = autenticacaoService.getCliente(token);

		// verifica o produto desejado pelo cliente ao Adicionar procurando pelo seu ID
		Produto produtoAdicionadoAoCarrinho = produtoService.buscaProduto(adicionaAoCarrinhoDto.getProdutoId());

		// adiciona ao carrinho o produto, o cliente da sessão e o dto pra criar o
		// carrinho
		carrinhoService.adicionaAoCarrinho(adicionaAoCarrinhoDto, produtoAdicionadoAoCarrinho, cliente);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Adicionado ao carrinho!"), HttpStatus.CREATED);
	}

	/**
	 * endpoint para listar os itens contidos no carrinho do cliente logado usando o
	 * token para trazer o carrinho a ele associado
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationFailException
	 */
	@GetMapping("/")
	public ResponseEntity<CarrinhoDto> exibeItensDoCarrinho(@RequestParam("token") String token)
			throws AuthenticationFailException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);
		CarrinhoDto carrinhoDto = carrinhoService.listaItensDoCarrinho(cliente);
		return new ResponseEntity<CarrinhoDto>(carrinhoDto, HttpStatus.OK);
	}

	/**
	 * endpoint para atualizar as infos do carrinho quando o produto é alterado
	 * 
	 * @param carrinhoDto
	 * @param token
	 * @return
	 * @throws AuthenticationFailException
	 * @throws ProdutoNotExistException
	 */
	// TODO verificar esse ID que é passado pra vincular o carrinho q ta sendo
	// atualizado
	@PutMapping("/atualiza/{itemCarrinhoId}")
	public ResponseEntity<ApiResponse> atualizaItemCarrinho(@PathVariable("itemCarrinhoId") Long itemCarrinhoId,
			@RequestBody @Valid AdicionaAoCarrinhoDto carrinhoDto, @RequestParam("token") String token)
			throws AuthenticationFailException, ProdutoNotExistException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);
		Produto produto = produtoService.buscaProduto(carrinhoDto.getProdutoId());

		carrinhoService.atualizaItemCarrinho(itemCarrinhoId, carrinhoDto, cliente, produto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto no carrinho atualizado!"), HttpStatus.OK);
	}

	/**
	 * endpoint para remover um item do carrinho do cliente a partir do token o
	 * cliente e o carridnho do id passado é localizado para ter o seu item excluido
	 * 
	 * @param itemCarrinhoId
	 * @param token
	 * @return
	 * @throws AuthenticationFailException
	 * @throws CarrinhoItemNotExistException
	 */
	@DeleteMapping("/deleta/{itemCarrinhoId}")
	public ResponseEntity<ApiResponse> deletaItemCarrinho(@PathVariable("itemCarrinhoId") Long itemCarrinhoId,
			@RequestParam("token") String token) throws AuthenticationFailException, CarrinhoItemNotExistException {
		autenticacaoService.autenticaToken(token);
		Long clienteId = autenticacaoService.getCliente(token).getId();
		carrinhoService.deletaItemCarrinho(itemCarrinhoId, clienteId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item removido!"), HttpStatus.OK);

	}

}
