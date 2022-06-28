package br.com.asantos.teashop.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.asantos.teashop.model.cliente.Cliente;

/**
 * Essa classe representa a criação de um id de sessão que dará suporte ao fluxo
 * do pedido/compra/carrinho vinculado ao cliente
 * 
 * @author Aline Santos
 *
 */

@Entity
@Table(name = "tokens")
public class TokenAutenticacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tok_id")
	private Integer id;

	@Column(name = "tok_token")
	private String token;

	@Column(name = "tok_dt_criacao")
	private Date dtCriacao;

	// !TODO verificar melhor forma de criar essa config, por default ele não
	// configura a ação de OnDelete
	@OneToOne(targetEntity = Cliente.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "tok_cli_id", foreignKey = @ForeignKey(name = "fk_cli_id"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Cliente cliente;

	public TokenAutenticacao() {
	}

	public TokenAutenticacao(Integer id, String token, Date dtCriacao, Cliente cliente) {
		this.id = id;
		this.token = token;
		this.dtCriacao = dtCriacao;
		this.cliente = cliente;
	}

	public TokenAutenticacao(Cliente cliente) {
		this.cliente = cliente;
		this.dtCriacao = new Date();
		this.token = UUID.randomUUID().toString();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
