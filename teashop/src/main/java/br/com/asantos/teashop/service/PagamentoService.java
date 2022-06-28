package br.com.asantos.teashop.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.PagamentoDto;
import br.com.asantos.teashop.dto.carrinho.CarrinhoDto;
import br.com.asantos.teashop.model.Pagamento;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.Pedido;
import br.com.asantos.teashop.repository.CarrinhoRepository;
import br.com.asantos.teashop.repository.CupomRepository;
import br.com.asantos.teashop.repository.PagamentoRepository;
import br.com.asantos.teashop.repository.PedidoRepository;
import br.com.asantos.teashop.repository.cliente.CartaoRepository;
import br.com.asantos.teashop.repository.cliente.ClienteRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@SuppressWarnings("unused")
	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	@SuppressWarnings("unused")
	@Autowired
	private CupomRepository cupomRepository;

//	@Transactional
//	public void pagaNovoPedido(Pedido pedido, CarrinhoDto carrinhoDto) {
//		Pagamento pagamento = new Pagamento();
//		PagamentoDto pagamentoDto = new PagamentoDto();
//				
//		pagamento.setPedido(pedido);
//		
//		
//		pagamento.setPedido(pedidoRepository.getById(pagamentoDto.getPedidoId()));
//		pagamento.setCartao(cartaoRepository.getById(pagamentoDto.getCartaoId()));
//		pagamento.setCupom(cupomRepository.getById(pagamentoDto.getCupomId()));
//		pagamento.setCliente(clienteRepository.getById(pedido.getCliente().getId()));
//		pagamento.setTotalAPagar(pagamentoDto.getValorTotal());
//		pagamento.setTotalDesconto(pagamentoDto.getValorDesconto());
//		
//		pagamentoRepository.save(pagamento);		
//		
//	}

	@SuppressWarnings("deprecation")
	@Transactional
	public PagamentoDto efetuaPagamento(PagamentoDto pagamentoDto, Cliente cliente) {
		Pagamento pagamento = new Pagamento();
		
		Pedido pedido = pedidoRepository.findAllByClienteOrderByDtCriacaoDesc(cliente).get(0);

		pagamento.setPedido(pedidoRepository.getById(pedido.getId()));

		pagamento.setCartao(cartaoRepository.getOne(pagamentoDto.getCartaoId()));

	//	pagamento.setCupom(cupomRepository.getById(pagamentoDto.getCupomId()));

		pagamento.setCliente(clienteRepository.getById(cliente.getId()));

		pagamento.setTotalAPagar(pagamentoDto.getValorTotal());

		pagamento.setTotalDesconto(pagamentoDto.getValorDesconto());

		pagamentoRepository.save(pagamento);
		return new PagamentoDto(pagamento);

	}

	public List<PagamentoDto> buscaPagamentos(Cliente cliente) {
		
		List<Pagamento> listaPagamentos = pagamentoRepository.findAllByClienteId(cliente.getId());
		List<PagamentoDto> listaPagamentosDto = new ArrayList<>();
		for(Pagamento pagamento : listaPagamentos) {
			PagamentoDto novoPagamentoDto = new PagamentoDto(pagamento);
			listaPagamentosDto.add(novoPagamentoDto);
		}
		
		return listaPagamentosDto;
	}



}
