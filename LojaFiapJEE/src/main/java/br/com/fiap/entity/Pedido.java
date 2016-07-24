package br.com.fiap.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
@Table(name="Pedido")
public class Pedido implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_PEDIDO")
	private Long id;

	@Column(name="ID_ITEM")
	@OneToMany( fetch=FetchType.EAGER, mappedBy="pedido")
	private Set<Item> itens = new HashSet<>();

	@Column(name="DATA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;


	@OneToOne
	private Cliente cliente;

	@Column(name="VALOR_FRETE")
	private double valorFrete;


	public double getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(double valorFrete) {
		this.valorFrete = valorFrete;
	}

	@Column(name="TOTAL")
	private double total;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Item> getItens() {
		return itens;
	}

	public void setItens(Set<Item> itens) {
		this.itens = itens;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}




}
