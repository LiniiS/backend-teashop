package br.com.asantos.teashop.exceptions;

@SuppressWarnings("serial")
public class AuthenticationFailException extends IllegalArgumentException {
	//TODO renomear pra AutenticacaoFailException
	public AuthenticationFailException(String msg) {
		super(msg);
	}
}
