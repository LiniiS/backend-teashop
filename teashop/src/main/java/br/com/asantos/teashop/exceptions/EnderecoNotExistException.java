package br.com.asantos.teashop.exceptions;

@SuppressWarnings("serial")
public class EnderecoNotExistException extends IllegalArgumentException {

	public EnderecoNotExistException(String msg) {
		super(msg);
	}
}
