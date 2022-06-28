package br.com.asantos.teashop.controller;

import java.util.List;
import java.util.Optional;

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
import br.com.asantos.teashop.dto.cliente.CartaoDto;
import br.com.asantos.teashop.exceptions.AuthenticationFailException;
import br.com.asantos.teashop.exceptions.ClienteNotExistException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;
import br.com.asantos.teashop.service.cliente.CartaoService;
import br.com.asantos.teashop.service.cliente.ClienteService;


/**
 * Essa classe é responsável pelo mapeamento das rotas associadas ao cartão do
 * cliente cadastrado, considera o cliente logado e verifica por meio de token
 * 
 * @author Aline Santos
 *
 */

@RestController
@RequestMapping("/cartao")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;
	@Autowired
	private AutenticacaoService autenticacaoService;
	@Autowired
	private ClienteService clienteService;

	@GetMapping("/lista")
	public ResponseEntity<List<CartaoDto>> buscaCartoes(@RequestParam("token") String token) {
		Long clienteId = autenticacaoService.getCliente(token).getId();
		List<CartaoDto> body = cartaoService.buscaCartoes(clienteId);
		return new ResponseEntity<List<CartaoDto>>(body, HttpStatus.OK);
	}

	@PutMapping("/atualiza/{cartaoId}")
	public ResponseEntity<ApiResponse> atualizaCartao(@PathVariable("cartaoId") Long cartaoId,
			@RequestBody @Valid CartaoDto cartaoDto, @RequestParam("token") String token)
			throws AuthenticationFailException {
		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);
		cartaoService.atualizaCartao(cartaoId, cartaoDto, cliente);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Dados do cartão foram atualizados"),
				HttpStatus.OK);
	}

	@DeleteMapping("/deleta/{cartaoId}")
	public ResponseEntity<ApiResponse> deletaCartao(@PathVariable("cartaoId") Long cartaoId) {
		cartaoService.deletaCartao(cartaoId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Cartao removido"), HttpStatus.OK);
	}

	// TODO verificar a adição de um novo cartão pegando o cliente id do token da
	// sessão
	@PostMapping("/adiciona")
	public ResponseEntity<ApiResponse> adiciona(@Valid @RequestBody CartaoDto cartaoDto,
			@RequestParam("token") String token) throws AuthenticationFailException, ClienteNotExistException {
		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

		cartaoService.adicionaCartao(cartaoDto, cliente);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Novo cartão adicionado"), HttpStatus.CREATED);
	}

	@PostMapping("/adicionaCartao")
	public ResponseEntity<ApiResponse> adicionaCartao(@Valid @RequestBody CartaoDto cartaoDto,
			@RequestParam("token") String token) throws AuthenticationFailException, ClienteNotExistException {

		// verifica se o token passado na requisição é válido (possui um cliente
		// associado)
		autenticacaoService.autenticaToken(token);
		Optional<Cliente> optionalCliente = clienteService.buscaCliente(autenticacaoService.getCliente(token).getId());
		if (!optionalCliente.isPresent()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Cliente não encontrado."),
					HttpStatus.BAD_REQUEST);
		}
		Cliente cliente = optionalCliente.get();
		cartaoService.adicionaCartao(cartaoDto, cliente);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Novo cartão adicionado."), HttpStatus.CREATED);
	}

}
