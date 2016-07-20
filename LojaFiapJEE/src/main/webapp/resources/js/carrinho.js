



$(document).ready(function() {
	

	
	 $('input[type=radio][name=frete]').on('change', function(){
		 
		 
			console.log('RADDDDDDDDDIO')
			if(this.value == 'SEDEX') {
				console.log("SEDEX ESCO")
				updateFreteEscolhido([{
					name: 'frete',
					value: 40010
				}])
			} else if(this.value == 'SEDEX10') {
				console.log("10 ESCO")
				updateFreteEscolhido([{
					name: 'frete',
					value: 40215
				}])
			} else if(this.value == 'PAC') {
				console.log("PAC ESCO")
				updateFreteEscolhido([{
					name: 'frete',
					value: 41106
				}])
			}
		 
	 })

	

	
	
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


	
	$("input").blur(function(){
	
		if($(this).attr('required')!=null && $(this).val()==""){
			$(this).parent().addClass('is-invalid');
		}
		
		if($(this).val()!= ""){
			console.log("blur 1")
			$(this).parent().addClass('is-dirty');
		}else if($(this).val() == ""){
			console.log("blur 2")
			$(this).parent().removeClass('is-dirty');
		}
		
	})
	

	$(".cep").inputmask("99999-999", {
		"placeholder": "#####-###",
		"showMaskOnHover": false
	});
	 
	 
	
	$(".cep").keyup(function() {
		console.log(" CEPPPP")
		$(".cep").parent().addClass('is-dirty');
		console.log("bbbbbb"+$(".cep").parent())
	
		
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