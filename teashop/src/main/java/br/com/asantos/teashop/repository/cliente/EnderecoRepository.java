package br.com.asantos.teashop.repository.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.cliente.endereco.Endereco;



@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	
	List<Endereco> findAllByClienteId(Long clienteId);

}
