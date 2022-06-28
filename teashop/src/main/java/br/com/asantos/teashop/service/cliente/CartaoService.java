package br.com.asantos.teashop.service.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.cliente.CartaoDto;
import br.com.asantos.teashop.exceptions.CartaoNotExistException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.cliente.cartao.Cartao;
import br.com.asantos.teashop.repository.cliente.CartaoRepository;

/**
 * Classe responsável por orquestrar os serviços solicitados via Controller para
 * gerenciar as ações referentes aos Cartões do Cliente
 * 
 * @author Aline Santos
 *
 */
@Service
@Transactional
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;

	/**
	 * Método para listar os cartoes do cliente convertendo-os de Cartao para
	 * CartaoDto
	 * 
	 * @param clienteId
	 * @return
	 */

	public List<CartaoDto> buscaCartoes(Long clienteId) {
		List<Cartao> cartoes = cartaoRepository.findAllByClienteId(clienteId);
		List<CartaoDto> cartaoDtos = new ArrayList<CartaoDto>();
		for (Cartao cartao : cartoes) {
			CartaoDto cartaoDto = getDtoDoCartao(cartao);
			cartaoDtos.add(cartaoDto);
		}
		return cartaoDtos;
	}

	/**
	 * Método auxiliar para popular um Cartao extraindo os dados do DTO
	 * 
	 * @param cartao
	 * @return
	 */
	private static CartaoDto getDtoDoCartao(Cartao cartao) {
		CartaoDto cartaoDto = new CartaoDto(cartao);
		return cartaoDto;
	}

	/**
	 * Método auxiliar para extrair os dados do DTO e popular um Cartao
	 * 
	 * @param cartaoDto
	 * @param cliente
	 * @return
	 */
	private static Cartao getCartaoDoDto(CartaoDto cartaoDto, Cliente cliente) {
		Cartao cartao = new Cartao(cartaoDto, cliente);
		return cartao;
	}

	/**
	 * Método para adicionar um novo cartão associando-o a um cliente
	 * 
	 * @param cartaoDto
	 * @param cliente
	 */
	public void adicionaCartao(CartaoDto cartaoDto, Cliente cliente) {
		Cartao cartao = getCartaoDoDto(cartaoDto, cliente);
		cartaoRepository.save(cartao);
	}

	/**
	 * Método para atualizar os dados do Cartao do cliente
	 * 
	 * @param cartaoId
	 * @param cartaoDto
	 * @param cliente
	 */

	public void atualizaCartao(Long cartaoId, CartaoDto cartaoDto, Cliente cliente) {
		Cartao cartao = getCartaoDoDto(cartaoDto, cliente);
		cartao.setId(cartaoId);
		cartaoRepository.save(cartao);
	}

	/**
	 * Método para deletar um Cartão cadastrado dado um ID, caso o ID seja
	 * inconsistente uma exceção será lançada
	 * 
	 * @param cartaoId
	 * @throws CartaoNotExistException
	 */
	public void deletaCartao(Long cartaoId) {
		Optional<Cartao> optionalCartao = cartaoRepository.findById(cartaoId);
		if (!optionalCartao.isPresent())
			throw new CartaoNotExistException("Cartão não pode ser removido. ID inválido.");
		cartaoRepository.deleteById(cartaoId);
	}

	/**
	 * Método para adicionar um cartão considerando o token e não o cliente Id
	 * (17/04)
	 * 
	 * @param cartaoDto
	 * @param cliente
	 */

	public void adiciona(CartaoDto cartaoDto, Cliente cliente) {
		Cartao cartao = getCartaoDoDto(cartaoDto, cliente);
		cartaoRepository.save(cartao);
	}
}
