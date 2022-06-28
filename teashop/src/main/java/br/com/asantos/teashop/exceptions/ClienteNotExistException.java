package br.com.asantos.teashop.exceptions;

@SuppressWarnings("serial")
public class ClienteNotExistException extends IllegalArgumentException{
	public ClienteNotExistException(String msg) {
		super(msg);
	}

}
