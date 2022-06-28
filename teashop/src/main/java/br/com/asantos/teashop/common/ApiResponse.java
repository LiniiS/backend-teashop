package br.com.asantos.teashop.common;

import java.time.LocalDateTime;

/**
 * Essa classe é responsável por especificar as respostas da API apresentando
 * mensagens e o horario associado ao evento ocorrido
 * 
 * @author Aline S
 *
 */
public class ApiResponse {

	private final boolean success;
	private final String message;

	public ApiResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public String getTimestamp() {
		return LocalDateTime.now().toString();
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

}
