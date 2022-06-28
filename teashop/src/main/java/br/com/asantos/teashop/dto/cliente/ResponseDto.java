package br.com.asantos.teashop.dto.cliente;

/**
 * Essa classe especifica a resposta em caso de erros associados aos Dtos
 * 
 * @author Aline Santos
 *
 */
public class ResponseDto {
	private String status;
	private String message;

	public ResponseDto(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
