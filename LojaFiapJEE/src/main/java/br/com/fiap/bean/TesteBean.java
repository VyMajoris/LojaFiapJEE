package br.com.fiap.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@RequestScoped
@ManagedBean
public class TesteBean {

	public TesteBean() {
		// TODO Auto-generated constructor stub
	}
	public void destroy(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

}
