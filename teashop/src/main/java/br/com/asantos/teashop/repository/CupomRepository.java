package br.com.asantos.teashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.Cupom;
import br.com.asantos.teashop.model.cliente.Cliente;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

	List<Cupom> findAllByCliente(Cliente cliente);

}
