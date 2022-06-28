package br.com.asantos.teashop.dto.cliente;

/**
 * Essa classe é responsável por transportar os dados necessários ao login
 * associado ao cliente cadastrado dentro da aplicação
 * 
 * @author Aline Santos
 *
 */
//TODO considerar refatorar para adotar o padrão Form
public class SignInDto {

	private String email;
	private String senha;

	public SignInDto(String email, String senha) {
		this.email = email;
		this.senha = senha;
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



}
