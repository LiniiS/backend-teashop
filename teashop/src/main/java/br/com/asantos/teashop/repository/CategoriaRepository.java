package br.com.asantos.teashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	// busca pelo nome -> seguindo o padrão de nomenclatura da JPA
	Categoria findByNome(String categoriaNome);
}
