package br.com.asantos.teashop.dto.pedido;

import br.com.asantos.teashop.model.pedido.Pedido;

/**
 * Essa classe é responsável por salvar os dados de interesse ao acompanhamento
 * do pedido (a principio n considerando o status dele 21/05)
 * 
 * @author Aline S
 *
 */
public class CriaPedidoDto {
	private Long id;
	private Long clienteId;
	private Double valorTotal;

	public CriaPedidoDto() {
	}

	/**
	 * Monta um dto a partir dos dados de um pedido
	 * 
	 * @param pedido
	 */
	public CriaPedidoDto(Pedido pedido) {
		this.setId(pedido.getId());
		this.setClienteId(pedido.getCliente().getId());
		this.setValorTotal(pedido.getValorTotal());
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

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

}
