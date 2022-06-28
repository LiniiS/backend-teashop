package br.com.asantos.teashop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import br.com.asantos.teashop.dto.carrinho.CarrinhoDto;
import br.com.asantos.teashop.dto.carrinho.ItemCarrinhoDto;
import br.com.asantos.teashop.dto.pedido.ItemCheckoutDto;
import br.com.asantos.teashop.exceptions.PedidoNotFoundException;
import br.com.asantos.teashop.model.cliente.Cliente;
import br.com.asantos.teashop.model.pedido.ItemPedido;
import br.com.asantos.teashop.model.pedido.Pedido;
import br.com.asantos.teashop.model.pedido.situacao.estados.Estado;
import br.com.asantos.teashop.repository.ItemPedidoRepository;
import br.com.asantos.teashop.repository.PedidoRepository;

@Service
@Transactional
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CarrinhoService carrinhoService;

	@Autowired
	PagamentoService pagamentoService;

	@Autowired
	ItemPedidoService itemPedidoService;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	/**
	 * Método que cria o Pedido do Cliente a partir dos itens que ele adiciona ao
	 * carrinho durante a sessão, após criado o Pedido, os itens do carrinho serão
	 * removidos
	 * 
	 * @param cliente
	 * @param sessaoId
	 */

	public void criaPedido(Cliente cliente, String sessaoId) {
		CarrinhoDto carrinhoDto = carrinhoService.listaItensDoCarrinho(cliente);

		List<ItemCarrinhoDto> listaItemCarrinhoDto = carrinhoDto.getItensCarrinho();

		// monta o pedido com base nos items que estão no carrinho
		// TODO mudar para PedidoDto
		Pedido novoPedido = new Pedido();

		novoPedido.setStatus(Estado.EM_PROCESSAMENTO);
		novoPedido.setDtCriacao(new Date());
		novoPedido.setValorTotal(carrinhoDto.getCustoTotal());
		novoPedido.setCliente(cliente);
		novoPedido.setSessao_id(sessaoId);
		pedidoRepository.save(novoPedido);

		// percorre todos os itens do carrinho desta sessão
		// transfere o item do carrinho -> item do pedido

		for (ItemCarrinhoDto itemCarrinhoDto : listaItemCarrinhoDto) {
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setDtCriacao(new Date());
			itemPedido.setValor(itemCarrinhoDto.getProduto().getPreco());
			itemPedido.setQuantidade(itemCarrinhoDto.getQuantidade());
			itemPedido.setProduto(itemCarrinhoDto.getProduto());
			itemPedido.setStatus(novoPedido.getStatus());
			itemPedido.setPedido(novoPedido);

			itemPedidoRepository.save(itemPedido);
			System.out.println(itemPedido.getStatus());
		}
		carrinhoService.deletaItensCarrinhoDoCliente(cliente);
	}

	// ------------- TESTE ---------------------

	@Transactional
	public void criaNovoPedido(Cliente cliente) {

		CarrinhoDto carrinhoDto = carrinhoService.listaItensDoCarrinho(cliente);
		List<ItemCarrinhoDto> listaItemCarrinhoDto = carrinhoDto.getItensCarrinho();
		Pedido novoPedido = new Pedido();

		novoPedido.setStatus(Estado.EM_PROCESSAMENTO);
		novoPedido.setDtCriacao(new Date());
		novoPedido.setValorTotal(carrinhoDto.getCustoTotal());
		novoPedido.setCliente(cliente);
		novoPedido.setSessao_id(UUID.randomUUID().toString());
		pedidoRepository.save(novoPedido);

		// percorre todos os itens do carrinho desta sessão
		// transfere o item do carrinho -> item do pedido

		for (ItemCarrinhoDto itemCarrinhoDto : listaItemCarrinhoDto) {
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setDtCriacao(new Date());
			itemPedido.setValor(itemCarrinhoDto.getProduto().getPreco());
			itemPedido.setQuantidade(itemCarrinhoDto.getQuantidade());
			itemPedido.setProduto(itemCarrinhoDto.getProduto());
			itemPedido.setStatus(novoPedido.getStatus());
			itemPedido.setPedido(novoPedido);

			itemPedidoRepository.save(itemPedido);
			System.out.println(itemPedido.getStatus());
		}
		carrinhoService.deletaItensCarrinhoDoCliente(cliente);

		

	}

	// ---------------
	public List<Pedido> listaPedidos(Cliente cliente) {
		return pedidoRepository.findAllByClienteOrderByDtCriacaoDesc(cliente);
	}

	public Pedido buscaPedido(Long pedidoId) throws PedidoNotFoundException {
		Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);
		if (pedido.isPresent()) {
			return pedido.get();
		}
		throw new PedidoNotFoundException("Pedido não encontrado");
	}

	/**
	 * Seção de Serviços de Pagamento via Stripe
	 */

	// usar a baseURL e a SecretKey
	@Value("${BASE_URL}")
	private String baseURL;

	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;

	// https://stripe.com/docs/api/checkout/sessions?lang=java

	public Session criaSessao(List<ItemCheckoutDto> listaItemCheckoutDto) throws StripeException {

		// páginas no FrontEnd pra lidar com cada situação após o pagamento:
		String sucessoURL = baseURL + "pagamento/sucesso";
		String erroURL = baseURL + "pagamento/erro";

		Stripe.apiKey = secretKey;

		// cria os parâmetros que serão aplicados a cada item no pedido que está nesta
		// sessão de checkout

		List<SessionCreateParams.LineItem> sessaoListaItems = new ArrayList<>();

		// pra cada item na sessão de Checkout (usando o DTO), percorra a lista:
		for (ItemCheckoutDto itemCheckoutDto : listaItemCheckoutDto) {
			// e cria adiciona os itens do pedido a partir da sessão pra essa linha da lista
			sessaoListaItems.add(criaSessaoLineItem(itemCheckoutDto));
		}

		// define-se os parâmetros de pagamento como: métodos de pgto, descontos,
		// códigos promocionais
		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT).setCancelUrl(erroURL).setSuccessUrl(sucessoURL)
				.addAllLineItem(sessaoListaItems).build();

		return Session.create(params);

	}

	/**
	 * Método pra construir o preço dos items na linha dentro da lista dos itens na
	 * sessão de Checkout
	 * 
	 * @param itemCheckoutDto
	 * @return
	 */
	private SessionCreateParams.LineItem criaSessaoLineItem(ItemCheckoutDto itemCheckoutDto) {
		return SessionCreateParams.LineItem.builder().setPriceData(criaDadosPreco(itemCheckoutDto))
				.setQuantity(Long.valueOf(String.valueOf(itemCheckoutDto.getQuantidade()))).build();

	}

	/**
	 * Método para criar os dados do item na lista de items da sessão de checkout
	 * atribuindo ao item o seu preço, na moeda {BRL} e os dados extras pra serem
	 * exibidos na tela de checkout.
	 * 
	 * @param itemCheckoutDto
	 * @return
	 */
	private SessionCreateParams.LineItem.PriceData criaDadosPreco(ItemCheckoutDto itemCheckoutDto) {
		return SessionCreateParams.LineItem.PriceData.builder()
				// refatorar pra deixar dinâmico e não hardcoded - BRL o American Express naõ
				// aceita, e no modelo dos cartões a bandeira é aceita (rever a consistência do
				// sistema)
				.setCurrency("BRL")
				// The unit amount in centavo to be charged, represented as a whole integer if
				// possible.
				.setUnitAmount((long) itemCheckoutDto.getPreco() * 100)
				.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
						.setName(itemCheckoutDto.getProduto()).build())
				.build();
	}

	@Transactional
	public Pedido buscaNovoPedido(Cliente cliente) {
		
		Pedido pedido = pedidoRepository.findAllByClienteIdOrderByDtCriacaoDesc(cliente.getId()).get(0);

		return pedido;
	}

}
