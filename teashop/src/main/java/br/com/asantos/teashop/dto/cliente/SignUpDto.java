package br.com.asantos.teashop.dto.cliente;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Essa classe é responsável por transportar os dados necessários para o
 * cadastro de um novo cliente dentro da aplicação
 * 
 * @author Aline Santos
 *
 */
//!TODO considerar refatorar para adotar o padrão Form (dados que vem do cliente e vão pra API)
public class SignUpDto {

	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String genero;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dtNascimento;
	@CPF
	private String cpf;
	@NotNull
	@NotBlank
	private String telefone;
	@Email
	private String email;
	@NotNull
	@NotBlank
	private String senha;

	@NotNull
	@NotBlank
	private String senhaVerificada;

	private boolean statusAtivo = true;

	public SignUpDto() {
	}

	public SignUpDto(@NotNull @NotBlank String nome, @NotNull @NotBlank String genero, @NotNull Date dtNascimento,
			@CPF @NotNull @NotBlank String cpf, @NotNull @NotBlank String telefone,
			@Email @NotNull @NotBlank String email, @NotNull @NotBlank String senha,
			@NotNull @NotBlank String senhaVerificada) {
		this.nome = nome;
		this.genero = genero;
		this.dtNascimento = dtNascimento;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.senhaVerificada = senhaVerificada;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean getStatusAtivo() {
		return statusAtivo;
	}

	public void setStatusAtivo(boolean statusAtivo) {
		this.statusAtivo = true;
	}

	public String getSenhaVerificada() {
		return senhaVerificada;
	}

	public void setSenhaVerificada(String senhaVerificada) {
		this.senhaVerificada = senhaVerificada;
	}

}
