package br.com.asantos.teashop.model.cliente;

import java.util.Date;

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

import br.com.asantos.teashop.model.Produto;



@Entity
@Table(name = "lista_desejos")
public class ListaDesejo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lst_id")
	private Long id;

	// a lista de Desejos pertence a apenas um cliente
	@OneToOne(targetEntity = Cliente.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "lst_cli_id")
	private Cliente cliente;

	@Column(name = "lst_dt_criacao")
	private Date dtCriacao;

	// uma lista pode conter muitos produtos
	@ManyToOne
	@JoinColumn(name = "lst_prd_id")
	private Produto produto;

	public ListaDesejo() {
	}


	public ListaDesejo(Cliente cliente, Produto produto) {
		this.cliente = cliente;
		this.produto = produto;
		this.dtCriacao = new Date();
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

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
