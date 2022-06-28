package br.com.asantos.teashop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.asantos.teashop.common.ApiResponse;
import br.com.asantos.teashop.dto.cliente.ClienteDto;
import br.com.asantos.teashop.dto.cliente.ClienteSenhaDto;
import br.com.asantos.teashop.dto.cliente.ResponseDto;
import br.com.asantos.teashop.dto.cliente.SignInDto;
import br.com.asantos.teashop.dto.cliente.SignInResponseDto;
import br.com.asantos.teashop.dto.cliente.SignUpDto;
import br.com.asantos.teashop.exceptions.CustomException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.repository.cliente.ClienteRepository;
import br.com.asantos.teashop.service.CupomService;
import br.com.asantos.teashop.service.TrocaItemPedidoService;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;
import br.com.asantos.teashop.service.cliente.ClienteService;

/**
 * Esta classe faz o mapeamento para as rotas associadas ao Cliente permite o
 * cadastro do cliente (sign up), permite o login (sign in) permite o acesso à
 * rota de atualizar o cliente
 * 
 * @author Aline Santos
 *
 */

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@Autowired
	AutenticacaoService autenticacaoService;

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	TrocaItemPedidoService trocaItemPedidoService;
	
	@Autowired
	CupomService cupomService;
	

	/**
	 * endpoint referente aos novos clientes, recebe os valores do formulario via
	 * corpo da requisição por meio do DTO e popula um Cliente
	 * 
	 * @param signUpDto
	 * @return
	 * @throws CustomException
	 */
	@PostMapping("/signup")
	public ResponseDto signUp(@Valid @RequestBody SignUpDto signUpDto) throws CustomException {
		return clienteService.signUp(signUpDto);
	}

	/**
	 * endpoint referente aos clientes já cadastrados que desejam fazer login os
	 * dados de login são recebidos via corpo de requisição e passados por meio do
	 * DTO onde é verificado se o o login é valido ou não, com exception customizada
	 * 
	 * @param signInDto
	 * @return
	 * @throws CustomException
	 */
	@PostMapping("/signin")
	public SignInResponseDto signIn(@Valid @RequestBody SignInDto signInDto) throws CustomException {
		return clienteService.signIn(signInDto);
	}

	/**
	 * endpoint referente a atualização dos dados cadastrais, não considerando o
	 * Endereço, Cartão, Pedido.
	 * 
	 * @param clienteId
	 * @param clienteDto
	 * @return
	 */
	@PutMapping("/atualiza/{clienteId}")
	public ResponseEntity<ApiResponse> atualizaCliente(@PathVariable("clienteId") Long clienteId,
			@RequestBody @Valid ClienteDto clienteDto) {
		// atualiza os dados cadastrais do cliente
		clienteService.atualizaCliente(clienteId, clienteDto);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Dados principais atualizados!"), HttpStatus.OK);
	}

	/**
	 * endpoint para atualizar apenas a senha do cliente
	 * TODO verificar bug com hash da senha atualizada
	 * @param token
	 * @param novaSenha
	 * @return
	 * @throws CustomException
	 */
	@PutMapping("/atualiza/senha/{token}")
	public ResponseEntity<ApiResponse> atualizaSenhaCliente(@RequestParam("token") String token, @RequestBody ClienteSenhaDto clienteDto)
			throws CustomException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);
		
		clienteService.atualizaSenhaCliente(cliente, clienteDto);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Senha atualizada!"), HttpStatus.OK);
	}

	@GetMapping("/dados{token}")
	public ResponseEntity<ClienteDto> buscaDadosClienteLogado(@RequestParam("token") String token) {
		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);
		ClienteDto clienteDto =  clienteService.buscaDadosClienteLogado(cliente);
		return new ResponseEntity<ClienteDto>(clienteDto, HttpStatus.OK);
	}
	
	
}
