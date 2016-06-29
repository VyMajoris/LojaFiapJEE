package br.com.fiap.bean;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Cliente;

@ManagedBean
@SessionScoped
public class LoginBean {
	Cliente cliente;
	HttpSession session;
	private GenericDao<Cliente> clienteDao;


	//@ManagedProperty("#{param.uid}")
	private String uid;
	//@ManagedProperty("#{param.email}")
	private String email;
	//@ManagedProperty("#{param.displayName}")
	private String displayName;
	//@ManagedProperty("#{param.providerData}")
	private String providerData;
	//@ManagedProperty("#{param.emailVerified}")
	private String emailVerified;
	//@ManagedProperty("#{param.token}")
	private String token;



	@PostConstruct
	public void init(){
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		clienteDao = new GenericDao<Cliente>(Cliente.class);
		//cliente = clienteDao.buscarById( (Long) session.getAttribute("idCliente"));


	}
	@PreDestroy
	public void onDestroy(){
		System.out.println("PRE DESTROY");
		RequestContext.getCurrentInstance().execute("firebase.auth().signOut();");
	}





	public void login(){
		//String token = FirebaseAuth.getInstance().createCustomToken(uid);
		getRequestParams();

		Cliente cliente = clienteDao.buscarById(uid);

		System.out.println("a");
		if (cliente == null) {
			System.out.println("b");
			cliente = new Cliente();
			cliente.setNome(displayName);
			cliente.setEmail(email);
			cliente.setId(uid);
			clienteDao.adicionar(cliente);

		}else{
			System.out.println("c");
			if (!email.equals(cliente.getEmail()) || !displayName.equals(cliente.getNome()) ) {
				cliente.setEmail(email);
				cliente.setNome(displayName);
				clienteDao.update(cliente);
			}
		}

		session.setAttribute("cliente", cliente);

		if(cliente.getCpf() == null  || cliente.getDtNascimento() == null || cliente.getTelefone() == null){
			pedirUpdate();
		}else if(cliente.getEndereco() == null){
			System.out.println("d");
			pedirUpdate();
		}else if(cliente.getEndereco().getBairro() == null ||
				cliente.getEndereco().getCep() == null ||
				cliente.getEndereco().getCidade() == null ||
				cliente.getEndereco().getComplemento() == null ||
				cliente.getEndereco().getEstado() == null ||
				cliente.getEndereco().getNumero() == null ||
				cliente.getEndereco().getPais() == null ||
				cliente.getEndereco().getRua() == null){
			System.out.println("f");
			pedirUpdate();
		}

	}


	public void pedirUpdate(){
		RequestContext.getCurrentInstance().execute("dialog.showModal();");
	}


	private void getRequestParams() {
		uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
		email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("email");
		displayName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("displayName");
		providerData = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("providerData");
		emailVerified = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailVerified");
		token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");
	}



	public GenericDao<Cliente> getClienteDao() {
		return clienteDao;
	}
	public void setClienteDao(GenericDao<Cliente> clienteDao) {
		this.clienteDao = clienteDao;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
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
	public String getProviderData() {
		return providerData;
	}
	public void setProviderData(String providerData) {
		this.providerData = providerData;
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




}
