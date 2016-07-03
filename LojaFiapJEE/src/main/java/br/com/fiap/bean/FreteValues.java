package br.com.fiap.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.fiap.dto.CServico;

@ManagedBean
@SessionScoped
public class FreteValues {

	private CServico freteSedex;
	private CServico freteSedex10;
	private CServico fretePac;

	private Double valorFreteEscolhido;
	private String freteEscolhido;
	private Double valorFreteSedex;
	private Double valorFreteSedex10;
	private Double valorFretePac;
	private String etaSedex;
	private String etaSedex10;
	private String etaPac;
	private String cep;
	private String sedexChecked;
	private String sedex10Checked;
	private String pacChecked;
	private String sedexLabelChecked;
	private String sedex10LabelChecked;
	private String pacLabelChecked;


	public String getSedexLabelChecked() {
		return sedexLabelChecked;
	}
	public void setSedexLabelChecked(String sedexLabelChecked) {
		this.sedexLabelChecked = sedexLabelChecked;
	}
	public String getSedex10LabelChecked() {
		return sedex10LabelChecked;
	}
	public void setSedex10LabelChecked(String sedex10LabelChecked) {
		this.sedex10LabelChecked = sedex10LabelChecked;
	}
	public String getPacLabelChecked() {
		return pacLabelChecked;
	}
	public void setPacLabelChecked(String pacLabelChecked) {
		this.pacLabelChecked = pacLabelChecked;
	}
	private String freteChecked ="";
	private boolean freteOk = false;






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
	public boolean isFreteOk() {
		return freteOk;
	}
	public void setFreteOk(boolean freteOk) {
		this.freteOk = freteOk;
	}

}
