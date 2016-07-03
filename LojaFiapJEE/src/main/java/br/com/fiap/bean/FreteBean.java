package br.com.fiap.bean;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.fiap.business.CepRestClient;
import br.com.fiap.dto.CResultado;
import br.com.fiap.dto.CServico;
import br.com.fiap.entity.Cliente;

@SessionScoped
@ManagedBean
public class FreteBean {

	@ManagedProperty("#{freteValues}")
	FreteValues freteValues;

	@ManagedProperty("#{carrinhoBean}")
	CarrinhoBean carrinhoBean;

	public FreteValues getFreteValues() {
		return freteValues;
	}

	public void setFreteValues(FreteValues freteValues) {
		this.freteValues = freteValues;
	}

	public CarrinhoBean getCarrinhoBean() {
		return carrinhoBean;
	}

	public void setCarrinhoBean(CarrinhoBean carrinhoBean) {
		this.carrinhoBean = carrinhoBean;
	}

	private CResultado frete;
	HttpSession session;

	NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

	Cliente cliente;
	private String freteId = "40010";
	private final String   sedexCod = "40010";
	private final String  sedex10Cod = "40215";
	private final String  pacCod = "41106";


	private boolean freteOk = false;
	private Double valorFreteEscolhido;

	private String freteEscolhido;
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
	private String sedexChecked ;
	private String sedex10Checked;
	private String pacChecked;
	private String freteChecked ="";



	@PostConstruct
	public void init() {
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		sedexChecked = "";
		sedex10Checked = "";
		pacChecked = "";





	}

	public void checkCep() throws ParseException {
		cliente =	(Cliente) session.getAttribute("cliente");
		if (cliente !=null) {
			System.out.println("FRETE B");
			if (cliente.getEndereco()!=null) {
				System.out.println("FRETE C");
				if (cliente.getEndereco().getCep()!= null) {
					freteValues.setFreteEscolhido("Selecione o frete");
					try {
						updateFreteValues(cliente.getEndereco().getCep());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//valorFreteEscolhido = freteSedex.getValor();


					freteValues.setFreteOk(false);
				}else{
					freteValues.setFreteEscolhido("Insira um CEP válido");
					freteValues.setFreteOk(false);
				}

			}else{
				freteValues.setFreteEscolhido("Insira um CEP válido");
				freteValues.setFreteOk(false);
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

	public void checkChecked() throws ParseException {
		switch (freteValues.getFreteChecked()) {
		case "sedex":

			System.out.println("==CHCKED SEDEX");


			freteValues.setFreteEscolhido("R$"+freteValues.getFreteSedex().getValor());
			freteValues.setValorFreteEscolhido(format.parse(freteValues.getFreteSedex().getValor()).doubleValue());


			break;
		case "sedex10":
			System.out.println("==CHCKED SEDEX10");
			freteValues.setFreteEscolhido("R$"+freteValues.getFreteSedex10().getValor());
			freteValues.setValorFreteEscolhido(format.parse(freteValues.getFreteSedex10().getValor()).doubleValue());

			break;
		case "pac":
			System.out.println("==CHCKED pac");
			freteValues.setFreteEscolhido("R$"+freteValues.getFretePac().getValor());
			freteValues.setValorFreteEscolhido(format.parse(freteValues.getFretePac().getValor()).doubleValue());

			break;

		default:
			break;
		}
	}

	private void updateFreteValues(String cep) throws ParseException {
		System.out.println("AAAAA");
		freteValues.setFreteSedex(CepRestClient.consultar(sedexCod, cep).getServicos().getCServico());
		System.out.println("bbbbb");
		freteValues.setFreteSedex10(CepRestClient.consultar(sedex10Cod, cep).getServicos().getCServico());
		System.out.println("cccc");
		freteValues.setFretePac(CepRestClient.consultar(pacCod, cep).getServicos().getCServico());
		System.out.println("dddd");




		updateFreteEscolhido();
	}



	public void updateFreteEscolhido() throws ParseException{

		String freteTemp = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("frete");

		if (freteTemp != null) {
			freteId = freteTemp;
		}


		System.out.println("UPDATE FRETEEEE : "+freteId);

		switch (freteId) {
		case sedexCod:





			freteValues.setFreteEscolhido("R$"+freteValues.getFreteSedex().getValor());
			freteValues.setValorFreteEscolhido( format.parse(freteValues.getFreteSedex().getValor()).doubleValue());
			freteValues.setFreteOk(true);
			freteValues.setSedex10Checked("");
			freteValues.setSedexChecked("checked");
			freteValues.setSedexLabelChecked("is-checked");
			freteValues.setSedex10LabelChecked("");
			freteValues.setPacLabelChecked("");
			freteValues.setPacChecked("");
			freteValues.setFreteChecked("sedex");


			break;
		case sedex10Cod:


			freteValues.setFreteEscolhido("R$"+freteValues.getFreteSedex10().getValor());
			freteValues.setValorFreteEscolhido( format.parse(freteValues.getFreteSedex10().getValor()).doubleValue());
			freteValues.setFreteOk(true);
			freteValues.setSedexLabelChecked("");
			freteValues.setSedex10LabelChecked("is-checked");
			freteValues.setPacLabelChecked("");
			freteValues.setSedex10Checked("checked");
			freteValues.setSedexChecked("");
			freteValues.setPacChecked("");
			freteValues.setFreteChecked("sedex10");


			break;
		case pacCod:
			freteValues.setFreteEscolhido("R$"+freteValues.getFretePac().getValor());
			freteValues.setValorFreteEscolhido( format.parse(freteValues.getFretePac().getValor()).doubleValue());
			freteValues.setFreteOk(true);
			freteValues.setSedexLabelChecked("");
			freteValues.setSedex10LabelChecked("");
			freteValues.setPacLabelChecked("is-checked");
			freteValues.setSedex10Checked("");
			freteValues.setSedexChecked("");
			freteValues.setPacChecked("checked");
			freteValues.setFreteChecked("pac");


			break;

		default:
			freteEscolhido = "Escolha um frete";
			freteOk = false;
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
	public Double getValorFreteEscolhido() {
		return valorFreteEscolhido;
	}

	public void setValorFreteEscolhido(Double valorFreteEscolhido) {
		this.valorFreteEscolhido = valorFreteEscolhido;
	}

	public String getFreteEscolhido() {
		return freteEscolhido;
	}

	public void setFreteEscolhido(String freteEscolhido) {
		this.freteEscolhido = freteEscolhido;
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
		return freteOk;
	}

	public void setFreteOk(boolean isFreteOk) {
		this.freteOk = isFreteOk;
	}




	public String getSedexChecked() {
		return sedexChecked;
	}

	public void setSedexChecked(String sedexChecked) {
		this.sedexChecked = sedexChecked;
	}

	public String getSedex10Checked() {
		return sedex10Checked;
	}

	public void setSedex10Checked(String sedex10Checked) {
		this.sedex10Checked = sedex10Checked;
	}

	public String getPacChecked() {
		return pacChecked;
	}

	public void setPacChecked(String pacChecked) {
		this.pacChecked = pacChecked;
	}

	public String getFreteChecked() {
		return freteChecked;
	}

	public void setFreteChecked(String freteChecked) {
		this.freteChecked = freteChecked;
	}



}

