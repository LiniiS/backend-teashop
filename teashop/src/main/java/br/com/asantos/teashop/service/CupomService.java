package br.com.asantos.teashop.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asantos.teashop.dto.CupomDto;
import br.com.asantos.teashop.dto.pedido.TrocaItemPedidoDto;
import br.com.asantos.teashop.model.Cupom;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.TrocaItemPedido;
import br.com.asantos.teashop.repository.CupomRepository;
import br.com.asantos.teashop.repository.TrocaItemPedidoRepository;
import br.com.asantos.teashop.repository.cliente.ClienteRepository;

@Service
public class CupomService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	CupomRepository cupomRepository;

	@Autowired
	TrocaItemPedidoRepository trocaItemRepository;

	@Transactional
	public CupomDto criaCupomAposTrocaDeItem(TrocaItemPedidoDto trocaItemPedido) {
		Cupom novoCupom = new Cupom();
		TrocaItemPedido itemTrocado = trocaItemRepository.getById(trocaItemPedido.getId());
	
		// inserir no cupom: valor, cliente, codigo do cupom
		// codigo do cupom será NOMEDOCLIENTE+ID DO ITEM NO PEDIDO
		novoCupom.setCliente(clienteRepository.getById(trocaItemPedido.getClienteId()));
		novoCupom.setCodigo(criaCodigoCupom(trocaItemPedido));
		novoCupom.setValor(itemTrocado.getItemPedido().getValor());
		
		cupomRepository.save(novoCupom);
		//o status tá em String, mudar pra Enum 
		itemTrocado.setStatus("TROCA_AUTORIZADA");
		

		return new CupomDto(novoCupom);
	}

	/**
	 * O código gerado após a autorização da troca será o nome do cliente + o id do
	 * item NOME1
	 * 
	 * @param trocaItemPedido
	 * @return
	 */

	private String criaCodigoCupom(TrocaItemPedidoDto trocaItemPedido) {

		Cliente cliente = clienteRepository.getById(trocaItemPedido.getClienteId());
		TrocaItemPedido itemTrocado = trocaItemRepository.getById(trocaItemPedido.getItemPedidoId());

		String codigoCupom = cliente.getNome().concat(itemTrocado.getId().toString()).toUpperCase().replaceAll(" ", "");
		System.out.println(codigoCupom);

		return codigoCupom;
	}

	@Transactional
	public void atualizaStatusDoItemEmTroca(TrocaItemPedidoDto itemRejeitadoParaTroca) {
		TrocaItemPedido itemRejeitado = trocaItemRepository.getById(itemRejeitadoParaTroca.getId());
		itemRejeitado.setStatus("TROCA_NEGADA");
		trocaItemRepository.save(itemRejeitado);
	}

	
	public List<CupomDto> listaCuponsDoCliente(Cliente cliente) {
		List<Cupom> cuponsDoCliente = cupomRepository.findAllByCliente(cliente);
		
		List<CupomDto> cuponsDtoDoCliente = new ArrayList<CupomDto>();
		for(Cupom cupom : cuponsDoCliente) {
			CupomDto cupomDto = new CupomDto(cupom);
			cuponsDtoDoCliente.add(cupomDto);
		}	
		return cuponsDtoDoCliente;
	}

	
}
