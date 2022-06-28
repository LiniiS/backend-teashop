package br.com.asantos.teashop.dto.carrinho;

import javax.validation.constraints.NotNull;

/**
 * Esta classe permite a transferencia de dados referente aos produtos
 * adicionados ao carrinho do cliente, ao adicionar um produto ao carrinho o
 * custo será calculado com base na quantidade deste produto e seu preço
 * 
 * @author Aline Santos
 *
 */
public class AdicionaAoCarrinhoDto {

	private Long id;
	@NotNull
	private Long produtoId;
	@NotNull
	private Integer quantidade;

	public AdicionaAoCarrinhoDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
