package br.com.fiap.teste;

import java.util.Date;

import javax.faces.context.FacesContext;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.dao.HibernateUtil;
import br.com.fiap.dto.Carrinho;
import br.com.fiap.dto.ItemCarrinho;
import br.com.fiap.entity.Cliente;
import br.com.fiap.entity.Endereco;
import br.com.fiap.entity.Item;
import br.com.fiap.entity.Pedido;
import br.com.fiap.entity.Produto;

public class Teste {
	static GenericDao<Produto> produtodao = new GenericDao<>(Produto.class);
	static GenericDao<Cliente> clienteDao = new GenericDao<>(Cliente.class);
	static GenericDao<Pedido> pedidoDao = new GenericDao<>(Pedido.class);
	static GenericDao<Endereco> enderecoDao = new GenericDao<>(Endereco.class);
	static GenericDao<Item> itemDao = new GenericDao<>(Item.class);
	static Statistics stats;

	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		stats = sessionFactory.getStatistics();
		System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		stats.setStatisticsEnabled(true);
		System.out.println("Stats enabled="+stats.isStatisticsEnabled());



		printStats(stats, 0);



		//test3();

		fillData(100);

	}


	public static void fillData(int i){

		for (int j = 0; j < i; j++) {
			Produto produto = new Produto();
			produto.setDataPublicacao(new Date());
			produto.setDescricao("Descrição "+j);
			produto.setEstoque(j);
			produto.setNome("nome "+j);
			produto.setUrl_imagem("url_imagem "+j);
			produto.setValor(100+j);
			produtodao.adicionar(produto);
		}

	}

	public static void test3(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	public static void test2(){
		enderecoDao.deletarById(2l);
	}

	public static void test1(){

	}
	private static void printStats(Statistics stats, int i) {
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch Count="
				+ stats.getEntityFetchCount());
		System.out.println("Second Level Hit Count="
				+ stats.getSecondLevelCacheHitCount());
		System.out
		.println("Second Level Miss Count="
				+ stats
				.getSecondLevelCacheMissCount());
		System.out.println("Second Level Put Count="
				+ stats.getSecondLevelCachePutCount());
	}


}
