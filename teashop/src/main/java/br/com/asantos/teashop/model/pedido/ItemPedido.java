package br.com.asantos.teashop.model.pedido;

import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.asantos.teashop.model.Produto;
import br.com.asantos.teashop.model.pedido.situacao.estados.Estado;

/**
 * Verificar a necessidade desta classe a implementação com o pagamento via
 * Stripe dispensou essa classe considerar a reavaliação do sistema
 * 
 * @author Aline S
 *
 */
@Entity
@Table(name = "items_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itp_id")
	private Long id;

	@Column(name = "itp_quantidade")
	@NotNull
	private int quantidade;

	@Column(name = "itp_dt_criacao")
	private Date dtCriacao;

	// considerar um BigDecimal qnd for valores monetários
	@Column(name = "itp_valor")
	@NotNull
	private double valor;

	@OneToOne
	@JoinColumn(name = "itp_prd_id", referencedColumnName = "prd_id")
	@NotNull
	private Produto produto;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "itp_ped_id", referencedColumnName = "ped_id")
	private Pedido pedido;

	
	@Column(name="itp_status")
	@Enumerated(EnumType.STRING)
	private Estado status;

	public ItemPedido() {
	}

	public ItemPedido(@NotNull int quantidade, Date dtCriacao, @NotNull double valor, @NotNull Produto produto,
			Pedido pedido, Estado status) {
		this.quantidade = quantidade;
		this.dtCriacao = new Date();
		this.valor = valor;
		this.produto = produto;
		this.pedido = pedido;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Estado getStatus() {
		return status;
	}

	public void setStatus(Estado status) {
		this.status = status;
	}

}
