package br.com.asantos.teashop.exceptions;

public class PedidoNotFoundException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public PedidoNotFoundException(String mensagem) {
		super(mensagem);
	}
}
