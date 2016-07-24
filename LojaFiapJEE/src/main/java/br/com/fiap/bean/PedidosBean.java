package br.com.fiap.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.dao.HibernateUtil;
import br.com.fiap.entity.Item;
import br.com.fiap.entity.Pedido;

@RequestScoped
@ManagedBean
public class PedidosBean {

	private String idPedido = "";
	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	private Pedido pedido;
	private HttpSession session;
	private GenericDao<Pedido> 	pedidosDao = new GenericDao<>(Pedido.class);
	private List<Pedido> listMeusPedidos;

	@ManagedProperty("#{clienteBean}")
	ClienteBean clienteBean;

	public ClienteBean getClienteBean() {
		return clienteBean;
	}

	public void setClienteBean(ClienteBean clienteBean) {
		this.clienteBean = clienteBean;
	}

	@PostConstruct
	public void init(){

		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);






		//pesquisarPedidos();
	}



	public void listarMeusPedidos(){


		if (clienteBean.getCliente() != null) {



			Session session = HibernateUtil.getSessionFactory().openSession();
			Criteria query = session.createCriteria(Pedido.class);
			query.add(Restrictions.eq("cliente.id", clienteBean.getCliente().getId()));

			List<Pedido> templist = query.list();
			if (!templist.isEmpty()) {
				listMeusPedidos = templist;
				for (Pedido pedido : listMeusPedidos) {
					for (Item item : pedido.getItens()) {

					}

				}
			}


			session.close();
		}else{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../index/newIndex.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public List<Pedido> getListMeusPedidos() {
		return listMeusPedidos;
	}

	public void setListMeusPedidos(List<Pedido> listMeusPedidos) {
		this.listMeusPedidos = listMeusPedidos;
	}

	public void mostrarPedido() {

		boolean ok = false;



		if (!idPedido.isEmpty()) {


			if (clienteBean.getCliente() != null) {

				pedido = pedidosDao.buscarById(Long.parseLong(idPedido)) ;
				if (pedido != null) {



					if (pedido.getCliente().getId().equals(clienteBean.getCliente().getId())) {

						ok = true;
					}else{
						ok = false;
					}
				}else{
					ok = false;
				}
			}else{
				ok = false;
			}
		}else{
			ok = false;
		}


		if (!ok) {

			try {

				FacesContext.getCurrentInstance().getExternalContext().redirect("../index/newIndex.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}








}
