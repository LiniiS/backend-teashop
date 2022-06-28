package br.com.asantos.teashop.model.pedido;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.asantos.teashop.model.Pagamento;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.situacao.estados.Estado;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ped_id")
	private Long id;

	@Column(name = "ped_valor_total")
	private double valorTotal;

	//TODO Alterar o nome dessa variável pra sessaoId
	@Column(name = "ped_sessao_id")
	private String sessao_id;

	// status do pedido -> inicialmente EmAnalise -> setar automaticamente qnd criar
	// o pedido
	@Column(name = "ped_status")
	@Enumerated(EnumType.STRING)
	private Estado status;

	@Column(name = "ped_dt_criacao")
	private Date dtCriacao;

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<ItemPedido> itemsPedido;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ped_cli_id", referencedColumnName = "cli_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pagamento> pagamento;
	
	
	
	public Pedido() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getSessao_id() {
		return sessao_id;
	}

	public void setSessao_id(String sessao_id) {
		this.sessao_id = sessao_id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItemsPedido() {
		return itemsPedido;
	}

	public void setItemsPedido(List<ItemPedido> itemsPedido) {
		this.itemsPedido = itemsPedido;
	}

	public Estado getStatus() {
		return status;
	}

	public void Estado(Estado status) {
		this.status = status;
	}

	/* TODO repensar STATUS PEDIDO implementação, talvez seja melhor passar pra uma service
	 verificar se seria melhor manter aqui as transições do pedido
	 ou criar uma service pra transitar entre os estados
	public void aprovar() {
		this.status.aprovar(this);
	}

	public void reprovar() {
		this.status.reprovar(this);
	}

	public void finalizar() {
		this.status.finalizar(this);
	}
	 */
	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public void setStatus(Estado emProcessamento) {
		this.status = Estado.EM_PROCESSAMENTO;
		
	}



}
