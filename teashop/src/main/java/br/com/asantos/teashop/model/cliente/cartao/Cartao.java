package br.com.asantos.teashop.model.cliente.cartao;

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

import br.com.asantos.teashop.dto.cliente.CartaoDto;
import br.com.asantos.teashop.model.cliente.Cliente;

/**
 * Esta classe representa o modelo referente aos cartões do cliente cadastrado
 * no comércio eletrônico
 * 
 * @author Aline Santos
 *
 */
@Entity
@Table(name = "cartoes")
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private Long id;

	@Column(name = "car_numero")
	@NotNull
	private long numeroCartao;

	@Column(name = "car_nome_impresso")
	@NotNull
	@NotBlank
	private String nomeImpresso;

	@Column(name = "car_bandeira")
	@NotNull
	@Enumerated(EnumType.STRING)
	private Bandeira bandeira;

	@Column(name = "car_cvv")
	@NotNull
	private int cvv;
	
	@Column(name="car_padrao")
	private Boolean cartaoPadrao;

	// muitos cartões para um cliente
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "car_cli_id", nullable = false)
	Cliente cliente;

	public Cartao() {
	}

	public Cartao(@NotNull long numeroCartao, @NotNull @NotBlank String nomeImpresso, @NotNull Bandeira bandeira,
			@NotNull int cvv, Boolean cartaoPadrao, Cliente cliente) {
		this.numeroCartao = numeroCartao;
		this.nomeImpresso = nomeImpresso;
		this.bandeira = bandeira;
		this.cvv = cvv;
		this.cartaoPadrao = cartaoPadrao;
		this.cliente = cliente;
	}

	/**
	 * Constroi um Cartão a partir do DTO recebido
	 * @param cartaoDto
	 * @param cliente
	 */
	public Cartao(CartaoDto cartaoDto, Cliente cliente) {
		
		this.numeroCartao = cartaoDto.getNumeroCartao();
		this.nomeImpresso = cartaoDto.getNomeImpresso();
		this.bandeira = cartaoDto.getBandeira();
		this.cvv = cartaoDto.getCvv();
		this.cartaoPadrao = cartaoDto.getCartaoPadrao();
		this.cliente = cliente;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(long numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getNomeImpresso() {
		return nomeImpresso;
	}

	public void setNomeImpresso(String nomeImpresso) {
		this.nomeImpresso = nomeImpresso;
	}

	public Bandeira getBandeira() {
		return bandeira;
	}

	public void setBandeira(Bandeira bandeira) {
		this.bandeira = bandeira;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Boolean getCartaoPadrao() {
		return cartaoPadrao;
	}

	public void setCartaoPadrao(Boolean cartaoPadrao) {
		this.cartaoPadrao = cartaoPadrao;
	}

	
}
