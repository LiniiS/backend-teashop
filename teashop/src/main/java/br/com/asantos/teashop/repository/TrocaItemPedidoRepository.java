package br.com.asantos.teashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.TrocaItemPedido;

@Repository
public interface TrocaItemPedidoRepository extends JpaRepository<TrocaItemPedido, Long>{

	

	List<TrocaItemPedido> findByClienteOrderByIdDesc(Cliente cliente);

	TrocaItemPedido getByIdOrderByIdDesc(Long itemTrocaId);

}
