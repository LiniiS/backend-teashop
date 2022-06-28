package br.com.asantos.teashop.repository.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.cliente.cartao.Cartao;



@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	List<Cartao> findAllByClienteId(Long clienteId);

}
