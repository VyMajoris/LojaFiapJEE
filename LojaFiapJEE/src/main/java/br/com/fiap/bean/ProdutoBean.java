package br.com.fiap.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Produto;

@ManagedBean
@ViewScoped
public class ProdutoBean {

	private Long idProduto;
	private Produto produto;
	private GenericDao<Produto> produtoDao = new GenericDao<>(Produto.class);

	@PostConstruct
	public void init(){


	}

	public void carregarProduto(){
		produto = produtoDao.buscarById(idProduto);
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
