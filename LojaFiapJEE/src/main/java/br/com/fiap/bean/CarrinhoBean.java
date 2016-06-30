package br.com.fiap.bean;


import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Ajax;

import br.com.fiap.business.Calculos;
import br.com.fiap.dao.GenericDao;
import br.com.fiap.dto.Carrinho;
import br.com.fiap.entity.Produto;


@SessionScoped
@ManagedBean
public class CarrinhoBean {
	private static final int CARRINHO_MAX_PARCELAS = 10;
	GenericDao<Produto> produtoDao;
	HttpSession session;



	@ManagedProperty("#{freteValues}")
	FreteValues freteValues;

	public FreteValues getFreteValues() {
		return freteValues;
	}

	public void setFreteValues(FreteValues freteValues) {
		this.freteValues = freteValues;
	}

	ArrayList<Produto> listProdutosSemEstoque = new ArrayList<>();

	public ArrayList<Produto> getListProdutosSemEstoque() {
		return listProdutosSemEstoque;
	}

	public void setListProdutosSemEstoque(ArrayList<Produto> listProdutosSemEstoque) {
		this.listProdutosSemEstoque = listProdutosSemEstoque;
	}

	private ArrayList<Long> itemCarrinhoKeySetList = new ArrayList<>();

	private String qtdItens;
	private Double valorBoleto;
	private Double valorCC;
	private Double valorParcela;

	@PostConstruct
	public void init() {
		produtoDao = new GenericDao<>(Produto.class);
		 calcularValor();


	}

	public String verificarPedido(){

			  return "/checkout/checkout.xhtml?faces-redirect=true";



	}


	public void veriricarProdutosEstoque(){
		if (listProdutosSemEstoque.size()>0) {
			//show dialog sem estoque

		}

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

	public void calcularValor() {

		System.out.println("CALCULAR"+ freteValues.isFreteOk());

		if (freteValues.isFreteOk()) {
			System.out.println("CALCULAR OK");
			valorBoleto =  Calculos.calcularValorBoleto(carrinho.getValorTotal(), freteValues.getValorFreteEscolhido());
			valorParcela =  Calculos.calcularParcelasSemJurosCartao(carrinho.getValorTotal(), freteValues.getValorFreteEscolhido(), CARRINHO_MAX_PARCELAS);
			valorCC =    Calculos.calcularValorCartao(carrinho.getValorTotal(), freteValues.getValorFreteEscolhido());
		}
		Ajax.update("@(.updateFrete)");


	}





	public void updateSession(Carrinho carrinho){
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("carrinho", carrinho);
	}

	Carrinho carrinho;
	public CarrinhoBean() {
		// TODO Auto-generated constructor stub
		qtdItens = "0";
		carrinho = new Carrinho();
		produtoDao = new GenericDao<>(Produto.class);

	}

	public void removeAll(){
		carrinho.getItemCarrinhoMap().clear();
		itemCarrinhoKeySetList.clear();
		carrinho.updateCarrinho();
		updateSession(carrinho);
		Ajax.update("listCarrinho");
		Ajax.update("carrinho");
	}

	public void putItem(Long idProduto, int qtd){

		Produto produto = produtoDao.buscarById(idProduto);

		if (hasEstoque(produto, qtd)) {

			carrinho.putItemCarrinho(idProduto, qtd,produto);
			itemCarrinhoKeySetList = new ArrayList<>(carrinho.getItemCarrinhoMap().keySet());

			System.out.println("Has estoque");
		}else{
			// SHOW DIALOG mostra sem estqoue
		}

		updateSession(carrinho);
		Ajax.update("carrinho");
		Ajax.update("listCarrinho");
	}

	public void removeItem(Long idProduto){

		System.out.println("AJAX");
		carrinho.removeItemCarrinho(idProduto);
		itemCarrinhoKeySetList = new ArrayList<>(carrinho.getItemCarrinhoMap().keySet());
		updateSession(carrinho);
		Ajax.update("listCarrinho");
		Ajax.update("carrinho");
	}
	public boolean hasEstoque(Produto produto, int qtd){
		return produto.getEstoque() >= qtd;
	}






	//GETTER SETTER


	public void setQtdItens(String qtdItens) {
		this.qtdItens = qtdItens;
	}

	public String getQtdItens() {
		return qtdItens.equals("0") ? "" : qtdItens;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public ArrayList<Long> getItemCarrinhoKeySetList() {
		return itemCarrinhoKeySetList;
	}

	public void setItemCarrinhoKeySetList(ArrayList<Long> itemCarrinhoKeySetList) {
		this.itemCarrinhoKeySetList = itemCarrinhoKeySetList;
	}

	public Double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(Double valorParcela) {
		this.valorParcela = valorParcela;
	}



}
