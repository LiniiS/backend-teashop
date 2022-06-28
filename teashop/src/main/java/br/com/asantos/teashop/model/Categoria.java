package br.com.asantos.teashop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

/**
 * Esta classe representa o modelo referente as categorias dos produtos do
 * comércio eletrônico para chás
 * 
 * @author Aline Santos
 *
 */
@Entity
@Table(name = "categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cat_id")
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "cat_nome")
	private String nome;

	@Column(name = "cat_descricao")
	private String descricao;

	@URL
	@Column(name = "cat_imgUrl")
	private String imgUrl;

	@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Produto> produtos;

	public Categoria() {
	}

	public Categoria(@NotNull @NotBlank String nome, String descricao, @URL String imgUrl) {
		this.nome = nome;
		this.descricao = descricao;
		this.imgUrl = imgUrl;
	}

	public Categoria(@NotNull @NotBlank String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Set<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<Produto> produtos) {
		this.produtos = produtos;
	}

}
