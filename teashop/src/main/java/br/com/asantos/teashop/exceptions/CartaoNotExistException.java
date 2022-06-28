package br.com.asantos.teashop.exceptions;

@SuppressWarnings("serial")
public class CartaoNotExistException extends IllegalArgumentException {

	public CartaoNotExistException(String msg) {
		super(msg);
	}
}
