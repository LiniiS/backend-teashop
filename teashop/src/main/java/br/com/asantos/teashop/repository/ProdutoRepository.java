package br.com.asantos.teashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
