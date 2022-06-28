package br.com.asantos.teashop.dto.carrinho;

import java.util.List;

/**
 * Esta classe permite a transferencia de dados referente aos carrinhos de um de
 * compra do cliente, ao adicionar um produto ao carrinho um item de carrinho
 * será criado com a data atual e terá seu custo total calculado
 * 
 * @author Aline Santos
 *
 */
public class CarrinhoDto {

	private Long id;
	private List<ItemCarrinhoDto> itensCarrinho;

	// TODO considerar na refatoração o uso de BigDecimal qnd se tratar de dinheiro
	private double custoTotal;

	public CarrinhoDto() {

	}

	/**
	 * Construtor para criar o Carrinho com os dados dos itens com seus produtos
	 * adicionados com o custo total calculado considerando o cliente que adicionou
	 * o produto ao carrinho
	 * 
	 * @param listaItensCarrinhoDto
	 * @param custoTotal
	 */
	public CarrinhoDto(List<ItemCarrinhoDto> listaItensCarrinhoDto, double custoTotal) {
		this.itensCarrinho = listaItensCarrinhoDto;
		this.custoTotal = custoTotal;
	}

	public List<ItemCarrinhoDto> getItensCarrinho() {
		return itensCarrinho;
	}

	public void setItensCarrinho(List<ItemCarrinhoDto> itensCarrinho) {
		this.itensCarrinho = itensCarrinho;
	}

	public double getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(double custoTotal) {
		this.custoTotal = custoTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
