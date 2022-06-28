package br.com.asantos.teashop.service.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.exceptions.AuthenticationFailException;
import br.com.asantos.teashop.model.TokenAutenticacao;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.repository.TokenRepository;
import br.com.asantos.teashop.utils.Helper;



/**
 * Essa classe é responsável por auxiliar a Controller que delega serviços
 * necessários dentro do fluxo de mapeamento de rotas que exige a verificação do
 * cliente autorizado/cadastrado/logado na aplicação
 * 
 * @author Aline Santos
 *
 */

@Service
public class AutenticacaoService {

	@Autowired
	private TokenRepository tokenRepository;
	
	//persiste o token criado
	public void salvaTokenConfirmacao(TokenAutenticacao tokenAutenticacao) {
		tokenRepository.save(tokenAutenticacao);
	}
	
	//retorna um token dado um cliente
	public TokenAutenticacao getToken(Cliente cliente) {
		return tokenRepository.findByCliente(cliente);
	}
	
		
	//retorna um cliente dado um token
	public Cliente getCliente(String token) {
		final TokenAutenticacao tokenAutenticacao = tokenRepository.findByToken(token);
		//existe um token?
		if(Helper.notNull(tokenAutenticacao)) {
			//se existe um token, verifica se há um cliente associado
			if(Helper.notNull(tokenAutenticacao.getCliente()));
				return tokenAutenticacao.getCliente();
		}
		return null;
	}
	
	//autentica um token (dado um token verifica se existe e se é valido)
	public void tokenAutenticado(String token) throws AuthenticationFailException {
		if(!Helper.notNull(token)) {
			throw new AuthenticationFailException("Token inválido");
		}
	}
	
	
	public void autenticaToken(String token) throws AuthenticationFailException {
		if(!Helper.notNull(token)) {
			//não há um token válido (null check)
			throw new AuthenticationFailException("Token inexistente");
		}
		if(!Helper.notNull(getCliente(token))) {
			//não há um cliente ocm esse token, token inválido
			throw new AuthenticationFailException("Token inválido");
		}
	}
}
