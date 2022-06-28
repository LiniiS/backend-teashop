package br.com.asantos.teashop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.asantos.teashop.common.ApiResponse;
import br.com.asantos.teashop.dto.cliente.ClienteDto;
import br.com.asantos.teashop.dto.pedido.TrocaItemPedidoDto;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.repository.cliente.ClienteRepository;
import br.com.asantos.teashop.service.CupomService;
import br.com.asantos.teashop.service.TrocaItemPedidoService;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;
import br.com.asantos.teashop.service.cliente.ClienteService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
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
	 * endpoint referente a listagem de todos os Clientes cadastrados destinado ao
	 * gerenciamento de clientes (painel do admin)
	 * 
	 * @return
	 */
	@GetMapping("/clientes")
	public ResponseEntity<List<ClienteDto>> getClientes() {
		List<ClienteDto> body = clienteService.listaClientes();
		return new ResponseEntity<List<ClienteDto>>(body, HttpStatus.OK);
	}

	/**
	 * endpoint referente a remoção de um cadastro de cliente
	 * 
	 * @param clienteId
	 * @return
	 */
	@DeleteMapping("/deleta/{clienteId}")
	public ResponseEntity<ApiResponse> deletaCliente(@PathVariable("clienteId") Long clienteId) {
		clienteService.deletaClientePorId(clienteId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Cliente removido."), HttpStatus.OK);
	}

	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<ClienteDto> buscaCliente(@PathVariable("clienteId") Long clienteId) {
		// busca o cliente pelo seu ID

		Optional<Cliente> optionalCliente = clienteService.buscaClienteAdmin(clienteId);

		ClienteDto body = clienteService.exibeCliente(optionalCliente);

		return new ResponseEntity<ClienteDto>(body, HttpStatus.OK);
	}
	
	//admin autorizao troca que cria o cupom
	@PostMapping("/troca/autoriza/{id}")
	public ResponseEntity<ApiResponse> autorizaTroca(@PathVariable("id") Long id){
		
		TrocaItemPedidoDto itemAutorizadoParaTroca = trocaItemPedidoService.buscaItemDeTrocaAutorizada(id);
			
		cupomService.criaCupomAposTrocaDeItem(itemAutorizadoParaTroca);
		
		return new ResponseEntity<>(new ApiResponse(true, "Troca autorizada com sucesso!"), HttpStatus.CREATED);
		
	}
	
	//admin autorizao troca que cria o cupom
	@PostMapping("/troca/rejeita/{id}")
	public ResponseEntity<ApiResponse> rejeitaTroca(@PathVariable("id") Long id){
		
		TrocaItemPedidoDto itemRejeitadoParaTroca = trocaItemPedidoService.buscaItemDeTrocaAutorizada(id);
			
		cupomService.atualizaStatusDoItemEmTroca(itemRejeitadoParaTroca);
		
		return new ResponseEntity<>(new ApiResponse(true, "Troca rejeitada com sucesso!"), HttpStatus.OK);
		
	}

}
