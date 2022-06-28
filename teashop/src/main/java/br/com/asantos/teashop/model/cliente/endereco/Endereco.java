package br.com.asantos.teashop.model.cliente.endereco;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.asantos.teashop.dto.cliente.EnderecoDto;
import br.com.asantos.teashop.model.cliente.Cliente;

/**
 * Esta classe representa o modelo referente aos endereços associados ao cliente
 * cadastrado no comércio eletrônico de chás
 * 
 * @author Aline Santos
 *
 */
@Entity
@Table(name = "enderecos")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "end_id")
	private Long id;

	@Column(name = "end_residencia")
	@Enumerated(EnumType.STRING)
	@NotNull
	private Residencia residencia;

	@Column(name = "end_logradouro")
	@NotNull
	@NotBlank
	private String logradouro;

	@Column(name = "end_bairro")
	@NotNull
	@NotBlank
	private String bairro;

	@Column(name = "end_cidade")
	@NotNull
	@NotBlank
	private String cidade;

	@Column(name = "end_estado")
	@NotNull
	@NotBlank
	private String estado;

	@Column(name = "end_pais")
	@NotNull
	@NotBlank
	private String pais;

	@Column(name = "end_observacao")
	private String observacao;

	@Column(name = "end_cep")
	@NotNull
	@NotBlank
	private String cep;

	@Column(name = "end_numero")
	@NotNull
	private int numero;

	// !TODO verificar a implementação do tipo de end: entrega / cobrança
	@Enumerated(EnumType.STRING)
	@Column(name = "end_tipo")
	@NotNull
	private TipoEndereco tipoEndereco;

	@Column(name = "end_nome")
	@NotNull
	@NotBlank
	private String nome;

	// muitos endereços para um cliente
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "end_cli_id", nullable = false)
	Cliente cliente;

	public Endereco() {
	}

	// constroi um endereco a partir do Dto
	public Endereco(EnderecoDto enderecoDto, Cliente cliente) {
		this.residencia = enderecoDto.getResidencia();
		this.logradouro = enderecoDto.getLogradouro();
		this.bairro = enderecoDto.getBairro();
		this.cidade = enderecoDto.getCidade();
		this.estado = enderecoDto.getEstado();
		this.pais = enderecoDto.getPais();
		this.observacao = enderecoDto.getObservacao();
		this.cep = enderecoDto.getCep();
		this.numero = enderecoDto.getNumero();
		this.tipoEndereco = enderecoDto.getTipoEndereco();
		this.nome = enderecoDto.getNome();
		this.cliente = cliente;
	}

	public Endereco(@NotNull Residencia residencia, @NotNull @NotBlank String logradouro,
			@NotNull @NotBlank String bairro, @NotNull @NotBlank String cidade, @NotNull @NotBlank String estado,
			@NotNull @NotBlank String pais, String observacao, @NotNull @NotBlank String cep, @NotNull int numero,
			@NotNull TipoEndereco tipoEndereco, @NotNull @NotBlank String nome, Cliente cliente) {
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
		this.cliente = cliente;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

}
