package br.com.asantos.teashop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.asantos.teashop.dto.ProdutoDto;



/**
 * Esta classe representa o modelo referente aos produtos do comércio
 * eletrônico para chás
 * 
 * @author Aline Santos
 *
 */
@Entity
@Table(name="produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="prd_id")
	private Long id;
	
	@NotNull @NotBlank
	@Column(name="prd_nome")
	private String nome;
	
	@NotNull @NotBlank
	@Column(name="prd_sabor")
	private String sabor;
	
	//opcional
	@Column(name="prd_descricao")
	private String descricao;
	
	//TODO Considerar usar um validador próprio, esse depende do Hibernate
	@URL
	@Column(name="prd_imagem")
	private String urlImagem;
	
	
	//!TODO considerar o uso de BigDecimal
	@NotNull
	@Column(name="prd_preco")
	private double preco;
	
	//muitos produtos para uma mesma categoria
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="cat_id", nullable = false)
	private Categoria categoria;
	
	
	public Produto() {}
	
	//constroi um produto a partir dos dados do Dto
	public Produto(ProdutoDto produtoDto, Categoria categoria) {
		this.nome = produtoDto.getNome();
		this.sabor = produtoDto.getSabor();
		this.descricao = produtoDto.getDescricao();
		this.urlImagem = produtoDto.getUrlImagem();
		this.preco = produtoDto.getPreco();
		this.categoria = categoria;
	}

	
	public Produto(@NotNull @NotBlank String nome, @NotNull @NotBlank String sabor, String descricao,
			@URL String urlImagem, @NotNull  double preco, Categoria categoria) {
		this.nome = nome;
		this.sabor = sabor;
		this.descricao = descricao;
		this.urlImagem = urlImagem;
		this.preco = preco;
		this.categoria = categoria;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	
	
	
	
}
