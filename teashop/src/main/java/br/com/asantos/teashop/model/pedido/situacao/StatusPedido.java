package br.com.asantos.teashop.model.pedido.situacao;

import br.com.asantos.teashop.exceptions.StatusPedidoException;
import br.com.asantos.teashop.model.pedido.Pedido;

public abstract class StatusPedido {
	
	public void aprovar(Pedido pedido) {
		throw new StatusPedidoException("Pedido não pode ser aprovado!");
	}
	
	public void reprovar(Pedido pedido) {
		throw new StatusPedidoException("Pedido não pode ser reprovado!");
	}
	
	public void finalizar(Pedido pedido) {
		throw new StatusPedidoException("Pedido não pode ser finalizado!");
	}

}
