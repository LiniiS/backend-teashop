package br.com.asantos.teashop.service.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.ProdutoDto;
import br.com.asantos.teashop.exceptions.ProdutoNotExistException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.cliente.ListaDesejo;
import br.com.asantos.teashop.repository.cliente.ListaDesejoRepository;
import br.com.asantos.teashop.service.ProdutoService;

@Service
@Transactional
public class ListaDesejoService {

	private final ListaDesejoRepository lstDesejoRepository;

	public ListaDesejoService(ListaDesejoRepository lstDesejoRepository) {
		this.lstDesejoRepository = lstDesejoRepository;

	}

	public void criaListaDesejo(ListaDesejo lstDesejo) {
		lstDesejoRepository.save(lstDesejo);

	}

	public List<ListaDesejo> leListaDesejoDoCliente(Long clienteId) {
		return lstDesejoRepository.findAllByClienteIdOrderByDtCriacaoDesc(clienteId);
	}

	public void deletaItemDaLista(Long produtoId) {
		Optional<ListaDesejo> itemLstDesejo = lstDesejoRepository.findById(produtoId);
		if (!itemLstDesejo.isPresent())
			throw new ProdutoNotExistException("Produto inexistente na lista de Desejos");
		lstDesejoRepository.deleteById(produtoId);

	}

	/**
	 * retorna a lista de produtos contidos na lista do Cliente, dado um cliente
	 * refatorado pelo leListaDesejo
	 * 
	 * @param cliente
	 * @return
	 */
	// TODO rever essa implementação, substituida pelo leListaDesejos que recebe o
	// ID e nao um cliente
	public List<ProdutoDto> buscaListaDesejoDoCliente(Cliente cliente) {
		final List<ListaDesejo> lstDesejos = lstDesejoRepository.findAllByClienteOrderByDtCriacaoDesc(cliente);
		List<ProdutoDto> produtoDto = new ArrayList<>();
		for (ListaDesejo lstDesejo : lstDesejos) {
			produtoDto.add(ProdutoService.getDtoDoProduto(lstDesejo.getProduto()));
		}
		return produtoDto;
	}

}
