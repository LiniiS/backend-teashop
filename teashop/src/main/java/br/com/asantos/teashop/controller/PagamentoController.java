package br.com.asantos.teashop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.asantos.teashop.dto.PagamentoDto;
import br.com.asantos.teashop.exceptions.AuthenticationFailException;
import br.com.asantos.teashop.exceptions.ProdutoNotExistException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.service.PagamentoService;
import br.com.asantos.teashop.service.PedidoService;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private AutenticacaoService autenticacaoService;

	@SuppressWarnings("unused")
	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PagamentoService pagamentoService;

	@PostMapping("/novo")
	public ResponseEntity<PagamentoDto> novoPedido(@RequestParam("token") String token,
			@RequestBody PagamentoDto pagamentoDto) throws  AuthenticationFailException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

		// pedidoService.criaNovoPedido(cliente);
		 pagamentoService.efetuaPagamento(pagamentoDto, cliente);

		return new ResponseEntity<PagamentoDto>(pagamentoDto, HttpStatus.CREATED);

	}
	
	@GetMapping("/todos")
	public ResponseEntity<List<PagamentoDto>> listaPagamentos(@RequestParam("token") String token)throws  AuthenticationFailException {

		autenticacaoService.autenticaToken(token);
		Cliente cliente = autenticacaoService.getCliente(token);

	
		List<PagamentoDto> listaPagamentosDto = pagamentoService.buscaPagamentos(cliente);

		return new ResponseEntity<List<PagamentoDto>>(listaPagamentosDto, HttpStatus.OK);
	}
}
