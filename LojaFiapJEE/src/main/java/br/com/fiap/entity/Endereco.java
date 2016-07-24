package br.com.fiap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;


@Entity
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL, region="endereco")

@NamedQuery(name="endereco.findAll", query="SELECT e FROM Endereco e", hints =
{ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
@Table(name="Endereco")
public class Endereco {

	public int compareTo(Endereco that) {
	     return ComparisonChain.start()
	         .compare(this.id, that.getId())
	         .compare(this.estado, that.getEstado())
	         .compare(this.cidade, that.getCidade())
	         .compare(this.cep, that.getCep())
	         .compare(this.bairro, that.getBairro())
	         .compare(this.numero, that.getNumero())
	         .compare(this.complemento, that.getComplemento())
	         .compare(this.rua, that.getRua())
	         .compare(this.estado, that.estado, Ordering.natural().nullsLast())
	         .result();
	   }

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_ENDERECO")
	private Long id;


	@OneToOne(fetch = FetchType.EAGER, mappedBy = "endereco")
	@PrimaryKeyJoinColumn
	private Cliente cliente;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	@Column(name="PAIS")
	private String pais;
	@Column(name="ESTADO")
	private String estado;
	@Column(name="CIDADE")
	private String cidade;
	@Column(name="BAIRRO")
	private String bairro;
	@Column(name="RUA")
	private String rua;
	@Column(name="NUMERO")
	private String numero;
	@Column(name="COMPLEMENTO")
	private String complemento;
	@Column(name="CEP")
	private String cep;


	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

}
