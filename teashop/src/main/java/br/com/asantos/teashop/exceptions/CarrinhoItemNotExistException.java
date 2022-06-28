package br.com.asantos.teashop.exceptions;

@SuppressWarnings("serial")
public class CarrinhoItemNotExistException extends IllegalArgumentException {
	public CarrinhoItemNotExistException(String msg) {
		super(msg);
	}
}
