package br.com.asantos.teashop.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.pedido.ItemPedidoDto;
import br.com.asantos.teashop.model.pedido.ItemPedido;
import br.com.asantos.teashop.repository.ItemPedidoRepository;

@Service
@Transactional
public class ItemPedidoService {
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public void adicionaProdutos(ItemPedido itemPedido) {
		itemPedidoRepository.save(itemPedido);
	}

	public ItemPedidoDto buscaItem(Long id) {
		ItemPedido item =  itemPedidoRepository.getById(id);
		ItemPedidoDto itemDto = new ItemPedidoDto();
		itemDto.setPedidoId(item.getId());
		itemDto.setProdutoId(item.getQuantidade());
		itemDto.setValor(item.getValor());
		
		return itemDto;
	}

}
