package br.com.asantos.teashop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.ProdutoDto;
import br.com.asantos.teashop.exceptions.ProdutoNotExistException;
import br.com.asantos.teashop.model.Categoria;
import br.com.asantos.teashop.model.Produto;
import br.com.asantos.teashop.repository.ProdutoRepository;

/**
 * Classe responsável por orquestrar os serviços solicitados via Controller para
 * o gerenciar os produtos vendidos no comércio eletrônico (painel admin)
 * 
 * @author Aline Santos
 *
 */
@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	/**
	 * Método para listar todos os produtos cadastrados, a partir de um Produto
	 * criado, gera os DTOs e os adiciona à lista que será devolvida e exibida na
	 * view
	 * 
	 * @return
	 */
	public List<ProdutoDto> listaProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		List<ProdutoDto> produtoDtos = new ArrayList<ProdutoDto>();
		for (Produto produto : produtos) {
			ProdutoDto produtoDto = getDtoDoProduto(produto);
			produtoDtos.add(produtoDto);
		}
		return produtoDtos;
	}

	/**
	 * Método de suporte para criar um DTO a partir de um produto já salvo
	 * 
	 * @param produto
	 * @return
	 */
	public static ProdutoDto getDtoDoProduto(Produto produto) {
		ProdutoDto produtoDto = new ProdutoDto(produto);
		return produtoDto;
	}

	/**
	 * Método de suporte para extrair os dados do DTO e construir um produto
	 * 
	 * @param produtoDto
	 * @param categoria
	 * @return
	 */
	public static Produto getProdutoDoDto(ProdutoDto produtoDto, Categoria categoria) {
		Produto produto = new Produto(produtoDto, categoria);
		return produto;
	}

	/**
	 * Método para adicionar um produto passando um DTO e sua categoria
	 * 
	 * @param produtoDto
	 * @param categoria
	 */

	public void adicionaProduto(ProdutoDto produtoDto, Categoria categoria) {
		Produto produto = getProdutoDoDto(produtoDto, categoria);
		produtoRepository.save(produto);
	}

	// TODO ao refatorar, considerar adicionar o Optional Produto
	public void atualizaProduto(Long produtoId, ProdutoDto produtoDto, Categoria categoria) {
		Produto produto = getProdutoDoDto(produtoDto, categoria);
		produto.setId(produtoId);
		produtoRepository.save(produto);

	}

	/**
	 * Método pra encontrar um Produto dado seu id, findProdutoById
	 * 
	 * @param produtoId
	 * @return
	 * @throws ProdutoNotExistException
	 */
	public Produto buscaProduto(Long produtoId) throws ProdutoNotExistException {
		Optional<Produto> optionalProduto = produtoRepository.findById(produtoId);
		if (!optionalProduto.isPresent())
			throw new ProdutoNotExistException("Produto com ID: " + produtoId + " inválido.");
		return optionalProduto.get();
	}

	/**
	 * Método para remover um produto
	 * 
	 * @param produtoId
	 * @throws ProdutoNotExistException
	 */
	public void deletaProduto(Long produtoId) throws ProdutoNotExistException {
		Optional<Produto> optionalProduto = produtoRepository.findById(produtoId);
		if (!optionalProduto.isPresent())
			throw new ProdutoNotExistException("Produto com ID: " + produtoId + " inválido.");
		produtoRepository.deleteById(produtoId);
	}

}
