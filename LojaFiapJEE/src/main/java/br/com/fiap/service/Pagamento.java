package br.com.fiap.service;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/pagamento")
public class Pagamento {

	PagamentoListener pagamentoListener;

	public Pagamento(PagamentoListener pagamentoListener) {
		this.pagamentoListener = pagamentoListener;
	}
	public Pagamento() {

	}

	@POST
	@Path("/boleto/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response pagamentoBoleto(@PathParam("id") String id) {

		System.out.println("BOLETOOO");

		if (pagamentoListener.pagamentoBoleto()) {
			return Response.status(200).entity("Obrigado pelo pagamento! id do pedido:" + id).build();
		}else{
			return Response.status(200).entity("Este boleto já foi pago!").build();
		}


	}
	@POST
	@Path("/cartao/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response pagamentoCartao(@PathParam("id") String id) {

		if (pagamentoListener.pagamentoCartao()) {
			return Response.status(200).entity("Obrigado pelo pagamento! id do pedido:" + id).build();
		}else{
			return Response.status(200).entity("Este pedido já foi pago! id do pedido:" + id).build();
		}

	}


	interface PagamentoListener {
		boolean pagamentoBoleto();
		boolean pagamentoCartao();
	}

}
