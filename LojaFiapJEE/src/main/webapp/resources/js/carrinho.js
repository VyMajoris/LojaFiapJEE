


$(document).ready(function() {
	

	
	
	$('.qtd').keydown(function(e){

		if ((e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105)) {
			var valueString = $(this).val()+""+(String.fromCharCode(e.keyCode));

			if ( parseInt(valueString) >= 1 && parseInt(valueString) <= 10   ) {
				
				//

			}else{
				e.preventDefault();
			}

		}else{
			e.preventDefault();
		}
	})



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

	
	$("input").blur(function(){
		console.log("BLURRR"+$(this).val())
		if($(this).attr('required')!=null && $(this).val()==""){
			$(this).parent().addClass('is-invalid');
		}
	})
	

	$(".cep").inputmask("99999-999", {
		"placeholder": "#####-###",
		"showMaskOnHover": false
	});
	
	$(".cep").keyup(function() {
		$("#idCep").parent().addClass('is-dirty');
		console.log($(this).val().indexOf("#") )
	
		
		if($(this).val().indexOf("#")  == -1 && $(this).val() != "") {
			$(this).parent().removeClass('is-dirty');
			$(this).parent().removeClass('is-invalid');
			
			app_key = 'PGfWjOmrFPac9yHZD6cWaTSxLPioaUle';
			app_secret = '8aOC02s1W1DOCt2P1Jgsq6oWJT574tU16E9s7w8kOvHucGPk';
			$.getJSON("https://webmaniabr.com/api/1/cep/" + this.value + "/?app_key=" + app_key + "&app_secret=" + app_secret, function(data) {
				if(data.uf != "") {
					$(".estado").parent().addClass('is-dirty');
					$(".cidade").parent().addClass('is-dirty');
					$(".rua").parent().addClass('is-dirty');
					$(".bairro").parent().addClass('is-dirty');
					$(".estado").val(converterEstados(data.uf)).parent().removeClass('is-invalid');
					$(".cidade").val(data.cidade).parent().removeClass('is-invalid');
					$(".rua").val(data.endereco).parent().removeClass('is-invalid');
					$(".bairro").val(data.bairro).parent().removeClass('is-invalid');
					$(".cep").val(data.cep).parent().removeClass('is-invalid');
					updateCEPFrete([{
						name: 'cep',
						value: data.cep
					}])
				}
			});
		}
	});
});