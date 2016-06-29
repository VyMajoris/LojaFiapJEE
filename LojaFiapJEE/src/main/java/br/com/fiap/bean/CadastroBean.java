package br.com.fiap.bean;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Cliente;
import br.com.fiap.entity.Endereco;

@ManagedBean
@SessionScoped
public class CadastroBean {
	Cliente clienteTemp;
	Cliente cliente;
	HttpSession session;
	private GenericDao<Cliente> clienteDao;
	Endereco endereco;


	@PostConstruct
	public void init(){
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		clienteDao = new GenericDao<Cliente>(Cliente.class);
		//cliente = clienteDao.buscarById( (Long) session.getAttribute("idCliente"));
		clienteTemp = (Cliente) session.getAttribute("cliente");


		cliente = (Cliente) session.getAttribute("cliente");
		System.out.println("as "+cliente);
		if (cliente == null) {
			System.out.println("bs "+cliente);
			cliente = new Cliente();
		}

		if(cliente.getEndereco() == null){
			System.out.println("ENDERECO: null ");
			cliente.setEndereco(new Endereco());
		}else{System.out.println("ENDEREC  "+cliente.getEndereco().getPais());

		}

	}



	public void updateInput(){
		RequestContext.getCurrentInstance().execute("updateInput();");
	}



	public void atualizarCliente(){
		clienteDao.update(cliente);
	}

	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void login(){


	}

	public GenericDao<Cliente> getClienteDao() {
		return clienteDao;
	}
	public void setClienteDao(GenericDao<Cliente> clienteDao) {
		this.clienteDao = clienteDao;
	}




}
