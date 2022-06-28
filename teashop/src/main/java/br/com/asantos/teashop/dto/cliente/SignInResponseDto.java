package br.com.asantos.teashop.dto.cliente;

/**
 * Essa classe especifica a resposta em caso de erros associados ao SignIn
 * 
 * @author Aline Santos
 *
 */
public class SignInResponseDto {

	private String status;
	private String token;

	public SignInResponseDto(String status, String token) {
		this.status = status;
		this.token = token;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
