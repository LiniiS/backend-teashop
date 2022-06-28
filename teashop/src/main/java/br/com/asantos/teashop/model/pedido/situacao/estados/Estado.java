package br.com.asantos.teashop.model.pedido.situacao.estados;

public enum Estado {
	EM_PROCESSAMENTO ("Em Processamento"),
	EM_TRANSITO ("Em Trânsito"),
	EM_TROCA ("Em Troca"),
	TROCA_AUTORIZADA ("Troca Autorizada"),
	ENTREGUE ("Entregue");
	
	private final String tipoEstado;
	
	private Estado(String tipoEstado){
		this.tipoEstado = tipoEstado;
	}

	public String getTipoEstado() {
		return tipoEstado;
	}
}
