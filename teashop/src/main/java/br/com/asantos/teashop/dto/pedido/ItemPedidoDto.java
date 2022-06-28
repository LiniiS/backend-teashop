package br.com.asantos.teashop.dto.pedido;

import javax.validation.constraints.NotNull;

import br.com.asantos.teashop.model.pedido.ItemPedido;
import br.com.asantos.teashop.model.pedido.situacao.estados.Estado;

public class ItemPedidoDto {

	@NotNull
	private double valor;
	@NotNull
	private int quantidade;
	@NotNull
	private long pedidoId;
	@NotNull
	private long produtoId;

	private Estado pedidoStatus;

	public ItemPedidoDto() {
	}

	public ItemPedidoDto(@NotNull double valor, @NotNull int quantidade, @NotNull long pedidoId,
			@NotNull long produtoId, Estado pedidoStatus) {
		this.valor = valor;
		this.quantidade = quantidade;
		this.pedidoId = pedidoId;
		this.produtoId = produtoId;
		this.pedidoStatus = pedidoStatus;
	}

	
	public ItemPedidoDto(ItemPedido itemPedido) {
		this.setProdutoId(itemPedido.getProduto().getId());
		this.setValor(itemPedido.getValor());
		this.setQuantidade(itemPedido.getQuantidade());
		this.setPedidoId(itemPedido.getPedido().getId());
		this.setPedidoStatus(itemPedido.getStatus());
		
	}
	
	
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(long produtoId) {
		this.produtoId = produtoId;
	}

	public Estado getPedidoStatus() {
		return pedidoStatus;
	}

	public void setPedidoStatus(Estado pedidoStatus) {
		this.pedidoStatus = pedidoStatus;
	}

}
