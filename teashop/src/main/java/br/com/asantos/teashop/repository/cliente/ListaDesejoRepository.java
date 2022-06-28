package br.com.asantos.teashop.repository.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.cliente.ListaDesejo;



@Repository
public interface ListaDesejoRepository extends JpaRepository<ListaDesejo, Long> {

	List<ListaDesejo> findAllByClienteIdOrderByDtCriacaoDesc(Long clienteId);

	List<ListaDesejo> findAllByClienteOrderByDtCriacaoDesc(Cliente cliente);

}
