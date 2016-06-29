package br.com.fiap.bean;


import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Ajax;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.dto.Carrinho;
import br.com.fiap.entity.Produto;

@ManagedBean
@SessionScoped
public class CarrinhoBean {
	GenericDao<Produto> produtoDao;
	HttpSession session;
	@ManagedProperty("#{freteBean}")
	FreteBean freteBean;
	ArrayList<Produto> listProdutosSemEstoque = new ArrayList<>();

	public ArrayList<Produto> getListProdutosSemEstoque() {
		return listProdutosSemEstoque;
	}

	public void setListProdutosSemEstoque(ArrayList<Produto> listProdutosSemEstoque) {
		this.listProdutosSemEstoque = listProdutosSemEstoque;
	}

	private ArrayList<Long> itemCarrinhoKeySetList = new ArrayList<>();

	private String qtdItens;

	@PostConstruct
	public void init() {
		produtoDao = new GenericDao<>(Produto.class);

	}

	public String verificarPedido(){

		if (freteBean.isFreteOk()) {

			  return "/checkout/checkout.xhtml?faces-redirect=true";

		}else{
			//show dialog ERRO FRETE
			return null;
		}

	}

	public void veriricarProdutosEstoque(){
		if (listProdutosSemEstoque.size()>0) {
			//show dialog sem estoque

		}

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

	public FreteBean getFreteBean() {
		return freteBean;
	}

	public void setFreteBean(FreteBean freteBean) {
		this.freteBean = freteBean;
	}




}
