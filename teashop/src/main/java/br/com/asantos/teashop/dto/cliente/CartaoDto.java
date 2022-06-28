package br.com.asantos.teashop.dto.cliente;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.asantos.teashop.model.TokenAutenticacao;
import br.com.asantos.teashop.model.cliente.cartao.Bandeira;
import br.com.asantos.teashop.model.cliente.cartao.Cartao;

/**
 * Esta classe permite a transferência de dados referente aos cartões associados
 * ao cliente cadastrado
 * 
 * @author Aline Santos
 *
 */

public class CartaoDto {

	private Long id;

	@NotNull
	private long numeroCartao;

	@NotNull
	@NotBlank
	private String nomeImpresso;

	@NotNull
	private Bandeira bandeira;

	@NotNull
	private int cvv;

	private Boolean cartaoPadrao;

	private Long clienteId;

	public CartaoDto() {
	}

	public CartaoDto(Cartao cartao) {
		this.setId(cartao.getId());
		this.setNumeroCartao(cartao.getNumeroCartao());
		this.setNomeImpresso(cartao.getNomeImpresso());
		this.setBandeira(cartao.getBandeira());
		this.setCvv(cartao.getCvv());
		this.setCartaoPadrao(cartao.getCartaoPadrao());
		this.setClienteId(cartao.getCliente().getId());
	}

	public CartaoDto(@NotNull long numeroCartao, @NotNull @NotBlank String nomeImpresso, @NotNull Bandeira bandeira,
			@NotNull int cvv, Boolean cartaoPadrao, Long clienteId) {
		this.numeroCartao = numeroCartao;
		this.nomeImpresso = nomeImpresso;
		this.bandeira = bandeira;
		this.cvv = cvv;
		this.cartaoPadrao = cartaoPadrao;
		this.clienteId = clienteId;
	}

	/**
	 * Construtor de teste para cadastrar o Cartão pegando o cliente do token
	 * 
	 * @param numeroCartao
	 * @param nomeImpresso
	 * @param bandeira
	 * @param cvv
	 * @param cartaoPadrao
	 * @param token
	 */
	public CartaoDto(@NotNull long numeroCartao, @NotNull @NotBlank String nomeImpresso, @NotNull Bandeira bandeira,
			@NotNull int cvv, Boolean cartaoPadrao, TokenAutenticacao token) {
		this.numeroCartao = numeroCartao;
		this.nomeImpresso = nomeImpresso;
		this.bandeira = bandeira;
		this.cvv = cvv;
		this.cartaoPadrao = cartaoPadrao;
		this.clienteId = setClienteIdToken(token);
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

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Boolean getCartaoPadrao() {
		return cartaoPadrao;
	}

	public void setCartaoPadrao(Boolean cartaoPadrao) {
		this.cartaoPadrao = cartaoPadrao;
	}

	private Long setClienteIdToken(TokenAutenticacao token) {
		TokenAutenticacao tk = token;
		return this.clienteId = tk.getCliente().getId();
	}

}
