package br.com.asantos.teashop.dto.pedido;

/**
 * Classe responsável por transferir os dados do carrinho dentro da sessão de
 * compra, gerando uma sessão de checkout que será paga via API de Pagamentos
 * 
 * @author Aline S
 *
 */

public class ItemCheckoutDto {

	private String produto;
	private int quantidade;
	private double preco;
	private Long produtoId;
	private Long clienteId;

	public ItemCheckoutDto() {
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

}
