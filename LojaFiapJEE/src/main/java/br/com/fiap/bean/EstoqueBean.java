package br.com.fiap.bean;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import org.hibernate.LockMode;
import org.hibernate.Session;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.dao.HibernateUtil;
import br.com.fiap.dto.ItemCarrinho;
import br.com.fiap.entity.Produto;
import br.com.fiap.entity.ProdutoNoCache;


@Named
@ApplicationScoped
public class EstoqueBean {

	GenericDao<Produto> produtoDao;
	GenericDao<ProdutoNoCache> produtoNoCacheDao;

	@PostConstruct
	public void init(){
		produtoDao = new GenericDao<>(Produto.class);
		produtoNoCacheDao = new GenericDao<>(ProdutoNoCache.class);
	}

	synchronized public void descontarEstoque(ArrayList<ItemCarrinho> itemCarrinho){
		//Session session = HibernateUtil.getSessionFactory().openSession();



		for (ItemCarrinho item : itemCarrinho) {


			ProdutoNoCache produto = produtoNoCacheDao.buscarById(item.getProduto().getId());
			produto.setEstoque(produto.getEstoque()-item.getQuantidade());

			//session.update(produto );
			produtoNoCacheDao.update(produto);
		}
		//	session.close();
	}

	public void rollbackEstoque(){


	}

	synchronized public ArrayList validaEstoque(ArrayList<ItemCarrinho> itemList) {
		ArrayList<ItemCarrinho> listItensSemEstoque = new ArrayList<>();

		Session session = HibernateUtil.getSessionFactory().openSession();

		Produto produto;
		for (ItemCarrinho item : itemList) {
			produto =	(Produto) session.createQuery("from Produto where id ="+item.getProduto().getId()).setLockMode("this", LockMode.PESSIMISTIC_WRITE).setCacheable(false).uniqueResult();
			Long idP = item.getProduto().getId();
			item.setProduto(produto);
			int estoque = produto.getEstoque();
			if (estoque < item.getQuantidade()) {
				listItensSemEstoque.add(item);
			}
		}
		session.close();
		return listItensSemEstoque;
	}

}
