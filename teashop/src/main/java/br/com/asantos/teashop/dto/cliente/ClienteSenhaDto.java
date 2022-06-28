package br.com.asantos.teashop.dto.cliente;

import br.com.asantos.teashop.model.cliente.Cliente;

public class ClienteSenhaDto {
	
	private Long id;
	private String novaSenha;
	private String novaSenhaVerificada;
	
	private Cliente cliente;
	
	

	public ClienteSenhaDto(String novaSenha, String novaSenhaVerificada, Cliente cliente) {
		this.novaSenha = novaSenha;
		this.novaSenhaVerificada = novaSenhaVerificada;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaVerificada() {
		return novaSenhaVerificada;
	}

	public void setNovaSenhaVerificada(String novaSenhaVerificada) {
		this.novaSenhaVerificada = novaSenhaVerificada;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	

}
