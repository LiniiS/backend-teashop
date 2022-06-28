package br.com.asantos.teashop.service.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.cliente.EnderecoDto;
import br.com.asantos.teashop.exceptions.EnderecoNotExistException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.cliente.endereco.Endereco;
import br.com.asantos.teashop.repository.cliente.EnderecoRepository;

/**
 * Classe responsável por orquestrar os serviços solicitados via Controller para
 * gerenciar as ações referentes aos Endereços do Cliente
 * 
 * @author Aline Santos
 *
 */
@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	/**
	 * Método para listar todos os enderecos cadastrados para o cliente
	 * 
	 * @return
	 */
	public List<EnderecoDto> listaEnderecos() {
		List<Endereco> enderecos = enderecoRepository.findAll();
		List<EnderecoDto> enderecoDtos = new ArrayList<EnderecoDto>();
		for (Endereco endereco : enderecos) {
			EnderecoDto enderecoDto = getDtoDoEndereco(endereco);
			enderecoDtos.add(enderecoDto);
		}
		return enderecoDtos;
	}

	/**
	 * método auxiliar para converter um Endereco em EnderecoDto
	 * 
	 * @param endereco
	 * @return
	 */
	private static EnderecoDto getDtoDoEndereco(Endereco endereco) {
		EnderecoDto enderecoDto = new EnderecoDto(endereco);
		return enderecoDto;
	}

	/**
	 * Método auxiliar para converter um EnderecoDto em Endereco dado um cliente
	 * 
	 * @param enderecoDto
	 * @param cliente
	 * @return
	 */
	private static Endereco getEnderecoDoDto(EnderecoDto enderecoDto, Cliente cliente) {
		Endereco endereco = new Endereco(enderecoDto, cliente);
		return endereco;
	}

	/**
	 * Método para persistir os dados de endereco do cliente
	 * 
	 * @param enderecoDto
	 * @param cliente
	 */
	public void adicionaEndereco(EnderecoDto enderecoDto, Cliente cliente) {
		Endereco endereco = getEnderecoDoDto(enderecoDto, cliente);
		enderecoRepository.save(endereco);
	}

	/**
	 * Método para atualizar os dados de um endereco já cadastrado dado seu ID
	 * 
	 * @param enderecoId
	 * @param enderecoDto
	 * @param cliente
	 */
	public void atualizaEndereco(Long enderecoId, EnderecoDto enderecoDto, Cliente cliente) {
		Endereco endereco = getEnderecoDoDto(enderecoDto, cliente);
		endereco.setId(enderecoId);
		enderecoRepository.save(endereco);
	}

	/**
	 * Método para buscar um Endereco cadastrado dado um ID, em caso de
	 * inconsistencia no ID uma exceção será lançada
	 * 
	 * @param enderecoId
	 * @return
	 * @throws EnderecoNotExistException
	 */
	public Endereco buscaEndereco(Long enderecoId) throws EnderecoNotExistException {
		Optional<Endereco> optionalEndereco = enderecoRepository.findById(enderecoId);
		if (!optionalEndereco.isPresent())
			throw new EnderecoNotExistException("ID não encontrado. Cadastro inválido");
		return optionalEndereco.get();
	}

	/**
	 * Método para listar todos os endereços associados ao cliente em sessão
	 * 
	 * @param clienteId
	 * @return
	 */
	public List<EnderecoDto> buscaEnderecos(Long clienteId) {
		List<Endereco> enderecos = enderecoRepository.findAllByClienteId(clienteId);
		List<EnderecoDto> enderecoDtos = new ArrayList<EnderecoDto>();
		for (Endereco endereco : enderecos) {
			EnderecoDto enderecoDto = getDtoDoEndereco(endereco);
			enderecoDtos.add(enderecoDto);
		}
		return enderecoDtos;
	}

	/**
	 * Método para deletar um Endereço cadastrado dado um ID, em caso de
	 * inconsistencia no ID uma exceção será lançada
	 * 
	 * @param enderecoId
	 * @throws EnderecoNotExistException
	 */
	public void deletaEndereco(Long enderecoId) throws EnderecoNotExistException {
		Optional<Endereco> optionalEndereco = enderecoRepository.findById(enderecoId);
		if (!optionalEndereco.isPresent())
			throw new EnderecoNotExistException("Endereço não pode ser removido. ID inválido");
		enderecoRepository.deleteById(enderecoId);
	}

	/**
	 * método de teste
	 * 
	 * @param enderecoDto
	 * @param cliente
	 */
	public void adiciona(EnderecoDto enderecoDto, Cliente cliente) {
		Endereco endereco = getEnderecoDoDto(enderecoDto, cliente);
		enderecoRepository.save(endereco);
	}
}
