package br.com.asantos.teashop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.asantos.teashop.common.ApiResponse;
import br.com.asantos.teashop.dto.ProdutoDto;
import br.com.asantos.teashop.model.Produto;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.cliente.ListaDesejo;
import br.com.asantos.teashop.service.ProdutoService;
import br.com.asantos.teashop.service.cliente.AutenticacaoService;
import br.com.asantos.teashop.service.cliente.ListaDesejoService;



@RestController
@RequestMapping("/desejos")
public class ListaDesejoController {

	@Autowired
	private ListaDesejoService lstDesejoService;

	@Autowired
	private AutenticacaoService autenticacaoService;

	/**
	 * endpoint para adicionar um produto à lista de desejos do cliente
	 * @param produto
	 * @param token
	 * @return
	 */
	// salvar o produto na lista de desejo
	@PostMapping("/adiciona")
	public ResponseEntity<ApiResponse> adicionaAListaDesejo(@RequestBody Produto produto,
			@RequestParam("token") String token) {
		// autenticar o token para o usuário se estiver logado
		autenticacaoService.autenticaToken(token);

		// se estiver logado, buscar o usuário daquele token
		Cliente cliente = autenticacaoService.getCliente(token);

		// salvar o item na lista vinculada àquele token
		ListaDesejo lstDesejo = new ListaDesejo(cliente, produto);
		lstDesejoService.criaListaDesejo(lstDesejo);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Adicionado à lista de Desejos"),
				HttpStatus.CREATED);
	}

	/**
	 * endpoint para listar os items da lista de desejos do cliente
	 * @param token
	 * @return
	 */
	// listar todos os items para o usuário
	@GetMapping("/{token}")
	public ResponseEntity<List<ProdutoDto>> buscaListaDesejo(@PathVariable("token") String token) {
		Long clienteId = autenticacaoService.getCliente(token).getId();
		List<ListaDesejo> body = lstDesejoService.leListaDesejoDoCliente(clienteId);
		List<ProdutoDto> produtos = new ArrayList<ProdutoDto>();

		for (ListaDesejo lstDesejo : body) {
			produtos.add(ProdutoService.getDtoDoProduto(lstDesejo.getProduto()));
		}
		return new ResponseEntity<List<ProdutoDto>>(produtos, HttpStatus.OK);

	}

	/**
	 * endpoint para remover o item da lista de desejos
	 * @param produtoId
	 * @return
	 */
	// remover o item da lista de desejos
	@DeleteMapping("deleta/{produtoId}")
	public ResponseEntity<ApiResponse> deletaItem(@PathVariable("produtoId") Long produtoId) {
		lstDesejoService.deletaItemDaLista(produtoId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item removido da lista de desejos"),
				HttpStatus.OK);
	}

}
