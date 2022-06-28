package br.com.asantos.teashop.dto;

import br.com.asantos.teashop.model.Cupom;

public class CupomDto {

	private Long id;
	private String codigo;
	private Long clienteId;
	private double valor;

	public CupomDto(String codigo, Long clienteId, double valor) {
		this.codigo = codigo;
		this.clienteId = clienteId;
		this.valor = valor;
	}

	public CupomDto(Cupom cupom) {
		this.id = cupom.getId();
		this.codigo = cupom.getCodigo();
		this.clienteId = cupom.getCliente().getId();
		this.valor = cupom.getValor();

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

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
