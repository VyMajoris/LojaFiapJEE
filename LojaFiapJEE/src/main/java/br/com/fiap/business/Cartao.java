package br.com.fiap.business;

public class Cartao {
	private String numCartao;
	private String dtValidade;
	private String nome;
	private String ccv;

	public Cartao() {
	}

	public String getNumCartao() {
		return numCartao;
	}

	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}

	public String getDtValidade() {
		return dtValidade;
	}

	public void setDtValidade(String dtValidade) {
		this.dtValidade = dtValidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCcv() {
		return ccv;
	}

	public void setCcv(String ccv) {
		this.ccv = ccv;
	}
}