package br.com.fiap.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



//Sem cache
@Entity
@NamedQuery(name="ProdutoDirect.findAll", query="SELECT p FROM Produto p")
@Table(name="Produto")
public class ProdutoDirect implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_PRODUTO")
	private Long id;

	@Column(name="NOME")
	private String nome;

	@Column(name="DESCRICAO")
	private String descricao;

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	@Column(name="ESTOQUE")
	private int estoque;



	@Column(name="CATEGORIA")
	private String categoria;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Column(name="VALOR")
	private double valor;

	@Column(name="URL_IMAGEM")
	private String url_imagem;

	@Column(name="DATA_ADDED")
	@Temporal(TemporalType.DATE)
	private Date dataAdded;

	public Date getDataAdded() {
		return dataAdded;
	}

	public void setDataAdded(Date dataAdded) {
		this.dataAdded = dataAdded;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getUrl_imagem() {
		return url_imagem;
	}

	public void setUrl_imagem(String url_imagem) {
		this.url_imagem = url_imagem;
	}



}
