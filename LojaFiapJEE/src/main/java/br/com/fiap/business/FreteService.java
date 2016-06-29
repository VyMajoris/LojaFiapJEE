package br.com.fiap.business;

import br.com.fiap.dto.CResultado;
import retrofit.http.GET;
import retrofit.http.Query;


public interface FreteService {
	@GET("/calculador/CalcPrecoPrazo.asmx/CalcPrecoPrazo")
	CResultado getFrete (
			@Query("nCdServico") String nCdServico,
			@Query("sCepOrigem")String sCepOrigem,
			@Query("sCepDestino")String sCepDestino,
			@Query("nVlPeso")String nVlPeso,
			@Query("nCdFormato")String nCdFormato,
			@Query("nVlComprimento")String nVlComprimento,
			@Query("nVlAltura")String nVlAltura,
			@Query("nVlLargura")String nVlLargura,
			@Query("nVlDiametro")String nVlDiametro,
			@Query("sCdMaoPropria")String sCdMaoPropria,
			@Query("nVlValorDeclarado")String nVlValorDeclarado,
			@Query("sCdAvisoRecebimento")String sCdAvisoRecebimento,
			@Query("nCdEmpresa")String nCdEmpresa,
			@Query("sDsSenha")String sDsSenha)


	;

}