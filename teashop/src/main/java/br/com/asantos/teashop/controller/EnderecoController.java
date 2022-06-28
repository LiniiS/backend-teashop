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
import br.com.asantos.teashop.dto.cliente.EnderecoDto;
import br.com.asantos.teashop.exceptions.AuthenticationFailException;
import br.com.asantos.teashop.exceptions.ClienteNotExistException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;
import br.com.asantos.teashop.service.cliente.ClienteService;
import br.com.asantos.teashop.service.cliente.EnderecoService;

/**
 * Essa classe é responsável pelo mapeamento das rotas associadas ao endereço do
 * cliente cadastrado, considera o cliente logado e verifica por meio de token
 * 
 * @author Aline Santos
 *
 */

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private AutenticacaoService autenticacaoService;

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/lista")
	public ResponseEntity<List<EnderecoDto>> buscaEnderecos(@RequestParam("token") String token) {
		Long clienteId = autenticacaoService.getCliente(token).getId();
		List<EnderecoDto> body = enderecoService.buscaEnderecos(clienteId);
		return new ResponseEntity<List<EnderecoDto>>(body, HttpStatus.OK);

	}

	@PutMapping("/atualiza/{enderecoId}")
	public ResponseEntity<ApiResponse> atualizaEndereco(@PathVariable("enderecoId") Long enderecoId,
			@RequestBody @Valid EnderecoDto enderecoDto, @RequestParam("token") String token)
			throws AuthenticationFailException {
		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);
		enderecoService.atualizaEndereco(enderecoId, enderecoDto, cliente);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Dados do endereço foram atualizados."),
				HttpStatus.OK);
	}

	// !TODO considerar uma verificação de usuário logado
	@DeleteMapping("/deleta/{enderecoId}")
	public ResponseEntity<ApiResponse> deletaEndereco(@PathVariable("enderecoId") Long enderecoId) {
		enderecoService.deletaEndereco(enderecoId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Endereco removido."), HttpStatus.OK);
	}

	/**
	 * Endpoint para teste de cadastro de endereço baseado no Token e não no
	 * ClienteId
	 * 
	 * @param enderecoDto
	 * @param token
	 * @return
	 * @throws AuthenticationFailException
	 * @throws ClienteNotExistException
	 */

	@PostMapping("/adiciona")
	public ResponseEntity<ApiResponse> adiciona(@Valid @RequestBody EnderecoDto enderecoDto,
			@RequestParam("token") String token) throws AuthenticationFailException, ClienteNotExistException {
		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

		enderecoService.adicionaEndereco(enderecoDto, cliente);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Novo endereco adicionado"), HttpStatus.CREATED);
	}

	@PostMapping("/adicionaEndereco")
	public ResponseEntity<ApiResponse> adicionaEndereco(@Valid @RequestBody EnderecoDto enderecoDto,
			@RequestParam("token") String token) throws AuthenticationFailException, ClienteNotExistException {
		autenticacaoService.autenticaToken(token);
		// !TODO verificar essa autenticacao
		Optional<Cliente> optionalCliente = clienteService.buscaCliente(autenticacaoService.getCliente(token).getId());
		if (!optionalCliente.isPresent()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Cliente não encontrado"),
					HttpStatus.BAD_REQUEST);
		}

		Cliente cliente = optionalCliente.get();
		enderecoService.adicionaEndereco(enderecoDto, cliente);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Novo endereco adicionado"), HttpStatus.CREATED);
	}

}
