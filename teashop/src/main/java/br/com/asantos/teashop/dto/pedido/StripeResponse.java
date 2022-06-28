package br.com.asantos.teashop.dto.pedido;

public class StripeResponse {
	
	private String sessaoId;

	public StripeResponse(String sessaoId) {
		this.sessaoId = sessaoId;
	}

	public String getSessaoId() {
		return sessaoId;
	}

	public void setSessaoId(String sessaoId) {
		this.sessaoId = sessaoId;
	}

}
