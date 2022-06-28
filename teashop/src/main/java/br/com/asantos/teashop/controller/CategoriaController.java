package br.com.asantos.teashop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.asantos.teashop.common.ApiResponse;
import br.com.asantos.teashop.model.Categoria;
import br.com.asantos.teashop.service.CategoriaService;
import br.com.asantos.teashop.utils.Helper;


/**
 * Esta classe faz o mapeamento para as rotas associadas gerenciamento das
 * categorias dos produtos vendidos no comércio eletrônico (painel do admin)
 * 
 * @author Aline Santos
 *
 */
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/")
	//TODO padronizar os nomes pra busca/adiciona/atualiza
	public ResponseEntity<List<Categoria>> getCategorias() {
		List<Categoria> body = categoriaService.listaCategorias();
		return new ResponseEntity<List<Categoria>>(body, HttpStatus.OK);
	}

	@PostMapping("/adiciona")
	public ResponseEntity<ApiResponse> adicionaCategoria(@Valid @RequestBody Categoria categoria) {
		// verifica se já existe uma categoria com esse nome
		if (Helper.notNull(categoriaService.buscaCategoria(categoria.getNome()))) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Categoria inválida."), HttpStatus.CONFLICT);
		}
		categoriaService.adicionaCategoria(categoria);
		return new ResponseEntity<>(new ApiResponse(true, "Nova categoria para produtos adicionada."),
				HttpStatus.CREATED);
	}

	@PutMapping("/atualiza/{categoriaId}")
	public ResponseEntity<ApiResponse> atualizaCategoria(@PathVariable("categoriaId") Long categoriaId,
			@Valid @RequestBody Categoria categoria) {
		if (!Helper.notNull(categoriaService.buscaCategoria(categoriaId))) {
			return new ResponseEntity<>(new ApiResponse(false, "Categoria com ID: " + categoriaId + "não existe."),
					HttpStatus.NOT_FOUND);
		}
		categoriaService.atualizaCategoria(categoriaId, categoria);
		return new ResponseEntity<>(new ApiResponse(true, "Dados da categoria foram atualizados!"), HttpStatus.OK);
	}

	@DeleteMapping("/deleta/{categoriaId}")
	public ResponseEntity<ApiResponse> deletaCategoria(@PathVariable("categoriaId") Long categoriaId) {
		if (!Helper.notNull(categoriaService.buscaCategoria(categoriaId))) {
			return new ResponseEntity<>(new ApiResponse(false, "Categoria com ID: " + categoriaId + "não existe"),
					HttpStatus.NOT_FOUND);
		}
		categoriaService.deletaCategoria(categoriaId);
		return new ResponseEntity<>(new ApiResponse(true, "Categora removida."), HttpStatus.OK);
	}

}
