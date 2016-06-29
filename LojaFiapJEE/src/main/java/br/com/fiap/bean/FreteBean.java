package br.com.fiap.bean;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.fiap.business.CepRestClient;
import br.com.fiap.dto.CResultado;
import br.com.fiap.dto.CServico;
import br.com.fiap.entity.Cliente;

@SessionScoped
@Named
public class FreteBean {

	private CResultado frete;
	HttpSession session;

	NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

	Cliente cliente;
	private String freteId = "40010";
	private final String   sedexCod = "40010";
	private final String  sedex10Cod = "40215";
	private final String  pacCod = "41106";


	private boolean isFreteOk = false;
	private String valorFreteEscolhido;
	private Double valorFreteSedex;
	private Double valorFreteSedex10;
	private Double valorFretePac;
	private String etaSedex;
	private String etaSedex10;
	private String etaPac;
	private String cep;
	CServico freteSedex;
	CServico freteSedex10;
	CServico fretePac;


	@PostConstruct
	public  void init() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		cliente =	(Cliente) session.getAttribute("cliente");
		if (cliente !=null) {
			if (cliente.getEndereco()!=null) {
				if (cliente.getEndereco().getCep()!= null) {
					try {
						updateFreteValues(cliente.getEndereco().getCep());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//valorFreteEscolhido = freteSedex.getValor();
					valorFreteEscolhido = "";
					isFreteOk = false;
				}else{
					valorFreteEscolhido = "Insira um CEP válido";
					isFreteOk = false;
				}

			}else{
				valorFreteEscolhido = "Insira um CEP válido";
				isFreteOk = false;
			}
		}else{
			 try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../index/newIndex.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	private void updateFreteValues(String cep) throws ParseException {
		System.out.println("AAAAA");
		freteSedex = CepRestClient.consultar(sedexCod, cep).getServicos().getCServico();
		System.out.println("bbbbb");
		freteSedex10 = CepRestClient.consultar(sedex10Cod, cep).getServicos().getCServico();
		System.out.println("cccc");
		fretePac = CepRestClient.consultar(pacCod, cep).getServicos().getCServico();
		System.out.println("dddd");



		valorFreteSedex =  format.parse(freteSedex.getValor()).doubleValue();
		etaSedex = freteSedex.getPrazoEntrega();

		valorFreteSedex10 =   format.parse(freteSedex10.getValor()).doubleValue();
		etaSedex10 = freteSedex10.getPrazoEntrega();

		valorFretePac =    format.parse(fretePac.getValor()).doubleValue();
		etaPac= fretePac.getPrazoEntrega();

	}



	public void updateFreteEscolhido() throws ParseException{
		freteId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frete");


		switch (freteId) {
		case sedexCod:
			valorFreteEscolhido =   "R$"+freteSedex.getValor();
			isFreteOk = true;
			break;
		case sedex10Cod:
			valorFreteEscolhido =   "R$"+freteSedex10.getValor();
			isFreteOk = true;

			break;
		case pacCod:
			valorFreteEscolhido =   "R$"+fretePac.getValor();
			isFreteOk = true;
			break;

		default:
			valorFreteEscolhido = "Erro";
			isFreteOk = false;
			break;
		}



	}

	//update todos fretes com base no cep
	public void updateCEPFrete() throws ParseException{
		cep = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cep");
		if (cep!=null){
			updateFreteValues(cep);
		}


	}



	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getFreteId() {
		return freteId;
	}

	public void setFreteId(String freteId) {
		this.freteId = freteId;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public CServico getFreteSedex() {
		return freteSedex;
	}

	public void setFreteSedex(CServico freteSedex) {
		this.freteSedex = freteSedex;
	}

	public CServico getFreteSedex10() {
		return freteSedex10;
	}

	public void setFreteSedex10(CServico freteSedex10) {
		this.freteSedex10 = freteSedex10;
	}

	public CServico getFretePac() {
		return fretePac;
	}

	public void setFretePac(CServico fretePac) {
		this.fretePac = fretePac;
	}

	public String getSedexCod() {
		return sedexCod;
	}

	public String getSedex10Cod() {
		return sedex10Cod;
	}

	public String getPacCod() {
		return pacCod;
	}

	public String getValorFreteEscolhido() {
		return valorFreteEscolhido;
	}

	public void setValorFreteEscolhido(String valorFreteEscolhido) {
		this.valorFreteEscolhido = valorFreteEscolhido;
	}

	public Double getValorFreteSedex() {
		return valorFreteSedex;
	}

	public void setValorFreteSedex(Double valorFreteSedex) {
		this.valorFreteSedex = valorFreteSedex;
	}

	public Double getValorFreteSedex10() {
		return valorFreteSedex10;
	}

	public void setValorFreteSedex10(Double valorFreteSedex10) {
		this.valorFreteSedex10 = valorFreteSedex10;
	}

	public Double getValorFretePac() {
		return valorFretePac;
	}

	public void setValorFretePac(Double valorFretePac) {
		this.valorFretePac = valorFretePac;
	}

	public String getEtaSedex() {
		return etaSedex;
	}

	public void setEtaSedex(String etaSedex) {
		this.etaSedex = etaSedex;
	}

	public String getEtaSedex10() {
		return etaSedex10;
	}

	public void setEtaSedex10(String etaSedex10) {
		this.etaSedex10 = etaSedex10;
	}

	public String getEtaPac() {
		return etaPac;
	}

	public void setEtaPac(String etaPac) {
		this.etaPac = etaPac;
	}

	public CResultado getFrete() {
		return frete;
	}

	public void setFrete(CResultado frete) {
		this.frete = frete;
	}

	public boolean isFreteOk() {
		return isFreteOk;
	}

	public void setFreteOk(boolean isFreteOk) {
		this.isFreteOk = isFreteOk;
	}




}

