package br.com.fiap.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.LazyDataModel;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Produto;

@ViewScoped
@ManagedBean
public class CategoriaBean {


	private String searchValue;



	HttpSession session;
	GenericDao<Produto> produtoDao;
	private List<Produto> listProduto;
	private List<Produto> listProdutoQuery = new ArrayList<>();
	private LazyDataModel<Produto> listProdutoLazy;


	@PostConstruct
	public void init(){

		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		produtoDao = new GenericDao<>(Produto.class);
		listProduto = produtoDao.listar();
	}

	public String pesquisarProdutos(){
		listProdutoQuery = produtoDao.pesquisar(searchValue, "CATEGORIA");
		return "/categoria/categoria.xhtml?faces-redirect=true";

	}


	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public GenericDao<Produto> getProdutoDao() {
		return produtoDao;
	}

	public void setProdutoDao(GenericDao<Produto> produtoDao) {
		this.produtoDao = produtoDao;
	}

	public List<Produto> getListProduto() {
		return listProduto;
	}

	public void setListProduto(List<Produto> listProduto) {
		this.listProduto = listProduto;
	}

	public LazyDataModel<Produto> getListProdutoLazy() {
		return listProdutoLazy;
	}

	public void setListProdutoLazy(LazyDataModel<Produto> listProdutoLazy) {
		this.listProdutoLazy = listProdutoLazy;
	}


	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public List<Produto> getListProdutoQuery() {
		return listProdutoQuery;
	}

	public void setListProdutoQuery(List<Produto> listProdutoQuery) {
		this.listProdutoQuery = listProdutoQuery;
	}




}
