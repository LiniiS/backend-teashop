package br.com.asantos.teashop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.carrinho.AdicionaAoCarrinhoDto;
import br.com.asantos.teashop.dto.carrinho.CarrinhoDto;
import br.com.asantos.teashop.dto.carrinho.ItemCarrinhoDto;
import br.com.asantos.teashop.exceptions.CarrinhoItemNotExistException;
import br.com.asantos.teashop.model.Carrinho;
import br.com.asantos.teashop.model.Produto;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.repository.CarrinhoRepository;


/**
 * Classe responsável por orquestrar os serviços solicitados via Controller para
 * gerenciar o carrinho e os itens do carrinho do cliente logadoF
 * 
 * @author Aline Santos
 *
 */
@Service
@Transactional
public class CarrinhoService {

	@Autowired
	private CarrinhoRepository carrinhoRepository;

	public CarrinhoService(CarrinhoRepository carrinhoRepository) {
		this.carrinhoRepository = carrinhoRepository;
	}

	/**
	 * Método para adicionar um produto ao carrinho do cliente logado o carrinho é
	 * criado no momento em que o cliente decide adicionar o produto ao carrinho,
	 * que terá o Produto Id, a quantidade desse produto, Cliente Id e data de
	 * criação
	 * 
	 * @param adicionaAoCarrinhoDto
	 * @param produto
	 * @param cliente
	 */
	public void adicionaAoCarrinho(AdicionaAoCarrinhoDto adicionaAoCarrinhoDto, Produto produto, Cliente cliente) {
		Carrinho carrinho = new Carrinho(produto, adicionaAoCarrinhoDto.getQuantidade(), cliente);
		carrinhoRepository.save(carrinho);
	}

	/**
	 * Método para elencar os itens de um carrinho de um cliente logado, o carrinho
	 * mostra o custo total considernado cada item do carrinho com seu preço e
	 * quantidade calculada, retorna um DTO que será apresentado ao cliente
	 * 
	 * @param cliente
	 * @return
	 */
	// retorna todos os itens dentro de um carrinho de compra
	public CarrinhoDto listaItensDoCarrinho(Cliente cliente) {
		// busca a lista de todos os carrinhos do cliente
		List<Carrinho> lstCarrinho = carrinhoRepository.findAllByClienteOrderByDtCriacaoDesc(cliente);

		// cria uma lista de itens do carrinho no formato ItemCarrinhoDto
		List<ItemCarrinhoDto> carrinhoItens = new ArrayList<>();

		// percorre a lista de carrinhos do cliente e
		// para cada carrinho contido na lista do cliente, trazer os dados
		// para criar o dto daquele item do carrinho
		for (Carrinho carrinho : lstCarrinho) {
			ItemCarrinhoDto itemCarrinhoDto = getDtoDoCarrinho(carrinho);
			carrinhoItens.add(itemCarrinhoDto);
		}

		double custoTotal = 0;
		// percorre os itens buscados e para cada item
		// calcula o seu custo total
		// considerando a quantidade e o preço de cada produto contido no carrinho
		for (ItemCarrinhoDto itemCarrinhoDto : carrinhoItens) {
			custoTotal += (itemCarrinhoDto.getProduto().getPreco() * itemCarrinhoDto.getQuantidade());
		}

		return new CarrinhoDto(carrinhoItens, custoTotal);
	}

	/**
	 * Método que cria um item do carrinho a partir dos dados de um carrinho
	 * 
	 * @param carrinho
	 * @return
	 */
	public static ItemCarrinhoDto getDtoDoCarrinho(Carrinho carrinho) {
		return new ItemCarrinhoDto(carrinho);
	}

	/**
	 * Método para atualizar os dados de um carrinho, uma nova data é atribuída o
	 * carrinho passado via dto constroi um novo carrinho com seu id, considerando o
	 * produto, qntidade e cliente passados ao atualizar
	 * 
	 * @param carrinhoDto
	 * @param cliente
	 * @param produto
	 */
	// TODO verificar esse ID que é passado pra vincular o carrinho q ta sendo
	// atualizado
	public void atualizaItemCarrinho(Long carrinhoItemId, AdicionaAoCarrinhoDto carrinhoDto, Cliente cliente,
			Produto produto) {
		// traz os dados de um carrinhoDto pelo seu ID passando-o para carrinho que será
		// construído com os dados atualizados
		Carrinho carrinho = carrinhoRepository.getById(carrinhoItemId);
		carrinho.setQuantidade(carrinhoDto.getQuantidade());
		carrinho.setDtCriacao(new Date());
		carrinhoRepository.save(carrinho);

	}

	/**
	 * Método para remover um item do carrinho do cliente, verifica se o id passado
	 * do carrinho existe antes de remover
	 * 
	 * @param carrinhoId
	 * @param clienteId
	 * @throws CarrinhoItemNotExistException
	 */
	public void deletaItemCarrinho(Long carrinhoId, Long clienteId) throws CarrinhoItemNotExistException {
		if (!carrinhoRepository.existsById(carrinhoId))
			throw new CarrinhoItemNotExistException("Carrinho com id inválido: " + carrinhoId);
		carrinhoRepository.deleteById(carrinhoId);
	}

	/**
	 * Método para remover todos os items (o carrinho em si) de um Cliente
	 * 
	 * @param clienteId
	 */
	public void deletaItensCarrinho(Long clienteId) {
		carrinhoRepository.deleteAll();
	}

	public void deletaItensCarrinhoDoCliente(Cliente cliente) {
		carrinhoRepository.deleteByCliente(cliente);
		
	}
}
