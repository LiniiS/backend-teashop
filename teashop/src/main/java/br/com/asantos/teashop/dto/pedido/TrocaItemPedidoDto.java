package br.com.asantos.teashop.dto.pedido;

import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.ItemPedido;
import br.com.asantos.teashop.model.pedido.TrocaItemPedido;

public class TrocaItemPedidoDto {

	// id da troca
	private long id;

	private String motivo;

	// id do item do pedido
	private long itemPedidoId;

	private long clienteId;

	private String status;

	// id do pedido que contém o item para troca
	private long pedidoId;

	public TrocaItemPedidoDto() {
	}

	public TrocaItemPedidoDto(String motivo, ItemPedido itemPedido, Cliente cliente, String status, long pedidoId) {
		this.motivo = motivo;
		this.itemPedidoId = itemPedido.getId();
		this.clienteId = cliente.getId();
		this.status = status;
		this.pedidoId = pedidoId;

	}

	public TrocaItemPedidoDto(TrocaItemPedido trocaItemPedido) {
		this.id = trocaItemPedido.getId();
		this.motivo = trocaItemPedido.getMotivo();
		this.clienteId = trocaItemPedido.getCliente().getId();
		this.status = trocaItemPedido.getStatus();
		this.itemPedidoId = trocaItemPedido.getItemPedido().getId();
		this.pedidoId = trocaItemPedido.getPedidoId();

	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public long getItemPedidoId() {
		return itemPedidoId;
	}

	public void setItemPedidoId(long itemPedidoId) {
		this.itemPedidoId = itemPedidoId;
	}

	public long getClienteId() {
		return clienteId;
	}

	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
