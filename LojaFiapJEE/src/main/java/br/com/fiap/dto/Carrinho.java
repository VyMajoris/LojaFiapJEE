package br.com.fiap.dto;

import java.util.HashMap;
import java.util.Map;

import br.com.fiap.entity.Produto;

public class Carrinho {

	private Map<Long, ItemCarrinho> itemCarrinhoMap = new HashMap<>();
	private double valorTotal;
	private int qtdItens;





	public void putItemCarrinho(Long idProduto, int qtd, Produto produto){
		System.out.println("carrinho put: "+ qtd);
		getItemCarrinhoMap().put(idProduto, new ItemCarrinho(produto, qtd));

		System.out.println("carrinho item quantidade: ++++++ "+ getItemCarrinhoMap().get(idProduto).getQuantidade());

		updateCarrinho();


	}

	public void  removeItemCarrinho(Long idProduto){
		getItemCarrinhoMap().remove(idProduto);
		updateCarrinho();
	}

	public void updateCarrinho() {

		setValorTotal(0);
		for (ItemCarrinho item : itemCarrinhoMap.values()) {
			setValorTotal(getValorTotal() + item.getValor());
			System.out.println(item.getValor());
		}
		qtdItens = itemCarrinhoMap.size();
	}



	public Map<Long, ItemCarrinho> getItemCarrinhoMap() {
		return itemCarrinhoMap;
	}
	public void setItemCarrinhoMap(Map<Long, ItemCarrinho> itemCarrinhoMap) {
		this.itemCarrinhoMap = itemCarrinhoMap;
	}
	public String getQtdItens() {
		return qtdItens==0? ""  : String.valueOf(qtdItens);
	}
	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}




}
