package br.com.asantos.teashop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.cliente.cartao.Cartao;
import br.com.asantos.teashop.model.pedido.Pedido;

@Entity
@Table(name="pagamentos")
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pag_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "pag_cli_id")
	private Cliente cliente;
	
	
	@ManyToOne
	@JoinColumn(name = "pag_card_id")
	private Cartao cartao;
	
	
	@ManyToOne
	@JoinColumn(name = "pag_cup_id")
	private Cupom cupom;
	
	@ManyToOne
	@JoinColumn(name = "pag_ped_id")
	private Pedido pedido;
	
	@Column(name="pag_total")
	private double totalAPagar;
	
	@Column(name="pag_desconto")
	private double totalDesconto;

	public Pagamento() {}
	
	
	
	
	public Pagamento(Cliente cliente, Cartao cartao, Cupom cupom, Pedido pedido,
			double totalAPagar, double totalDesconto) {
		this.cliente = cliente;
		this.cartao = cartao;
		this.cupom = cupom;
		this.pedido = pedido;
		this.totalAPagar = totalAPagar;
		this.totalDesconto = totalDesconto;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public Cupom getCupom() {
		return cupom;
	}

	public void setCupom(Cupom cupom) {
		this.cupom = cupom;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public double getTotalAPagar() {
		return totalAPagar;
	}

	public void setTotalAPagar(double totalAPagar) {
		this.totalAPagar = totalAPagar;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




	public double getTotalDesconto() {
		return totalDesconto;
	}




	public void setTotalDesconto(double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}

	
	
	
}
