var StatusTarefa = {
	CADASTRADA : {
		nome : 'CADASTRADA',
		label : 'Cadastrada'
	},
	INICIADA : {
		nome : 'INICIADA',
		label : 'Iniciada'
	},
	CONCLUIDA : {
		nome : 'CONCLUIDA',
		label : 'Concluida'
	},
	DESCARTADA : {
		nome : 'DESCARTADA',
		label : 'Descartada'
	}
}

var AcaoTarefa = {
	INICIAR : {
		url : 'tarefas/{codigoTarefa}/iniciar'
	},
	CONCLUIR : {
		url : 'tarefas/{codigoTarefa}/concluir'
	},
	DESCARTAR : {
		url : 'tarefas/{codigoTarefa}/descartar'
	},
	CRIAR : {
		url : 'tarefas/criar'
	}
}

var GridColunas = {
	MINHAS_TAREFAS : [ 'Código', 'Data Criação', 'Título', 'Data Devida',
			'Status', 'Data Conclusão', 'Ações' ],
	TAREFAS_CADASTRADAS : [ 'Código', 'Data Criação', 'Título',
			'Data Devida', 'Ações' ],
	TAREFAS : [ 'Código', 'Data Criação', 'Título', 'Data Devida', 'Status',
			'Data Conclusão', 'Responsável' ]
}

function Aba(aba, nomeAba, url, colunasGrid, viewModel) {
    
	var self = this;	
	
	self.aba = aba;
	
	self.nomeAba = nomeAba;
	
	self.url = url;
	
	self.colunasGrid = ko.observableArray(colunasGrid);
	
	self.viewModel = viewModel;
	
	self.carregar = function() {
		$.ajax({
			url : self.url,
		}).done(function(data) {
			viewModel.adicionarTarefas(data);
		}).fail(function() {
			viewModel.mensagem.renderizarMensagemErro('Erro ao carregar dados da aba [' + self.nomeAba + ']');
		 });
	};

	self.mostrar = function() {
		$(aba).tab('show');
	};

}

function Tarefa(codigo, dataCriacao, titulo, dataDevida, status, dataConclusao, responsavel) {
	
	var self = this;
	
	self.codigo = ko.observable(codigo);
	
	self.dataCriacao = ko.observable(dataCriacao);
	
	self.titulo = ko.observable(titulo);
	
	self.descricao = ko.observable();
	
	self.dataDevida = ko.observable(dataDevida);
	
	self.status = ko.observable(status);
	
	self.dataConclusao = ko.observable(dataConclusao);
	
	self.responsavel = ko.observable(responsavel);
	
	self.isIniciada = function() {return self.status() === StatusTarefa.INICIADA.nome};
	
	self.isCadastrada = function() {return self.status() === StatusTarefa.CADASTRADA.nome};
	
	self.isConcluida = function() {return self.status() === StatusTarefa.CONCLUIDA.nome};
	
	self.isDescartada = function() {return self.status() === StatusTarefa.DESCARTADA.nome};
	
	self.descricaoStatus = ko.computed(function() {return StatusTarefa[self.status()].label});
	
	self.dataCriacaoFormatada = ko.computed(function() {
		if(self.dataCriacao()) {
			return DateUtil.formatarDataHora(self.dataCriacao());
		}
		return '-';
	});
	
	self.dataDevidaFormatada = ko.computed(function() {
		if(self.dataDevida()) {
			return DateUtil.formatarData(self.dataDevida());
			return '-';
		}
	});
	
	self.dataConclusaoFormatada = ko.computed(function() {
		if (self.dataConclusao()) {
			return DateUtil.formatarDataHora(self.dataConclusao())
		}
		return '-';
	});
	
}

function NovaTarefa() {
	
	var self = this;
	
	self.titulo = ko.observable('');
	
	self.descricao = ko.observable('');
	
	self.dataDevida = ko.observable('');
	
	self.validar = function() {
		var mensagens = new Array();
		if(self.titulo().trim() === '') {
			mensagens.push('Título é obrigatório!');
		}
		if (self.descricao().trim() === '') {
			mensagens.push('Descrição é obrigatória!');
		}
		if (self.dataDevida().trim() === '') {
			mensagens.push('Data devida é obrigatória!');
		}
		return {valido : mensagens.length == 0, mensagens: mensagens}; 
	},
	
	self.toJSON = function() {
		var copia = ko.toJS(this);
		copia.dataDevida = DateUtil.formatarDataISO(self.dataDevida());
		return copia;
	}
}

function Mensagem(container) {
	
	var self = this;

	self.container = container;
	
	self.TipoMensagem = {
		ERRO : {
			value : 'ERRO'
		},
		SUCESSO : {
			value : 'SUCESSO'
		},
		AVISO : {
			value : 'AVISO'
		}
	};
	
	self.renderizarMensagemSucesso = function(mensagem) {
		$(self.container).removeClass('alert-warning').removeClass('alert-danger')
				.addClass('alert-success').empty().append(mensagem).show();
	};

	self.renderizarMensagemErro = function(mensagem) {
		$(self.container).removeClass('alert-warning')
			.removeClass('alert-success').addClass('alert-danger').empty().append(mensagem).show();
	};
	
	self.renderizarMensagemAviso = function(mensagem) {
		$(self.container).removeClass('alert-success').removeClass('alert-danger')
			.addClass('alert-warning').empty().append(mensagem).show();
	};
	
	self.tratarMensagem = function(mensagem) {
		if (self.TipoMensagem.SUCESSO.value === mensagem.tipo) {
			self.renderizarMensagemSucesso(mensagem.mensagem);
			return true;
		}
		if (self.TipoMensagem.ERRO.value === mensagem.tipo) {
			self.renderizarMensagemErro(mensagem.mensagem);
		}
	}
}

function ControleTarefas(isAdmin, usuario) {

	var self = this;
	
	self.stompClient;

	self.usuario = usuario;

	self.abas = new ko.observableArray([
	         new Aba($('#minhasTarefas'),'Minhas Tarefas', 'tarefas/buscar/responsavel/' + self.usuario, GridColunas.MINHAS_TAREFAS, self), 
			 new Aba($('#tarefasCadastradas'), 'Tarefas Cadastradas', 'tarefas/buscar/status/' + StatusTarefa.CADASTRADA.nome, GridColunas.TAREFAS_CADASTRADAS, self), 
			 new Aba($('#tarefas'), 'Tarefas', 'tarefas/buscar', GridColunas.TAREFAS, self)]);

	self.abaAtiva = ko.observable(self.abas()[0]);
	
	self.mensagem = new Mensagem($('#mensagem'))

	self.isAdmin = isAdmin === 'true';

	self.tarefas = ko.observableArray([]);
	
	self.novaTarefa = ko.observable({});
	
	self.adicionarTarefas = function(tarefas) {
		
		self.tarefas.removeAll()
		
		for ( var i in tarefas) {
			self.adicionarTarefa(tarefas[i]);
		}
	};
	
	self.adicionarTarefa = function(tarefa) {
		var codigo = tarefa.identificador.codigo;
		
		var dataCriacao = DateUtil.criarDataHora(tarefa.dataCadastro[2], tarefa.dataCadastro[1], 
				tarefa.dataCadastro[0], tarefa.dataCadastro[3], tarefa.dataCadastro[4]);
		
		var titulo = tarefa.titulo;
		
		var dataDevida = DateUtil.criarData(tarefa.dataDevida[2], tarefa
				.dataDevida[1], tarefa.dataDevida[0]);
		
		var status = tarefa.status.nome;
		
		var dataConclusao = tarefa.dataConclusao ? DateUtil.criarDataHora(tarefa.dataConclusao[2], 
				tarefa.dataConclusao[1], tarefa.dataConclusao[0], tarefa.dataConclusao[3], 
				tarefa.dataConclusao[4]) : null;
		
		var responsavel = tarefa.responsavel ? tarefa.responsavel.nome : '-';
		
		var tarefa = new Tarefa(codigo, dataCriacao, titulo, dataDevida, status, dataConclusao, responsavel);

		self.tarefas.push(tarefa);
	};
	
	self.getTarefa = function(codigo) {
		var existente = ko.utils.arrayFirst(self.tarefas(), function(tarefa) {
			return tarefa.codigo() === codigo;
		});
		return existente;
	};
	
	self.atualizarTarefa = function(tarefa) {
		var existente = self.getTarefa(tarefa.identificador.codigo);
		if (existente) {
			existente.status(tarefa.status.nome);
			existente.responsavel(tarefa.responsavel.nome);
			var dataConclusao = tarefa.dataConclusao ? DateUtil.criarDataHora(tarefa.dataConclusao[2], 
					tarefa.dataConclusao[1], tarefa.dataConclusao[0], tarefa.dataConclusao[3], 
					tarefa.dataConclusao[4]) : null;
			existente.dataConclusao(dataConclusao);
			self.mensagem.renderizarMensagemAviso('Tarefa [' + tarefa.identificador.codigo + '] atualizada!');
		} else {
			self.adicionarTarefa(tarefa);
			self.ordenarTarefas();
			self.mensagem.renderizarMensagemAviso('Tarefa [' + tarefa.identificador.codigo + '] adicionada!');
		}
	};
	

	self.ordenarTarefas = function() {
		self.tarefas.sort(function(left, right) {
			return left.dataDevida() == right.dataDevida() ? 0 : (left
					.dataDevida() < right.dataDevida() ? -1 : 1)
		})
	};
	
	self.iniciar = function(tarefa) {
		self.executarAcaoNaTarefa(tarefa.codigo(), AcaoTarefa.INICIAR);
	};

	self.concluir = function(tarefa) {
		self.executarAcaoNaTarefa(tarefa.codigo(), AcaoTarefa.CONCLUIR);
	};
	
	self.descartar = function(tarefa) {
		self.executarAcaoNaTarefa(tarefa.codigo(), AcaoTarefa.DESCARTAR);
	};
	
	self.carregarAbaAtiva = function() {
		self.abaAtiva().mostrar();
		self.abaAtiva().carregar();
	};

	self.bindClickAba = function() {
		$('#tabsTarefas a').click(function(e) {
			e.preventDefault();
			var idAba = $(this).attr('id');
			self.abas().forEach(function(aba) {
			    if($(aba.aba).attr('id') == idAba) {
			    	self.abaAtiva(aba);
			    }
			});
			self.carregarAbaAtiva();
		})
	};

	self.isColunaGridVisivel = function(coluna) {
		return self.abaAtiva().colunasGrid().indexOf(coluna) >= 0;
	}; 

	self.mostrarCriarTarefa = function() {
		self.novaTarefa(new NovaTarefa());
		$('#mensagem-modal').empty().hide();
		$('#modalCriarTarefa').modal();
	};
	
	self.salvarTarefa = function() {
		var validacao = self.novaTarefa().validar();
		if (validacao.valido) {
			$.ajax({url: AcaoTarefa.CRIAR.url, 
					data: ko.toJSON(self.novaTarefa), 
					type: 'POST',
					contentType: 'application/json',
					dataType:'json'})
			.done(function(data) {
				var sucesso = self.mensagem.tratarMensagem(data);
				if (sucesso) {
					$('#modalCriarTarefa').modal('hide');
				}
			}).fail(function() {
				self.mensagem.renderizarMensagemErro('Erro inesperado ao criar Tarefa [' + self.novaTarefa().titulo()+']');
			 });
		} else {
			$('#mensagem-modal').empty();
			validacao.mensagens.forEach(function(mensagem) {
				$('#mensagem-modal').append('<p>').append(mensagem).append('</p>');
			});
			$('#mensagem-modal').show();
		}
		
	};
	
	self.bindFecharMensagemClick = function() {
		$('#mensagem').click(function() {
			$(this).empty().hide();
		})

	};

	self.inicializar = function() {
		if (self.isAdmin) {
			self.abaAtiva(self.abas()[2]);
			self.registrarListenerTarefas();
		} else {
			self.abaAtiva(self.abas()[0]);
		}
		self.bindFecharMensagemClick();
		self.bindClickAba();
		self.carregarAbaAtiva();
		
		$("#dataDevida").datepicker();
	};
	
	self.executarAcaoNaTarefa = function(codigoTarefa, acao) {
		var url = acao.url;
		if (codigoTarefa) {
			url = url.replace('{codigoTarefa}', codigoTarefa);
		}
		$.ajax({
			url: url, 
			data: {codigoTarefa : codigoTarefa},
			type: 'PUT'
		}).done(function(data) {
			var sucesso = self.mensagem.tratarMensagem(data);
			if (sucesso) {
				self.abaAtiva().carregar();
			}
		}).fail(function() {
			self.mensagem.renderizarMensagemErro('Erro não esperado ao executar a ação na Tarefa [' + codigoTarefa + ']');
		});
	};
	

	self.registrarListenerTarefas = function() {
		ws = new SockJS('/controle-tarefas/ws');
		self.stompClient = Stomp.over(ws);

		self.stompClient.connect({}, function(frame) {
			
			self.stompClient.subscribe("/topic/updates", function(message) {
				var tarefa = JSON.parse(message.body);
				self.atualizarTarefa(tarefa);
			});

		}, function(error) {
			console.log("Erro em conexão cliente STOMP " + error);
		});
	};
	
	self.sair = function() {
		if (self.isAdmin) {
			if(self.stompClient) {
				 self.stompClient.disconnect();
			}
		}
		return true;
	}

}

$(function() {
	ControleTarefas = new ControleTarefas(isAdmin, usuario);
	ControleTarefas.inicializar();
	ko.applyBindings(ControleTarefas);
});
