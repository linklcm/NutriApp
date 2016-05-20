PrimeFaces.locales['pt_BR'] = {
	closeText : 'Fechar',
	prevText : 'Anterior',
	nextText : 'Próximo',
	currentText : 'Começo',
	monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
	monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
			'Set', 'Out', 'Nov', 'Dez' ],
	dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta',
			'Sábado' ],
	dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb' ],
	dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
	weekHeader : 'Semana',
	firstDay : 0,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'Horas',
	timeText : 'Tempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	currentText : 'Hora atual',
	ampm : false,
	month : 'Mês',
	week : 'Semana',
	day : 'Dia',
	allDayText : 'Todo Dia'
};

function up(lstr) { // converte minusculas em maiusculas
	var str = lstr.value; // obtem o valor
	lstr.value = str.toUpperCase(); // converte as strings e retorna ao campo
}

function validarData(campo) {
	var expReg = /^(([0-2]\d|[3][0-1])\/([0]\d|[1][0-2])\/[1-2][0-9]\d{2})$/;
	var msgErro = 'Formato inválido de data.';
	if ((campo.value.match(expReg)) && (campo.value != '')) {
		var dia = campo.value.substring(0, 2);
		var mes = campo.value.substring(3, 5);
		var ano = campo.value.substring(6, 10);
		if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
			alert("O mês especificado contém no máximo 30 dias.");
			return campo.value = "";
		} else {
			if (ano % 4 != 0 && mes == 2 && dia > 28) {
				alert("O mês especificado contém no máximo 28 dias.");
				return campo.value = "";
			} else {
				if (ano % 4 == 0 && mes == 2 && dia > 29) {
					alert("O mês especificado contém no máximo 29 dias.");
					return campo.value = "";
				} else {
					return true;
				}
			}
		}
	} else if (campo.value == '__/__/____') {
		return true;
	} else if (campo.value == '') {
		return true;
	} else {
		alert(msgErro);
		return campo.value = "";
	}
}


function validarMesAno(campo) {
	   var expReg = /^(0[1-9]|1[0-2])\/((1[2-9]|[2-9][0-9])[0-9]{2})$/;
	   var msgErro = 'Formato inválido de data.';

	   if ((expReg.test(campo))) {
	       
	    return true;
	     
	   } else if (campo == '__/____') {
	    return true;
	   } else if (campo == '') {
	    return true;
	   } else {
	    alert(msgErro);
	    return campo = "";
	   }
	  }


function Mascara_Hora(Hora, campo) {
	var hora01 = '';
	hora01 = hora01 + Hora;
	if (hora01.length == 2) {
		hora01 = hora01 + ':';
		campo.value = hora01;
	}
	if (hora01.length == 5) {
		Verifica_Hora(campo);
	}
}

function Verifica_Hora(campo) {
	hrs = (campo.value.substring(0, 2));
	min = (campo.value.substring(3, 5));
	estado = "";
	if ((hrs < 00) || (hrs > 23) || (min < 00) || (min > 59)) {
		estado = "errada";
	}

	if (campo.value == "") {
		estado = "errada";
	}
	if (estado == "errada") {
		alert("Hora inválida!");
		campo.focus();
	}
}

function remove(str, sub) {
	i = str.indexOf(sub);
	r = "";
	if (i == -1)
		return str;
	r += str.substring(0, i) + remove(str.substring(i + sub.length), sub);
	return r;
}

function removeMascara(object) {
	valor = object;
	semMascara = remove(valor, ".");
	semMascara = remove(semMascara, "-");
	semMascara = remove(semMascara, "/");
	semMascara = remove(semMascara, "(");
	semMascara = remove(semMascara, ")");
	semMascara = remove(semMascara, "_");

	return semMascara;
}

/**
 * MASCARA ( mascara(o,f) e execmascara() ) CRIADAS POR ELCIO LUIZ elcio.com.br
 */
function mascara_cpf_cnpj(o, f) {
	v_obj = o
	v_fun = f
	setTimeout("execmascara()", 1)
}

function execmascara() {
	v_obj.value = v_fun(v_obj.value)
}

function cpf_cnpj_mask(v) {
	v = v.replace(/\D/g, "") // Remove tudo o que não é dígito
	if (v.length > 11) { // cpnj
		v = v.replace(/(\d{2})(\d)/, "$1.$2") // Coloca ponto entre o segundo
												// e o terceiro dígitos
		v = v.replace(/(\d{3})(\d)/, "$1.$2") // Coloca ponto entre o sexto e
												// o setimo dígitos
		v = v.replace(/(\d{3})(\d)/, "$1/$2") // Coloca barra entre o décimo e
												// o decimoprimeiro dígitos
		v = v.replace(/(\d{4})(\d)/, "$1-$2") // Coloca ponto entre o
												// decimoquito e o decimosexto
												// dígitos
	} else { // cpf
		v = v.replace(/(\d{3})(\d)/, "$1.$2") // Coloca ponto entre o terceiro
												// e o quarto dígitos
		v = v.replace(/(\d{3})(\d)/, "$1.$2") // Coloca ponto entre o setimo e
												// o oitava dígitos
		v = v.replace(/(\d{3})(\d)/, "$1-$2") // Coloca ponto entre o
												// decimoprimeiro e o
												// decimosegundo dígitos
	}
	return v
}

function somenteNumeros(num) {
	var er = /[^0-9]/;
	er.lastIndex = 0;
	if (er.test(num.value)) {
		num.value = num.value.replace(/[^0-9]/g, "");
	}
}

function somenteNumerosEvirgula(num) {
	var er = /[^0-9,]/;
	er.lastIndex = 0;
	if (er.test(num.value)) {
		num.value = num.value.replace(/[^0-9,]/g, "");
	}
}

function moeda(z) {
	v = z.value;
	v = v.replace(/\D/g, ""); // permite digitar apenas números
	v = v.replace(/[0-9]{12}/, "inválido"); // limita pra máximo 999.999.999,99
	v = v.replace(/(\d{1})(\d{8})$/, "$1.$2"); // coloca ponto antes dos
	// últimos 8 digitos
	v = v.replace(/(\d{1})(\d{5})$/, "$1.$2"); // coloca ponto antes dos
	// últimos 5 digitos
	v = v.replace(/(\d{1})(\d{1,2})$/, "$1,$2"); // coloca virgula antes dos
	// últimos 2 digitos
	z.value = v;
}

function phoneMask(event) {
	var target, phone, element;
	target = (event.currentTarget) ? event.currentTarget : event.srcElement;
	phone = target.value.replace(/\D/g, '');
	element = $(target);
	element.unmask();
	if (phone.length > 10) {
		element.mask("(99) 99999-999?9");
	} else {
		element.mask("(99) 9999-9999?9");
	}

	function validaCPF(cpf) {
		erro = new String;
		aux = removeMascara(cpf.value);
		var nonNumbers = /\D/;

		if (nonNumbers.test(aux)) {
			erro = "A verificação de CPF suporta apenas números!";
		} else if (aux.length == 11) {
			if (aux == "00000000000" || aux == "11111111111"
					|| aux == "22222222222" || aux == "33333333333"
					|| aux == "44444444444" || aux == "55555555555"
					|| aux == "66666666666" || aux == "77777777777"
					|| aux == "88888888888" || aux == "99999999999") {

				erro = "Número de CPF inválido!";
			}

			var a = [];
			var b = new Number;
			var c = 11;

			for (var i = 0; i < 11; i++) {
				a[i] = aux.charAt(i);
				if (i < 9)
					b += (a[i] * --c);
			}

			if ((x = b % 11) < 2) {
				a[9] = 0;
			} else {
				a[9] = 11 - x;
			}
			b = 0;
			c = 11;

			for (var y = 0; y < 10; y++)
				b += (a[y] * c--);

			if ((x = b % 11) < 2) {
				a[10] = 0;
			} else {
				a[10] = 11 - x;
			}

			if ((aux.charAt(9) != a[9]) || (aux.charAt(10) != a[10])) {
				erro = "Número de CPF inválido.";
			}
		} else {
			if (aux.length == 0)
				return false;
			else
				erro = "Número de CPF inválido.";
		}
		if (erro.length > 0) {
			alert(erro);
			cpf.value = "";
			return false;
		}
		return true;
	}

}

function somenteLetras(letra) {
	var reg = /[^a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\-\'\s]/;
	reg.lastIndex = 0;
	if (reg.test(letra.value)) {
		letra.value = letra.value.replace(/[^a-zA-ZéúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\-\'\s]/g, "");
	}
}
