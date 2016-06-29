package br.com.fiap.business;

import com.mobprofs.retrofit.converters.SimpleXmlConverter;

import br.com.fiap.dto.CResultado;
import retrofit.RestAdapter;

public class CepRestClient {

	private final static String BASE_URL = "http://ws.correios.com.br/";
	public static String nCdServico;
	public static String sCepOrigem = "01538-001";
	public static String sCepDestino;
	public static String nVlPeso = "1";
	public static String nCdFormato = "1";
	public static String nVlComprimento = "16";
	public static String nVlAltura = "2";
	public static String nVlLargura = "11";
	public static String nVlDiametro = "1";
	public static String sCdMaoPropria = "n";
	public static String nVlValorDeclarado = "10";
	public static String sCdAvisoRecebimento = "n";
	public static String nCdEmpresa = "*";
	public static String sDsSenha = "*";


	public CepRestClient() {
	}

	public static CResultado consultar(String nCdServico,  String sCepDestino){
		RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setConverter(new SimpleXmlConverter())
                .build();
		FreteService freteServ = restAdapter.create(FreteService.class);

		CResultado resultado =  freteServ.getFrete(nCdServico, sCepOrigem, sCepDestino,
				nVlPeso, nCdFormato, nVlComprimento,
				nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria,
				nVlValorDeclarado, sCdAvisoRecebimento, nCdEmpresa,
				sDsSenha);


		return resultado;

	}






}