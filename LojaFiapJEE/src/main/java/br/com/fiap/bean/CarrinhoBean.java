package br.com.fiap.bean;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;

import br.com.fiap.business.Calculos;
import br.com.fiap.dao.GenericDao;
import br.com.fiap.dto.Carrinho;
import br.com.fiap.entity.Cliente;
import br.com.fiap.entity.Endereco;
import br.com.fiap.entity.Produto;


@SessionScoped
@ManagedBean
public class CarrinhoBean {
	private static final int CARRINHO_MAX_PARCELAS = 10;
	GenericDao<Produto> produtoDao;
	HttpSession session;

	GenericDao<Cliente> clienteDao = new GenericDao<>(Cliente.class);


	@ManagedProperty("#{clienteBean}")
	ClienteBean clienteBean;


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



	}



	public void validarCheckout(){
		Endereco enderecoSessionTemp;
		Endereco enderecoBancoTemp;
		enderecoSessionTemp = clienteBean.getCliente().getEndereco();
		enderecoSessionTemp.setCliente(null);
		enderecoBancoTemp =  clienteDao.buscarById(clienteBean.getCliente().getId()).getEndereco();
		enderecoBancoTemp.setCliente(null);
		if (clienteBean!=null) {
			if (clienteBean.getCliente() != null) {

				if (clienteBean.validaCliente()) {

					if (enderecoBancoTemp.compareTo(enderecoSessionTemp) != 0) {
						//novo endereço
						RequestContext.getCurrentInstance().execute("confirmarNovoEnderecoDialog.showModal();");
						System.out.println("new ENDEREÇO");


						return;
					}else if (enderecoBancoTemp.compareTo(enderecoSessionTemp) == 0) {
						//mesmo endereço
						System.out.println(" //mesmo endereço");
						try {
							FacesContext.getCurrentInstance().getExternalContext().redirect("../checkout/checkout.xhtml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;

					}else{
						System.out.println("erroCheckoutDialog");
						RequestContext.getCurrentInstance().execute("erroCheckoutDialog.showModal();");
						return;

					}
				}else{
					//show modal cadastro invalidol
					RequestContext.getCurrentInstance().execute("erroCadastroDialog.showModal();");
					return;
				}

			}else{
				System.out.println("erroCheckoutDialog");
				RequestContext.getCurrentInstance().execute("erroCheckoutDialog.showModal();");
				return;
			}


		}else{
			System.out.println("erroCheckoutDialog");
			RequestContext.getCurrentInstance().execute("erroCheckoutDialog.showModal();");

		}

		enderecoBancoTemp = null;
		enderecoSessionTemp = null;

	}

	public String continuarCheckoutNovoEnd(){

		clienteDao.update(clienteBean.getCliente());

		return "/checkout/checkout.xhtml?faces-redirect=true";

	}


	public GenericDao<Produto> getProdutoDao() {
		return produtoDao;
	}

	public void setProdutoDao(GenericDao<Produto> produtoDao) {
		this.produtoDao = produtoDao;
	}

	public ClienteBean getClienteBean() {
		return clienteBean;
	}

	public void setClienteBean(ClienteBean clienteBean) {
		this.clienteBean = clienteBean;
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

		valorBoleto =  Calculos.calcularValorBoleto(carrinho.getValorTotal(), freteValues.getValorFreteEscolhido());
		valorParcela =  Calculos.calcularParcelasSemJurosCartao(carrinho.getValorTotal(), freteValues.getValorFreteEscolhido(), CARRINHO_MAX_PARCELAS);
		valorCC =    Calculos.calcularValorCartao(carrinho.getValorTotal(), freteValues.getValorFreteEscolhido());

		System.out.println("CALC VALOR: getValorFreteEscolhido "+  freteValues.getValorFreteEscolhido());
		System.out.println("CALC VALOR: BOLETO "+  valorBoleto);
		System.out.println("CALC VALOR: valorParcela "+  valorParcela);
		System.out.println("CALC VALOR: valorCC "+  valorCC);

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
		//Ajax.update("listCarrinho");
		//Ajax.update("carrinho");
		//Ajax.update("endereco");
	}

	public String putItem( Long produtoId, int qtd, boolean abrirCarrinho){



		Produto produto = produtoDao.buscarById(produtoId);
		if (hasEstoque(produto, qtd)) {
			carrinho.putItemCarrinho(produtoId, qtd,produto);
			itemCarrinhoKeySetList = new ArrayList<>(carrinho.getItemCarrinhoMap().keySet());
			System.out.println("Has estoque");
		}else{
			// SHOW DIALOG mostra sem estqoue
			System.out.println("SEM ESTOQUE");
			System.out.println("ESATOQUE: "+produto.getEstoque());
		}
		updateSession(carrinho);

		if (abrirCarrinho) {
			return "/carrinho/carrinho.xhtml?faces-redirect=true";
		}
		return null;
	}


	public  void alterarQuantidade(){
		Ajax.update("carrinho");
		Ajax.update("listCarrinho");
		Ajax.update("endFrete");

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String,String> params = context.getExternalContext().getRequestParameterMap();


		System.out.println("ALETARA + "+params.get("produtoId"));
		System.out.println("ALETARA + "+params.get("qtd"));
		putItem(Long.parseLong(params.get("produtoId")), Integer.parseInt(params.get("qtd"))  ,false);


	}

	public void removeItem(Long idProduto){

		System.out.println("AJAX");
		carrinho.removeItemCarrinho(idProduto);
		itemCarrinhoKeySetList = new ArrayList<>(carrinho.getItemCarrinhoMap().keySet());
		updateSession(carrinho);
		Ajax.update("listCarrinho");
		Ajax.update("carrinho");
		if (itemCarrinhoKeySetList.isEmpty()) {
			Ajax.update("endFrete");
		}


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
