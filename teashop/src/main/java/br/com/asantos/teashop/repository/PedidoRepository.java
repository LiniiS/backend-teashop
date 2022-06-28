package br.com.asantos.teashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	//devolve uma lista de todos os pedidos dado um id de cliente
	List<Pedido> findAllByClienteIdOrderByDtCriacaoDesc(Long clienteId);
	
	List<Pedido> findAllByClienteOrderByDtCriacaoDesc(Cliente cliente);
	

}
