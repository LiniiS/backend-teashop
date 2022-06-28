package br.com.asantos.teashop.model.cliente.cartao;

public enum Bandeira {
	
	MASTERCARD ("Mastercard"),
	VISA ("Visa"),
	ELO ("Elo"),
	AMERICAN_EXPRESS ("American Express"),
	HIPERCARD ("Hipercard");
	
	private final String tipoBandeira;
	
	private Bandeira(String tipoBandeira){
		this.tipoBandeira = tipoBandeira;
	}

	public String getTipoBandeira() {
		return tipoBandeira;
	}
}
