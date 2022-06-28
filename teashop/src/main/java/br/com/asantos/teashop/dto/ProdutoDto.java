package br.com.asantos.teashop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import br.com.asantos.teashop.model.Produto;

/**
 * Esta classe permite a transferencia de dados referente aos produtos do do
 * comércio eletrônico para chás
 * 
 * @author Aline Santos
 *
 */
public class ProdutoDto {

	private Long id;

	@NotNull
	@NotBlank
	private String nome;

	@NotNull
	@NotBlank
	private String sabor;

	@NotNull
	@NotBlank
	private String descricao;

	// TODO considerar usar um validador prórpio, esse é do Hibernate
	@URL
	private String urlImagem;

	@NotNull
	private double preco;

	@NotNull
	private Long categoriaId;

	public ProdutoDto() {
	}

	// constroi um produtoDto a partir de um produto salvo
	public ProdutoDto(Produto produto) {
		this.setId(produto.getId());
		this.setNome(produto.getNome());
		this.setSabor(produto.getSabor());
		this.setDescricao(produto.getDescricao());
		this.setUrlImagem(produto.getUrlImagem());
		this.setPreco(produto.getPreco());
		this.setCategoriaId(produto.getCategoria().getId());
	}

	public ProdutoDto(@NotNull @NotBlank String nome, @NotNull @NotBlank String sabor,
			@NotNull @NotBlank String descricao, @URL String urlImagem, @NotNull double preco,
			@NotNull @NotBlank Long categoriaId) {
		this.nome = nome;
		this.sabor = sabor;
		this.descricao = descricao;
		this.urlImagem = urlImagem;
		this.preco = preco;
		this.categoriaId = categoriaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSabor() {
		return sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

}
