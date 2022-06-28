package br.com.asantos.teashop.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.asantos.teashop.common.ApiResponse;
import br.com.asantos.teashop.dto.ProdutoDto;
import br.com.asantos.teashop.model.Categoria;
import br.com.asantos.teashop.service.CategoriaService;
import br.com.asantos.teashop.service.ProdutoService;

/**
 * Esta classe faz o mapeamento para as rotas associadas gerenciamento dos
 * produtos (chás) vendidos no comércio eletrônico (painel do admin)
 * 
 * @author Aline Santos
 *
 */
@RestController
@RequestMapping("/produto")
public class ProdutoController {
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/")
	public ResponseEntity<List<ProdutoDto>> getProdutos() {
		List<ProdutoDto> body = produtoService.listaProdutos();
		return new ResponseEntity<List<ProdutoDto>>(body, HttpStatus.OK);
	}

	@PostMapping("/adiciona")
	public ResponseEntity<ApiResponse> adicionaProduto(@Valid @RequestBody ProdutoDto produtoDto) {
		Optional<Categoria> optionalCategoria = categoriaService.buscaCategoria(produtoDto.getCategoriaId());
		if (!optionalCategoria.isPresent()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Categoria inválida."), HttpStatus.CONFLICT);
		}
		Categoria categoria = optionalCategoria.get();
		produtoService.adicionaProduto(produtoDto, categoria);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Novo produto adicionado."), HttpStatus.CREATED);
	}

	@PutMapping("/atualiza/{produtoId}")
	public ResponseEntity<ApiResponse> atualizaProduto(@PathVariable("produtoId") Long produtoId,
			@RequestBody @Valid ProdutoDto produtoDto) {
		Optional<Categoria> optionalCategoria = categoriaService.buscaCategoria(produtoDto.getCategoriaId());
		if (!optionalCategoria.isPresent()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Categoria inválida"), HttpStatus.CONFLICT);
		}
		Categoria categoria = optionalCategoria.get();
		produtoService.atualizaProduto(produtoId, produtoDto, categoria);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Dados do produto foram atualizados."),
				HttpStatus.OK);
	}

}
