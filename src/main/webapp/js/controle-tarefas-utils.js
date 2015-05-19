var DateUtil = {

	criarData : function(dia, mes, ano) {
		return new Date(ano, mes-1, dia);
	},
	
	criarDataHora : function(dia, mes, ano, hora, minuto) {
		return new Date(ano, mes-1, dia, hora, minuto, 0, 0);
	},
	
	formatarData : function(data) {
		return moment(data).format('DD/MM/YYYY');
	},
	
	formatarDataHora : function(data) {
		return moment(data).format('DD/MM/YYYY H:mm');
	}, 
	
	formatarDataISO : function(data) {
		return moment(data, 'DD/MM/YYYY').format('YYYY-MM-DD');
	}
	
	
}

$(function() {
	$.datepicker.setDefaults({
		constrainInput : true,
		showOn : "both",
		dateFormat : 'dd/mm/yy',
		dayNames : [ 'Domingo', 'Segunda-Feira', 'Terça-Feira', 'Quarta-Feira', 'Quinta-Feira', 'Sexta-Feira', 'Sábado' ],
		dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D' ],
		dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb','Dom' ],
		monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro',	'Dezembro' ],
		monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
		prevText : 'Anterior',
		nextText : 'Próximo'
	});
});