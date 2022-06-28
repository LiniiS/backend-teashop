package br.com.asantos.teashop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Essa classe é responsável por lidar com exceções globais da aplicação
 * associadas aos controllers
 * 
 * @author Aline S
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	/**
	 * Handler que define a resposta de retorno das anomalias ao longo do fluxo da
	 * aplicação com mensagem customizada
	 * 
	 * @param exception
	 * @return
	 */

	@ExceptionHandler(value = CustomException.class)
	public final ResponseEntity<String> handleCustomException(CustomException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler lida com a resposta aos eventos de falhas ao realizar login
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = AuthenticationFailException.class)
	public final ResponseEntity<String> handleAuthenticationFailExcepion(AuthenticationFailException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler lida com a resposta ao evento de falhas ao buscar por um id não
	 * associado a um cliente (id inválido/inexistente)
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = ClienteNotExistException.class)
	public final ResponseEntity<String> handleClienteNotExistException(ClienteNotExistException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handler lida com a resposta ao evento de falhas ao buscar por um id não
	 * associado a um produto (inválido/inexistente)
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = ProdutoNotExistException.class)
	public final ResponseEntity<String> handleProdutoNotExistException(ProdutoNotExistException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler lida com a resposta ao evento de falhas ao buscar por um id não
	 * associado a um endereco de cliente cadastrado (inválido/inexistente)
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = EnderecoNotExistException.class)
	public final ResponseEntity<String> handleEnderecoNotExistException(EnderecoNotExistException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler lida com a resposta ao evento de falhas ao buscar por um id não
	 * associado a um cartão de cliente cadastrado (inválido/inexistente)
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value=CartaoNotExistException.class)
	public final ResponseEntity<String> handleCartaoNotExistException(CartaoNotExistException exception){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
