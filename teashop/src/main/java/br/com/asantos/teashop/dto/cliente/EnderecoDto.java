package br.com.asantos.teashop.dto.cliente;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.asantos.teashop.model.TokenAutenticacao;
import br.com.asantos.teashop.model.cliente.endereco.Endereco;
import br.com.asantos.teashop.model.cliente.endereco.Residencia;
import br.com.asantos.teashop.model.cliente.endereco.TipoEndereco;


/**
 * Esta classe permite a transferência de dados referente aos enderecos
 * associados ao cliente
 * 
 * @author Aline Santos
 *
 */
public class EnderecoDto {

	private Long id;

	@NotNull
	private Residencia residencia;

	@NotNull
	@NotBlank
	private String logradouro;

	@NotNull
	@NotBlank
	private String bairro;

	@NotNull
	@NotBlank
	private String cidade;

	@NotNull
	@NotBlank
	private String estado;

	@NotNull
	@NotBlank
	private String pais;

	@NotNull
	@NotBlank
	private String observacao;

	@NotNull
	@NotBlank
	private String cep;

	@NotNull
	private int numero;

	@NotNull
	private TipoEndereco tipoEndereco;

	@NotNull
	@NotBlank
	private String nome;

	private Long clienteId;

	public EnderecoDto() {
	}

	// construtor para criar um EnderecoDto usando um Endereco
	public EnderecoDto(Endereco endereco) {
		this.setId(endereco.getId());
		this.setResidencia(endereco.getResidencia());
		this.setLogradouro(endereco.getLogradouro());
		this.setBairro(endereco.getBairro());
		this.setCidade(endereco.getCidade());
		this.setEstado(endereco.getEstado());
		this.setPais(endereco.getPais());
		this.setObservacao(endereco.getObservacao());
		this.setCep(endereco.getCep());
		this.setNumero(endereco.getNumero());
		this.setTipoEndereco(endereco.getTipoEndereco());
		this.setNome(endereco.getNome());
		this.setClienteId(endereco.getCliente().getId());
	}

	public EnderecoDto(@NotNull Residencia residencia, @NotNull @NotBlank String logradouro,
			@NotNull @NotBlank String bairro, @NotNull @NotBlank String cidade, @NotNull @NotBlank String estado,
			@NotNull @NotBlank String pais, @NotNull @NotBlank String observacao, @NotNull @NotBlank String cep,
			@NotNull int numero, @NotNull TipoEndereco tipoEndereco, @NotNull @NotBlank String nome, Long clienteId) {
		this.residencia = residencia;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
		this.observacao = observacao;
		this.cep = cep;
		this.numero = numero;
		this.tipoEndereco = tipoEndereco;
		this.nome = nome;
		this.clienteId = clienteId;
	}

	/**
	 * Construtor de teste para verificar o cliente dado um Token
	 * 
	 * @param residencia
	 * @param logradouro
	 * @param bairro
	 * @param cidade
	 * @param estado
	 * @param pais
	 * @param observacao
	 * @param cep
	 * @param numero
	 * @param tipoEndereco
	 * @param nome
	 * @param token
	 */
	public EnderecoDto(@NotNull Residencia residencia, @NotNull @NotBlank String logradouro,
			@NotNull @NotBlank String bairro, @NotNull @NotBlank String cidade, @NotNull @NotBlank String estado,
			@NotNull @NotBlank String pais, @NotNull @NotBlank String observacao, @NotNull @NotBlank String cep,
			@NotNull int numero, @NotNull TipoEndereco tipoEndereco, @NotNull @NotBlank String nome,
			TokenAutenticacao token) {
		this.residencia = residencia;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
		this.observacao = observacao;
		this.cep = cep;
		this.numero = numero;
		this.tipoEndereco = tipoEndereco;
		this.nome = nome;
		this.clienteId = setClienteIdToken(token);

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Residencia getResidencia() {
		return residencia;
	}

	public void setResidencia(Residencia residencia) {
		this.residencia = residencia;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * set customizado para verificar o cliente dado um token
	 * 
	 * @param token
	 * @return
	 */
	public Long setClienteIdToken(TokenAutenticacao token) {
		TokenAutenticacao tk = token;
		return this.clienteId = tk.getCliente().getId();
	}
}
