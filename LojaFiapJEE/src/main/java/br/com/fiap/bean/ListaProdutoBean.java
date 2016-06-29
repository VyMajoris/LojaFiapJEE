package br.com.fiap.bean;

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
public class ListaProdutoBean {



	HttpSession session;
	GenericDao<Produto> produtoDao;
	private List<Produto> listProduto;
	private LazyDataModel<Produto> listProdutoLazy;

	int first = 0;
	int pageSize = 10;

	@PostConstruct
	public void init(){
		System.out.println("testesoigmnso");
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		produtoDao = new GenericDao<>(Produto.class);
		listProduto = produtoDao.listar();
		System.out.println("PRODUTOS");
	}

	public ListaProdutoBean() {
		System.out.println("PRODUTO333S");
		// TODO Auto-generated constructor stub

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

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}




}
