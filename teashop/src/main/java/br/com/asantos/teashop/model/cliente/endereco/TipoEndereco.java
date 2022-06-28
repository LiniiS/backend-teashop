package br.com.asantos.teashop.model.cliente.endereco;

public enum TipoEndereco {
	COBRANCA ("Cobrança"),
	ENTREGA ("Entrega"),
	COBRANCA_E_ENTREGA ("Cobrança e Entrega");
	
	private final String tipoEndereco;
	
	private TipoEndereco(String tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public String getTipoEndereco() {
		return tipoEndereco;
	}
	
}
