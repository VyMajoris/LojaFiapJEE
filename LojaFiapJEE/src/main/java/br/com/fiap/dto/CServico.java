package br.com.fiap.dto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="CServico")
public class CServico
{
	@Element(name="obsFim", required=false)
    private String obsFim;

	@Element(name="Codigo")
    private String codigo;

	@Element(name="EntregaDomiciliar", required=false)
    private String entregaDomiciliar;

	@Element(name="ValorAvisoRecebimento")
    private String valorAvisoRecebimento;

	@Element(name="ValorMaoPropria", required=false)
    private String valorMaoPropria;

	@Element(name="MsgErro" , required=false)
    private String msgErro;

	@Element(name="ValorValorDeclarado")
    private String valorValorDeclarado;

	@Element(name="Valor", required=false)
    private String valor;

	@Element(name="PrazoEntrega")
    private String prazoEntrega;

	@Element(name="EntregaSabado", required=false)
    private String entregaSabado;

	@Element(name="Erro", required=false)
    private String erro;

	@Element(name="ValorSemAdicionais", required=false)
    private String valorSemAdicionais;

	@Override
	public String toString() {
		return "CServico [obsFim=" + obsFim + ", codigo=" + codigo + ", entregaDomiciliar=" + entregaDomiciliar
				+ ", valorAvisoRecebimento=" + valorAvisoRecebimento + ", valorMaoPropria=" + valorMaoPropria
				+ ", msgErro=" + msgErro + ", valorValorDeclarado=" + valorValorDeclarado + ", valor=" + valor
				+ ", prazoEntrega=" + prazoEntrega + ", entregaSabado=" + entregaSabado + ", erro=" + erro
				+ ", valorSemAdicionais=" + valorSemAdicionais + "]";
	}

	public String getObsFim() {
		return obsFim;
	}

	public void setObsFim(String obsFim) {
		this.obsFim = obsFim;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEntregaDomiciliar() {
		return entregaDomiciliar;
	}

	public void setEntregaDomiciliar(String entregaDomiciliar) {
		this.entregaDomiciliar = entregaDomiciliar;
	}

	public String getValorAvisoRecebimento() {
		return valorAvisoRecebimento;
	}

	public void setValorAvisoRecebimento(String valorAvisoRecebimento) {
		this.valorAvisoRecebimento = valorAvisoRecebimento;
	}

	public String getValorMaoPropria() {
		return valorMaoPropria;
	}

	public void setValorMaoPropria(String valorMaoPropria) {
		this.valorMaoPropria = valorMaoPropria;
	}

	public String getMsgErro() {
		return msgErro;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public String getValorValorDeclarado() {
		return valorValorDeclarado;
	}

	public void setValorValorDeclarado(String valorValorDeclarado) {
		this.valorValorDeclarado = valorValorDeclarado;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPrazoEntrega() {
		return prazoEntrega;
	}

	public void setPrazoEntrega(String prazoEntrega) {
		this.prazoEntrega = prazoEntrega;
	}

	public String getEntregaSabado() {
		return entregaSabado;
	}

	public void setEntregaSabado(String entregaSabado) {
		this.entregaSabado = entregaSabado;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getValorSemAdicionais() {
		return valorSemAdicionais;
	}

	public void setValorSemAdicionais(String valorSemAdicionais) {
		this.valorSemAdicionais = valorSemAdicionais;
	}



}
