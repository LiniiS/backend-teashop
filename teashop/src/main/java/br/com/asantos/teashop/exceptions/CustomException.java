package br.com.asantos.teashop.exceptions;

/**
 * Essa classe é responsável por lidar com erros e contem mensagens customizadas 
 * dentro da aplicação
 * @author Aline Santos
 *
 */
@SuppressWarnings("serial")
public class CustomException extends IllegalArgumentException {
	public CustomException(String msg) {
		super(msg);
	}
}
