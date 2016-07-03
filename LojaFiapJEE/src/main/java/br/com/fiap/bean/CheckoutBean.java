package br.com.fiap.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.fiap.business.Calculos;
import br.com.fiap.dao.GenericDao;
import br.com.fiap.dto.Carrinho;
import br.com.fiap.dto.ItemCarrinho;
import br.com.fiap.entity.Cliente;
import br.com.fiap.entity.Item;
import br.com.fiap.entity.Pedido;
import br.com.fiap.entity.Produto;
@ManagedBean
@RequestScoped
public class CheckoutBean  {



	public CarrinhoBean getCarrinhoBean() {
		return carrinhoBean;
	}


	public void setCarrinhoBean(CarrinhoBean carrinhoBean) {
		this.carrinhoBean = carrinhoBean;
	}

	HttpSession session;
	Carrinho carrinho;
	private Double valor;
	public Double getValor() {
		return valor;
	}


	public void setValor(Double valor) {
		this.valor = valor;
	}

	private Double valorBoleto;
	private Double valorCC;


	private GenericDao<Pedido> pedidoDao;

	private GenericDao<Item> itemDao;
	private GenericDao<Cliente> clienteDao;


	@ManagedProperty("#{estoqueBean}")
	EstoqueBean estoqueBean;

	@ManagedProperty("#{carrinhoBean}")
	CarrinhoBean carrinhoBean;

	@ManagedProperty("#{freteValues}")
	FreteValues freteValues;


	@PostConstruct
	public void init(){
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		carrinho = (Carrinho) session.getAttribute("carrinho");
		itemDao = new GenericDao<>(Item.class);
		clienteDao = new GenericDao<>(Cliente.class);
		pedidoDao = new GenericDao<>(Pedido.class);
		calcularValorBoleto();
		calcularValorCC();


	}


	private void calcularValorBoleto() {
		valorBoleto = Calculos.calcularValorBoleto(carrinhoBean.getCarrinho().getValorTotal(), freteValues.getValorFreteEscolhido());
	}


	private void calcularValorCC() {
		valorCC = carrinho.getValorTotal();
		valorCC = valorCC + freteValues.getValorFreteEscolhido();

	}



	public String gerarPedidoBoleto(){

		ArrayList<Produto> listProdutosSemEstoque = estoqueBean.validaEstoque(new ArrayList<>(carrinho.getItemCarrinhoMap().values()));

		if (!listProdutosSemEstoque.isEmpty()) {
			estoqueBean.descontarEstoque(new ArrayList<>(carrinho.getItemCarrinhoMap().values()));
			Pedido pedido = new Pedido();
			pedido.setData(new Date());
			for (ItemCarrinho ic : carrinho.getItemCarrinhoMap().values()) {
				Item item = new Item(pedido,ic.getProduto(),ic.getQuantidade(),ic.getValor());
				itemDao.adicionar(item);
				pedido.getItens().add(item);
			}
			pedido.setTotal(valorBoleto);
			pedido.setTipo(0);
			pedidoDao.adicionar(pedido);
		}else{
			//sem estoque em um dos produtos
			carrinhoBean.setListProdutosSemEstoque(listProdutosSemEstoque);
			return "/carrinho/carrinho.xhtml?faces-redirect=true";

		}
		return null;


	}

	public void gerarPedidoCartao(){



	}


	public EstoqueBean getEstoqueBean() {
		return estoqueBean;
	}

	public void setEstoqueBean(EstoqueBean estoqueBean) {
		this.estoqueBean = estoqueBean;
	}




	public Double getValorBoleto() {
		return valorBoleto;
	}

	public void setValorBoleto(Double valorBoleto) {
		this.valorBoleto = valorBoleto;
	}

	public Double getValorCC() {
		return valorCC;
	}

	public void setValorCC(Double valorCC) {
		this.valorCC = valorCC;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}



	public GenericDao<Pedido> getPedidoDao() {
		return pedidoDao;
	}

	public void setPedidoDao(GenericDao<Pedido> pedidoDao) {
		this.pedidoDao = pedidoDao;
	}

	public GenericDao<Cliente> getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(GenericDao<Cliente> clienteDao) {
		this.clienteDao = clienteDao;
	}

	public GenericDao<Item> getItemDao() {
		return itemDao;
	}

	public void setItemDao(GenericDao<Item> itemDao) {
		this.itemDao = itemDao;
	}

	public FreteValues getFreteValues() {
		return freteValues;
	}


	public void setFreteValues(FreteValues freteValues) {
		this.freteValues = freteValues;
	}






}
