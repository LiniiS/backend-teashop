package br.com.asantos.teashop.exceptions;

public class StatusPedidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StatusPedidoException(String mensagem) {
		super(mensagem);
	}
}
