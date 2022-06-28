package br.com.asantos.teashop.dto.carrinho;

import br.com.asantos.teashop.model.Carrinho;
import br.com.asantos.teashop.model.Produto;

/**
 * Esta classe permite a transferencia de dados referente aos itens de um
 * carrinho do cliente, ao adicionar um produto ao carrinho, o carrinho será
 * criado com a data atual e terá seu custo total calculado
 * 
 * @author Aline Santos
 *
 */
public class ItemCarrinhoDto {

	private Long id;
	private Integer quantidade;
	// TODO refatorar considerando o uso do ProdutoDto e não a classe de domínio
	private Produto produto;

	public ItemCarrinhoDto() {
	}

	/**
	 * Constroi um item para o Carrinho
	 * 
	 * @param carrinho
	 */
	public ItemCarrinhoDto(Carrinho carrinho) {
		this.id = carrinho.getId();
		this.quantidade = carrinho.getQuantidade();
		this.setProduto(carrinho.getProduto());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
