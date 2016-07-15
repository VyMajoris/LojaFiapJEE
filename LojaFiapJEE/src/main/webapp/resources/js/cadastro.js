$(document).ready(function() {
	console.log("aaaa")
	$(".dtNascimento").inputmask("d/m/y", {
		"placeholder": "dd/mm/yyyy",
		"showMaskOnHover": false
	});
	$(".dtNascimento").keyup(function() {
		$(this).parent().addClass('is-dirty');
		if(this.value == "") {
			$(this).parent().removeClass('is-dirty');
		}
	})
	$(".tel").inputmask("(99) 9999-99999", {
		"placeholder": "(DD) ####-#####",
		"showMaskOnHover": false
	});
	$(".tel").keyup(function() {
		$(".tel").parent().addClass('is-dirty');
		if(this.value == "") {
			$(".tel").parent().removeClass('is-dirty');
		}
	})
	$(".cpf").inputmask("999.999.999-99", {
		"placeholder": "###.###.###-##",
		"showMaskOnHover": false
	});
	$(".cpf").keyup(function() {
		$(".cpf").parent().addClass('is-dirty');
		if(this.value == "") {
			$(".cpf").parent().removeClass('is-dirty');
		}
	})
	$(".cpf").inputmask("99999-999", {
		"placeholder": "#####-###",
		"showMaskOnHover": false
	});
	$(".cep").keyup(function() {
		$("#idCep").parent().addClass('is-dirty');
		if(this.value == "") {
			$("#idCep").parent().removeClass('is-dirty');
		}
		var string = this.value,
		substring = "_";
		if(string.indexOf(substring) == -1 && string != "") {
			app_key = 'PGfWjOmrFPac9yHZD6cWaTSxLPioaUle';
			app_secret = '8aOC02s1W1DOCt2P1Jgsq6oWJT574tU16E9s7w8kOvHucGPk';
			$.getJSON("https://webmaniabr.com/api/1/cep/" + this.value + "/?app_key=" + app_key + "&app_secret=" + app_secret, function(data) {
				if(data.uf != "") {
					$("#idEstado").parent().addClass('is-dirty');
					$("#idCidade").parent().addClass('is-dirty');
					$("#idRua").parent().addClass('is-dirty');
					$("#idBairro").parent().addClass('is-dirty');
					$("#idEstado").val(converterEstados(data.uf));
					$("#idCidade").val(data.cidade);
					$("#idRua").val(data.endereco);
					$("#idBairro").val(data.bairro);
					$("#idCep").val(data.cep);
				}
			});
		}
	});
});