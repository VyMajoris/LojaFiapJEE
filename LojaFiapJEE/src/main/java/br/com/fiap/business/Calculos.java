package br.com.fiap.business;

public class Calculos {
	private static final double BOLETO_DESCONTO = 0.15;

	public static Double calcularValorBoleto(Double valorTotal, Double valorFreteEscolhido) {
		System.out.println("VALOR TOTAL: "+ valorTotal);
		System.out.println("VALOR valorFreteEscolhido: "+valorFreteEscolhido);
		Double valorBoleto = valorTotal;

		valorBoleto = valorBoleto + valorFreteEscolhido;

		valorBoleto = valorBoleto -  (valorBoleto * BOLETO_DESCONTO);
		return valorBoleto;
	}



}
