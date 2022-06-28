package br.com.asantos.teashop.dto.pedido;

import br.com.asantos.teashop.model.pedido.Pedido;

/**
 * Similar ao ItemCheckoutDto, essa classe é usada pra transferir os itens do
 * carrinho pro pedido quando usar a teashop ocmo pagamento
 * 
 * @author xlnst
 *
 */
public class PedidoDto {
	private Long id;
	private double custoTotal;
	private Long clienteId;

	public PedidoDto() {
	}

	public PedidoDto(Pedido pedido) {
		this.id = pedido.getId();
		this.clienteId = pedido.getCliente().getId();
		this.custoTotal = pedido.getValorTotal();

	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public double getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(double custoTotal) {
		this.custoTotal = custoTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
