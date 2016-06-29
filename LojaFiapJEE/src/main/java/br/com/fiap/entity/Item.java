package br.com.fiap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="item")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i", hints =
{ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
@Table(name="Item")
public class Item {

	public Item(Pedido pedido, Produto produto, int quantidade, double valor) {
		super();
		this.pedido = pedido;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	public Item() {

	}

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_PEDIDO")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	private Pedido pedido;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "idProduto", updatable=true,  nullable=true, columnDefinition = "bigint(20)")
	private Produto produto;

	@Column(name="QUANTIDADE")
	private int quantidade;

	@Column(name="VALOR")
	private double valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}


}
