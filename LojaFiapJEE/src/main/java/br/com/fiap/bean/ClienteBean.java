package br.com.fiap.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.google.common.base.Strings;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Cliente;
import br.com.fiap.entity.Endereco;

@ManagedBean
@SessionScoped
public class ClienteBean {
	Cliente clienteTemp;
	Cliente cliente;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	HttpSession session;
	private GenericDao<Cliente> clienteDao;
	Endereco endereco;
	// @ManagedProperty("#{param.uid}")
	private String uid;
	// @ManagedProperty("#{param.email}")
	private String email;
	// @ManagedProperty("#{param.displayName}")
	private String displayName;
	// @ManagedProperty("#{param.providerData}")
	private String providerData;
	// @ManagedProperty("#{param.emailVerified}")
	private String emailVerified;
	// @ManagedProperty("#{param.token}")
	private String token;
	private String photoUrl;

	@PreDestroy
	public void preDestroy() {

		RequestContext.getCurrentInstance().execute("firebase.auth().signOut();");

	}

	@PostConstruct
	public void init() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		clienteDao = new GenericDao<>(Cliente.class);

		cliente = (Cliente) session.getAttribute("cliente");


	}

	public void login() {

		getRequestParams();

		cliente = clienteDao.buscarById(uid);


		if (cliente == null) {

			cliente = new Cliente();
			cliente.setNome(displayName);
			cliente.setEmail(email);
			cliente.setPhotoUrl(photoUrl);
			cliente.setId(uid);
			clienteDao.adicionar(cliente);

		} else {
			// Verifica se dados da conta do google foram atualziados desde o
			// ultimo login
			if (!email.equals(cliente.getEmail()) || !displayName.equals(cliente.getNome())
					|| !photoUrl.equals(cliente.getPhotoUrl())) {
				cliente.setEmail(email);
				cliente.setNome(displayName);
				cliente.setPhotoUrl(photoUrl);
				clienteDao.update(cliente);
			}
		}

		session.setAttribute("cliente", cliente);


	}

	public boolean validaCliente() {




		boolean valid;
		if (
				Strings.isNullOrEmpty(cliente.getEndereco().getBairro()) || Strings.isNullOrEmpty( cliente.getEndereco().getCep())
				|| Strings.isNullOrEmpty(cliente.getEndereco().getCidade()) || Strings.isNullOrEmpty(cliente.getEndereco().getEstado()) || Strings.isNullOrEmpty(cliente.getEndereco().getNumero())
				|| Strings.isNullOrEmpty(cliente.getEndereco().getRua())|| Strings.isNullOrEmpty( cliente.getCpf())
				|| cliente.getDtNascimento() == null || Strings.isNullOrEmpty(cliente.getEmail()) || Strings.isNullOrEmpty(cliente.getTelefone())) {

			valid = false;
			if (cliente.isValid() != valid) {
				cliente.setValid(valid);
				clienteDao.update(cliente);
			}
		} else {
			valid = true;
			if (cliente.isValid() != valid) {
				cliente.setValid(valid);
				clienteDao.update(cliente);
			}
		}



		return valid;

	}

	public void logout() {

		session.invalidate();

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + "/faces/index/newIndex.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pedirUpdate() {


		if (cliente !=null) {


			if (validaCliente() == false) {

				RequestContext.getCurrentInstance().execute("arrumarCadastroDialog.showModal();");

			}
		}


	}

	private void getRequestParams() {
		uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
		email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("email");
		displayName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("displayName");
		photoUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("photoURL");

		providerData = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("providerData");
		emailVerified = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("emailVerified");
		token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");
	}

	public void updateInput() {
		RequestContext.getCurrentInstance().execute("updateInput();");
	}

	public void atualizarCliente() {
		clienteDao.update(cliente);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(String emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProviderData() {
		return providerData;
	}

	public void setProviderData(String providerData) {
		this.providerData = providerData;
	}

}
