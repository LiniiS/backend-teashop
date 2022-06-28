package br.com.asantos.teashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.Carrinho;
import br.com.asantos.teashop.model.cliente.Cliente;



@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{

	//padrão JPA pra query
	List<Carrinho> findAllByClienteOrderByDtCriacaoDesc(Cliente cliente);

	
	List<Carrinho> deleteByCliente(Cliente cliente);

}

