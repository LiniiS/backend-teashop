package br.com.asantos.teashop.dto.cliente;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.asantos.teashop.model.cliente.Cliente;

 

/**
 * Classe que auxilia a transferencia de dados do Cliente no processo de editar
 * dados cadastrais
 * 
 * @author Aline Santos
 *
 */
public class ClienteDto {
	private Long id;

	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String genero;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dtNascimento;
	@CPF
	@NotNull
	@NotBlank
	private String cpf;
	@NotNull
	@NotBlank
	private String telefone;
	@Email
	@NotNull
	@NotBlank
	private String email;
	@NotNull
	@NotBlank
	private String senha;

	private boolean statusAtivo;

	public ClienteDto() {
	}

	public ClienteDto(Cliente cliente) {
		this.setId(cliente.getId());
		this.setNome(cliente.getNome());
		this.setGenero(cliente.getGenero());
		this.setDtNascimento(cliente.getDtNascimento());
		this.setCpf(cliente.getCpf());
		this.setTelefone(cliente.getTelefone());
		this.setEmail(cliente.getEmail());
		this.setSenha(cliente.getSenha());
		this.setStatusAtivo(cliente.getStatusAtivo());
	}

	public ClienteDto(@NotNull @NotBlank String nome, @NotNull @NotBlank String genero, @NotNull Date dtNascimento,
			@CPF @NotNull @NotBlank String cpf, @NotNull @NotBlank String telefone,
			@Email @NotNull @NotBlank String email, @NotNull @NotBlank String senha, boolean statusAtivo) {
		this.nome = nome;
		this.genero = genero;
		this.dtNascimento = dtNascimento;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.statusAtivo = statusAtivo;
	}

	/**
	 * Construtor para criar o DTO listado para o Admin
	 * 
	 * @param optionalCliente
	 */
	public ClienteDto(Optional<Cliente> optionalCliente) {
		this.setId(optionalCliente.get().getId());
		this.setNome(optionalCliente.get().getNome());
		this.setGenero(optionalCliente.get().getGenero());
		this.setDtNascimento(optionalCliente.get().getDtNascimento());
		this.setCpf(optionalCliente.get().getCpf());
		this.setTelefone(optionalCliente.get().getTelefone());
		this.setEmail(optionalCliente.get().getEmail());
		this.setSenha(optionalCliente.get().getSenha());
		this.setStatusAtivo(optionalCliente.get().getStatusAtivo());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		this.statusAtivo = statusAtivo;
	}

}
