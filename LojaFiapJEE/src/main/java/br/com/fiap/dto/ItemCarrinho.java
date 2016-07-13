package br.com.fiap.dto;

import br.com.fiap.entity.Produto;

public class ItemCarrinho {



	private Produto produto;

	private  int quantidade;
	private  int quantidadeAlteracao;

	private  Double valor;

	public ItemCarrinho(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.setValor(produto.getValor()*quantidade);

	}


	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}


	public void setValor(Double valor) {
		this.valor = valor;
	}


	public int getQuantidadeAlteracao() {
		return quantidadeAlteracao;
	}


	public void setQuantidadeAlteracao(int quantidadeAlteracao) {
		this.quantidadeAlteracao = quantidadeAlteracao;
	}






}
