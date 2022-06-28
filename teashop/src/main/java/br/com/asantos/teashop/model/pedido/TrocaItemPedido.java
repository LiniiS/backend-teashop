package br.com.asantos.teashop.model.pedido;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.asantos.teashop.model.cliente.Cliente;

@Entity
@Table(name="trocas")
public class TrocaItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tro_id")
	private Long id;
	
	@Column(name = "tro_motivo")
	private String motivo;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="troca_id")
	private ItemPedido itemPedido;
	
	@Column(name = "tro_status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "cli_id")
	private Cliente cliente;
	
	//teste
	@Column(name="tro_ped_id")
	private long pedidoId;

	@OneToOne
	@JoinColumn(name="tro_pedido_id")
	private Pedido pedido;
	
	public TrocaItemPedido() {}
	
	 
	public TrocaItemPedido(String motivo, ItemPedido itemPedido, Cliente cliente, String status, long pedidoId, Pedido pedido) {
		this.motivo = motivo;
		this.itemPedido = itemPedido;
		this.cliente = cliente;
		this.status = status;
		this.pedidoId = pedidoId;
		this.pedido = pedido;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public long getPedidoId() {
		// TODO Auto-generated method stub
		return pedidoId;
	}


	public void setPedidoId(long pedidoId) {
		this.pedidoId = pedidoId;
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}
