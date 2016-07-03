package br.com.fiap.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.dto.ItemCarrinho;
import br.com.fiap.entity.Produto;
import br.com.fiap.entity.ProdutoDirect;

@Named
@ApplicationScoped
public class EstoqueBean {

	GenericDao<ProdutoDirect> produtoDirectDao;
	ArrayList<Produto> listProdutosSemEstoque;

	@PostConstruct
	public void init(){
		produtoDirectDao = new GenericDao<>(ProdutoDirect.class);
	}

	public void descontarEstoque(ArrayList<ItemCarrinho> collection){

	}

	public void rollbackEstoque(){


	}

	synchronized public ArrayList validaEstoque(ArrayList<ItemCarrinho> itemList) {
		listProdutosSemEstoque = new ArrayList<>();

		for (ItemCarrinho item : itemList) {
			Long idP = item.getProduto().getId();
			int estoque = produtoDirectDao.buscarById(idP).getEstoque();
			if (estoque < item.getQuantidade()) {
				listProdutosSemEstoque.add(item.getProduto());
			}
		}
		return listProdutosSemEstoque;

	}


}