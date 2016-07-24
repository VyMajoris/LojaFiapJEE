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

import org.apache.commons.lang3.math.NumberUtils;
import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;

import br.com.fiap.business.Calculos;
import br.com.fiap.dao.GenericDao;
import br.com.fiap.dto.Carrinho;
import br.com.fiap.dto.ItemCarrinho;
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

	ArrayList<ItemCarrinho> listItensSemEstoque = new ArrayList<>();



	public ArrayList<ItemCarrinho> getListItensSemEstoque() {
		return listItensSemEstoque;
	}

	public void setListItensSemEstoque(ArrayList<ItemCarrinho> listItensSemEstoque) {
		this.listItensSemEstoque = listItensSemEstoque;
	}

	private ArrayList<Long> itemCarrinhoKeySetList = new ArrayList<>();

	private String qtdItens;
	private Double valorBoleto;


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

						if (clienteBean.validaCliente()) {
							RequestContext.getCurrentInstance().execute("confirmarNovoEnderecoDialog.showModal();");
						}else{
							RequestContext.getCurrentInstance().execute("erroNovoEndDialog.showModal();");

						}


						return;
					}else if (enderecoBancoTemp.compareTo(enderecoSessionTemp) == 0) {
						//mesmo endereço

						try {
							FacesContext.getCurrentInstance().getExternalContext().redirect("../checkout/checkout.xhtml");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;

					}else{

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
		if (!listItensSemEstoque.isEmpty()) {
			//show dialog sem estoque
			Ajax.update("listProdutosSemEstoque");
			RequestContext.getCurrentInstance().execute("erroProdutosCacheEstoqueDialog.showModal();");



		}

	}

	public Double getValorBoleto() {
		return valorBoleto;
	}

	public void setValorBoleto(Double valorBoleto) {
		this.valorBoleto = valorBoleto;
	}


	public void calcularValor() {

		valorBoleto =  Calculos.calcularValorBoleto(carrinho.getValorTotal(), freteValues.getValorFreteEscolhido());



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
	}

	public String putItem( Long produtoId, int qtd, boolean abrirCarrinho){



		Produto produto = produtoDao.buscarById(produtoId);

		if ( qtd > produto.getEstoque()  ) {
			//sem

			RequestContext.getCurrentInstance().execute("putItemErroEstoqueDialog.showModal();");
		}else{
			//com

			carrinho.putItemCarrinho(produtoId, qtd,produto);
			itemCarrinhoKeySetList = new ArrayList<>(carrinho.getItemCarrinhoMap().keySet());

		}

		updateSession(carrinho);

		if (abrirCarrinho) {
			return "/carrinho/carrinho.xhtml?faces-redirect=true";
		}
		return null;
	}


	public  void alterarQuantidade(){
		String qtd;
		int qtdInt;

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		qtd = params.get("qtd");

		if (!NumberUtils.isNumber(qtd)) {
			RequestContext.getCurrentInstance().execute("erroQtdCarrinhoDialog.showModal();");

		}else{
			qtdInt = Integer.parseInt(qtd);
			Produto produto = produtoDao.buscarById(Long.parseLong(params.get("produtoId")));


			if ( qtdInt > produto.getEstoque()  ) {
				//sem

				RequestContext.getCurrentInstance().execute("erroQtdEstoqueCarrinhoDialog.showModal();");
			}else{
				//com
				putItem(Long.parseLong(params.get("produtoId")),qtdInt ,false);
			}
		}
	}



	public void removeItem(Long idProduto){



		carrinho.removeItemCarrinho(idProduto);
		itemCarrinhoKeySetList.clear();


		itemCarrinhoKeySetList = new ArrayList<>(carrinho.getItemCarrinhoMap().keySet());
		updateSession(carrinho);


		if (itemCarrinhoKeySetList.isEmpty()) {

		}


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



}
