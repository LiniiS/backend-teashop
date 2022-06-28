package br.com.asantos.teashop.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.pedido.ItemPedidoDto;
import br.com.asantos.teashop.dto.pedido.TrocaItemPedidoDto;
import br.com.asantos.teashop.exceptions.CustomException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.ItemPedido;
import br.com.asantos.teashop.model.pedido.Pedido;
import br.com.asantos.teashop.model.pedido.TrocaItemPedido;
import br.com.asantos.teashop.model.pedido.situacao.estados.Estado;
import br.com.asantos.teashop.repository.ItemPedidoRepository;
import br.com.asantos.teashop.repository.PedidoRepository;
import br.com.asantos.teashop.repository.TrocaItemPedidoRepository;

@Service
public class TrocaItemPedidoService {

	@Autowired
	private TrocaItemPedidoRepository trocaItemPedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	/**
	 * Gera um pedido de troca referente a um item de um dos pedidos do cliente ->
	 * não altera o status do pedido -> bug ao dar get se solicitar troca mais de
	 * uma vez para o mesmo pedido
	 * 
	 * @param itemId
	 * @param cliente
	 * @param trocaDto
	 * @return
	 */
	@Transactional
	public TrocaItemPedidoDto criaTroca(Long itemId, Cliente cliente, TrocaItemPedidoDto trocaDto) {
		@SuppressWarnings("deprecation")
		Pedido pedido = pedidoRepository.getById(trocaDto.getPedidoId());
		System.out.println(trocaDto.getMotivo());

		ItemPedido itemPedido = itemPedidoRepository.getOne(itemId);

		TrocaItemPedido trocaItemPedido = new TrocaItemPedido();
		trocaItemPedido.setItemPedido(itemPedido);
		trocaItemPedido.setCliente(cliente);
		trocaItemPedido.setMotivo(trocaDto.getMotivo());
		System.out.println(trocaDto.getMotivo());
		trocaItemPedido.setPedidoId(trocaDto.getPedidoId());
		trocaItemPedido.setStatus("TROCA_SOLICITADA");

		trocaItemPedido = trocaItemPedidoRepository.save(trocaItemPedido);
		pedido = pedidoRepository.save(pedido);

		return new TrocaItemPedidoDto(trocaItemPedido);
	}

	public ItemPedidoDto buscaItemPedido(Long id) {
		ItemPedido itemPedido = new ItemPedido();
		System.out.println(itemPedido.getStatus());
//		if(itemPedido.getStatus().equals("EM_ANALISE")) {
//			throw new CustomException("Troca solicitada para o item");
//		}
		itemPedido = itemPedidoRepository.getOne(id);
		if (itemPedido.getStatus() == (Estado.EM_TROCA)) {
			throw new CustomException("Troca solicitada para o item");
		}
		return new ItemPedidoDto(itemPedido);

	}

	public List<TrocaItemPedidoDto> listaDeItemParaTroca() {
		List<TrocaItemPedido> itemsParaTroca = trocaItemPedidoRepository.findAll();
		List<TrocaItemPedidoDto> itemsParaTrocaDtos = new ArrayList<TrocaItemPedidoDto>();
		for (TrocaItemPedido itemParaTroca : itemsParaTroca) {

			itemsParaTrocaDtos.add(new TrocaItemPedidoDto(itemParaTroca));
		}

		return itemsParaTrocaDtos;
	}

	public TrocaItemPedidoDto buscaItemDeTrocaAutorizada(Long itemTrocaId) {
		// o id que chega é o id do item dentro do pedido
		TrocaItemPedido itemParaTroca = trocaItemPedidoRepository.getByIdOrderByIdDesc(itemTrocaId);

		return new TrocaItemPedidoDto(itemParaTroca);
	}

	public List<TrocaItemPedidoDto> listaDeTrocasDoCliente(Cliente cliente) {
		List<TrocaItemPedido> itemsTrocaDoCliente = trocaItemPedidoRepository.findByClienteOrderByIdDesc(cliente);

		List<TrocaItemPedidoDto> itemsTrocaDoClienteDto = new ArrayList<TrocaItemPedidoDto>();
		for (TrocaItemPedido trocaDoCliente : itemsTrocaDoCliente) {

			itemsTrocaDoClienteDto.add(new TrocaItemPedidoDto(trocaDoCliente));

		}

		return itemsTrocaDoClienteDto;
	}
}