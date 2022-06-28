package br.com.asantos.teashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.asantos.teashop.model.TokenAutenticacao;
import br.com.asantos.teashop.model.cliente.Cliente;

/**
 * Essa interface é responsável por se comunicar com o banco de dados por meio
 * de métodos especificos e os existentes na JpaRepository
 * 
 * @author Aline Santos
 *
 */

@Repository
public interface TokenRepository extends JpaRepository<TokenAutenticacao, Long> {

	// JPA monta a sql ao seguir o padrão de nomenclatura pro método
	TokenAutenticacao findByCliente(Cliente cliente);

	TokenAutenticacao findByToken(String token);
}
