package br.com.asantos.teashop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.asantos.teashop.model.cliente.Cliente;



/**
 * Esta classe representa o modelo referente ao carrinho de compras do cliente e
 * vincula os produtos ao cliente por meio do token quando o cliente está logado
 * 
 * @author Aline Santos
 *
 */
@Entity
@Table(name = "carrinhos")
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private Long id;

	@Column(name = "car_dt_criacao")
	private Date dtCriacao;

	@ManyToOne
	@JoinColumn(name = "car_prd_id")
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "car_cli_id")
	private Cliente cliente;

	@Column(name = "car_quantidade")
	private int quantidade;

	public Carrinho() {
	}

	/**
	 * Cria um carrinho a partir do Cliente logado, com um produto e a quantidade
	 * com a data de criação
	 * 
	 * @param produto
	 * @param quantidade
	 * @param cliente
	 */
	public Carrinho(Produto produto, Integer quantidade, Cliente cliente) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.cliente = cliente;
		this.dtCriacao = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
