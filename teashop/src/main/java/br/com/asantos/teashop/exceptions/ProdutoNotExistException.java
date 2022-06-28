package br.com.asantos.teashop.exceptions;

@SuppressWarnings("serial")
public class ProdutoNotExistException extends IllegalArgumentException{
	public ProdutoNotExistException(String msg) {
		super(msg);
	}

}
