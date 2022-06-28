package br.com.asantos.teashop.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.cliente.Cliente;

/**
 * Essa interface é responsável por realizar a comunicação com a persistência de
 * dados associados ao Cliente por meio da JpaRepository e seus métodos
 * 
 * @author Aline S
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

	Cliente findByEmail(String email);

	Cliente findByNome(String nome);

}
