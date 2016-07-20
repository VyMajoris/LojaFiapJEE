package br.com.fiap.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity


@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
@Table(name="Cliente")
public class Cliente implements Serializable {

	@Id
	@Column(name="ID_CLIENTE")
	private String id;

	@Column(name="EMAIL")
	private String email;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ENDERECO_ID")
	@Cascade({CascadeType.ALL})
	private Endereco endereco = new Endereco();

	@Column(name="VALID")
	private boolean valid;



	@Column(name="CPF")
	private String cpf;


	@Column(name="NOME")
	private String nome;

	@Column(name="TELEFONE")
	private String telefone;

	@Column(name="photoUrl")
	private String photoUrl;

	@Column(name="DT_NASCIMENTO")
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}




	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}


	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
