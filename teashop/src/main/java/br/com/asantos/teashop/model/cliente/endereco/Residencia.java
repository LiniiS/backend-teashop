package br.com.asantos.teashop.model.cliente.endereco;

public enum Residencia {
	
	CASA ("Casa"),
	APARTAMENTO ("Apartamento");
	
	private final String tipoResidencia;
	
	private Residencia(String tipoResidencia) {
		this.tipoResidencia = tipoResidencia;
	}

	public String getTipoResidencia() {
		return tipoResidencia;
	}
		
	
}
