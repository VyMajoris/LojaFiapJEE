$(document).ready(function() {
	$('input[type=radio][name=frete]').change(function() {
		if(this.value == 'SEDEX') {
			updateFreteEscolhido([{
				name: 'frete',
				value: 40010
			}])
		} else if(this.value == 'SEDEX10') {
			updateFreteEscolhido([{
				name: 'frete',
				value: 40215
			}])
		} else if(this.value == 'PAC') {
			updateFreteEscolhido([{
				name: 'frete',
				value: 41106
			}])
		}
	});
	$('#idRua').keyup(function() {
		console.log($(this))
		console.log($(this).parent())
		if($(this).val().length == 0) {
			$(this).parent().removeClass('is-dirty')
		}
	});
	$(".qtd").inputmask('99');
	$('.qtd').inputmask({
		"placeholder": ""
	});
	$(".qtd").focus(function() {
		$('.qtd').inputmask({
			"placeholder": ""
		});
	});
	$(".cep").inputmask('99999-999');
	$(".cep").keyup(function() {
		var string = this.value,
			substring = "_";
		if(string.indexOf(substring) == -1) {
			app_key = 'PGfWjOmrFPac9yHZD6cWaTSxLPioaUle';
			app_secret = '8aOC02s1W1DOCt2P1Jgsq6oWJT574tU16E9s7w8kOvHucGPk';
			$.getJSON("https://webmaniabr.com/api/1/cep/" + this.value + "/?app_key=" + app_key + "&app_secret=" + app_secret, function(data) {
				$("#idEstado").parent().addClass('is-dirty');
				$("#idCidade").parent().addClass('is-dirty');
				$("#idRua").parent().addClass('is-dirty');
				$("#idBairro").parent().addClass('is-dirty');
				$("#idCep").parent().addClass('is-dirty');
				$("#idEstado").val(converterEstados(data.uf));
				$("#idCidade").val(data.cidade);
				$("#idRua").val(data.endereco);
				$("#idBairro").val(data.bairro);
				$("#idCep").val(data.cep);
				if(data.uf != "") {
					updateCEPFrete([{
						name: 'cep',
						value: data.cep
					}])
				}
			});
		}
	});
});