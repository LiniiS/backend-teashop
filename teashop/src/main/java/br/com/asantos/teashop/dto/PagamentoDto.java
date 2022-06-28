package br.com.asantos.teashop.dto;

import br.com.asantos.teashop.model.Cupom;
import br.com.asantos.teashop.model.Pagamento;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.cliente.cartao.Cartao;
import br.com.asantos.teashop.model.pedido.Pedido;

public class PagamentoDto {

	private Long id;

	private Long clienteId;

	private Long pedidoId;

	private Long cartaoId;

	private double valorTotal;

	private double valorDesconto;

	private Long cupomId;

	public PagamentoDto() {
	}

	public PagamentoDto(Long clienteId, Long pedidoId, Long cartaoId, double valorTotal, double valorDesconto
			) {
		this.clienteId = clienteId;
		this.pedidoId = pedidoId;
		this.cartaoId = cartaoId;
		this.valorTotal = valorTotal;
		this.valorDesconto = valorDesconto;
		
	}

	public PagamentoDto(Pagamento pagamento) {
		this.id = pagamento.getId();
		this.clienteId = pagamento.getId();
		this.cartaoId = pagamento.getId();
		this.valorDesconto = pagamento.getTotalDesconto();
		this.valorTotal = pagamento.getTotalAPagar();
		this.pedidoId = pagamento.getPedido().getId();
	}
	
	public PagamentoDto(Cliente cliente, Pedido pedido, Cupom cupom, Cartao cartao) {
		this.setPedidoId(pedido.getId());
		this.setClienteId(cliente.getId());
		this.setCartaoId(cartao.getId());
		this.setValorTotal(pedido.getValorTotal());
		this.setValorDesconto(cupom.getValor());

	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getCartaoId() {
		return cartaoId;
	}

	public void setCartaoId(Long cartaoId) {
		this.cartaoId = cartaoId;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Long getCupomId() {
		return cupomId;
	}

	public void setCupomId(Long cupomId) {
		this.cupomId = cupomId;
	}

}
