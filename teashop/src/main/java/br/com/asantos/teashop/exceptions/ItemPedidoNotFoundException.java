package br.com.asantos.teashop.exceptions;

public class ItemPedidoNotFoundException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public ItemPedidoNotFoundException(String mensagem) {
		super(mensagem);
	}
}
