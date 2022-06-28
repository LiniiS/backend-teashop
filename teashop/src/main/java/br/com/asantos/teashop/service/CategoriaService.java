package br.com.asantos.teashop.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.asantos.teashop.model.Categoria;
import br.com.asantos.teashop.repository.CategoriaRepository;

/**
 * Classe responsável por orquestrar os serviços solicitados via Controller para
 * gerenciar as categorias dos produtos vendidos no comércio eletrônico (painel
 * admin)
 * 
 * @author Aline Santos
 *
 */

@Service
@Transactional
public class CategoriaService {

	private final CategoriaRepository categoriaRepository;

	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	// lista todas as categorias dos produtos
	public List<Categoria> listaCategorias() {
		return categoriaRepository.findAll();
	}

	// cria uma nova categoria de produtos
	public void adicionaCategoria(Categoria categoria) {
		categoriaRepository.save(categoria);
	}

	// busca por uma categoria dado um nome
	public Categoria buscaCategoria(String categoriaNome) {
		return categoriaRepository.findByNome(categoriaNome);
	}

	// busca por uma categoria dado um id
	public Optional<Categoria> buscaCategoria(Long categoriaId) {
		return categoriaRepository.findById(categoriaId);
	}

	public void atualizaCategoria(Long categoriaId, Categoria categoriaAtualizada) {
		Categoria categoria = categoriaRepository.findById(categoriaId).get();
		categoria.setNome(categoriaAtualizada.getNome());
		categoria.setDescricao(categoria.getDescricao());
		categoria.setImgUrl(categoria.getImgUrl());

		categoriaRepository.save(categoria);
	}

	public void deletaCategoria(Long categoriaId) {
		categoriaRepository.deleteById(categoriaId);
	}

}
