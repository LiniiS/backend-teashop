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

@Entity
@Table(name = "cupons")
public class Cupom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cup_id")
	private Long id;

	@Column(name = "cup_codigo")
	private String codigo;

	@Column(name = "cup_valor")
	private double valor;

	@ManyToOne
	@JoinColumn(name = "cli_id")
	private Cliente cliente;

	public Cupom() {
	}

	public Cupom(String codigo, double valor, Cliente cliente) {
		this.codigo = codigo;
		this.valor = valor;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
