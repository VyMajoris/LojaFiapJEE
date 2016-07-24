package br.com.fiap.teste;

import java.util.ArrayList;
import java.util.Random;

import javax.faces.context.FacesContext;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.joda.time.DateTime;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.dao.HibernateUtil;
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



		//printStats(stats, 5);



		//test3();

		fillData(25,"Categoria A");
		fillData(25,  "Categoria B");
		fillData(25, "Categoria C");
		fillData(25, "Categoria D");



	}


	static ArrayList<String> imgList = new ArrayList<>();
	static int imgCount = 0;


	static String randomString(final int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public static void fillData(int i, String string){

		for (int j = 0; j < i; j++) {
			imgCount++;
			Produto produto = new Produto();
			produto.setDescricao(string  +" - "+ randomString(20) );
			produto.setEstoque(j);
			produto.setNome("Produto "+ randomString(5) +" #"+j);
			produto.setUrl_imagem("https://unsplash.it/800/600?image="+imgCount);
			produto.setValor(100+j);
			produto.setCategoria(string);
			produto.setDataAdded( new DateTime(2016, 6, j+1, 0, 0).toDate());

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
		produtodao.listar();
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch Count="+stats.getEntityFetchCount());
		produtodao.listar();
		System.out.println("Second Level Hit Count="+ stats.getSecondLevelCacheHitCount());
		produtodao.listar();
		System.out.println("Second Level Miss Count="+ stats.getSecondLevelCacheMissCount());
		produtodao.listar();
		System.out.println("Second Level Put Count="+ stats.getSecondLevelCachePutCount());
		produtodao.listar();
		System.out.println("Second Level Put Count="+ stats.getSecondLevelCacheStatistics("produto"));
	}


}
