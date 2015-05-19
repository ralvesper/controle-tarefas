<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Controle de Tarefas</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet">
<link href='<c:url value="/css/style.css"/>' rel="stylesheet">
<link href='<c:url value="/css/jquery-ui.custom.css"/>' rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<nav class="navbar navbar-default navbar-fixed-top navbar-inverse"
					role="navigation">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#"><span class="glyphicon glyphicon-book"></span> Controle de Tarefas</a>
					</div>

					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<sec:authorize access="hasRole('ROLE_ADMINISTRADOR')" var="isAdmin">
							<ul class="nav navbar-nav">
								<li>
									<button type="submit" class="btn btn-primary"
										data-bind="visible: isAdmin, click:  $root.mostrarCriarTarefa"
										style="margin-top: .75em; margin-left: 30px;">
										<span class="glyphicon glyphicon-plus"></span> Tarefa
									</button>
								</li>
							</ul>
						</sec:authorize>
						
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown">
								<a class="dropdown-toggle" href="#"	data-toggle="dropdown">
									<sec:authentication property="principal.nome"/><strong class="caret"></strong>
								</a>
								<div class="dropdown-menu" style="padding: 10px; min-width: 120px;">
									<c:url value="/logout" var="logoutUrl" />
									<form action="${logoutUrl}" method="post" role="form" class="form-horizontal">
										<input class="btn btn-primary" data-bind="click: sair"
											style="margin-top: .75em; width: 100%; height: 32px; font-size: 13px;"
											type="submit" name="commit" value="Sair">
									</form>
								</div>
							<li>
						</ul>
					</div>
				</nav>
			</div>
			
			<div class="row clearfix">
				<!-- Painel de mensagens -->
				<div class="alert" id="mensagem" style="display: none;"></div>
				
				<ul id="tabsTarefas" class="nav nav-tabs">
					<!-- Aba Minhas Tarefas  -->
					<li data-bind="visible: !isAdmin">
						<a href="#"	id="minhasTarefas" role="tab" data-toggle="tab"> 
							<span class="glyphicon glyphicon-user"></span> Minhas Tarefas
						</a>
					</li>
				    <!-- Aba Tarefas Cadastradas  -->
					<li data-bind="visible: !isAdmin">
						<a href="#" id="tarefasCadastradas" role="tab" data-toggle="tab">
							<span class="glyphicon glyphicon-list"></span> Tarefas Cadastradas 
						</a>
					</li>
    				<!-- Aba Tarefas -->
					<li data-bind="visible: isAdmin">
						<a href="#" id="tarefas" role="tab" data-toggle="tab"> 
							<span class="glyphicon glyphicon glyphicon-list-alt"></span> Tarefas
						</a>
					</li>

				</ul>
				
				<!-- Grid Tarefas  -->
				<div id="gridTarefas">
					<table class="table">
						<thead>
							<tr data-bind="foreach: abaAtiva().colunasGrid">
								<th data-bind="text: $data"></th>
							</tr>
						</thead>
						<tbody data-bind="foreach: tarefas">
							<tr	data-bind="css: { success: isConcluida(), active: isCadastrada(), warning: isIniciada(), 
								danger: isDescartada()  }">
								<td	data-bind="visible: $parent.isColunaGridVisivel('Código'), text: codigo()"></td>
								<td	data-bind="visible: $parent.isColunaGridVisivel('Data Criação'), text: dataCriacaoFormatada"></td>
								<td	data-bind="visible: $parent.isColunaGridVisivel('Título'), text: titulo()"></td>
								<td	data-bind="visible: $parent.isColunaGridVisivel('Data Devida'), text: dataDevidaFormatada"></td>
								<td	data-bind="visible: $parent.isColunaGridVisivel('Status'),  text: descricaoStatus()"></td>
								<td data-bind="visible: $parent.isColunaGridVisivel('Data Conclusão'), text: dataConclusaoFormatada"></td>
								<td	data-bind="visible: $parent.isColunaGridVisivel('Responsável'), text: responsavel()"></td>
								<td data-bind="visible: $parent.isColunaGridVisivel('Ações')">
									
									<button title="Concluir" data-bind="visible: isIniciada(), click:  $parent.concluir"
										type="submit" class="btn">
										<span class="glyphicon glyphicon-ok"></span>
									</button>
									
									<button title="Descartar"
										data-bind="visible: isIniciada(), click:  $parent.descartar"
										type="submit" class="btn">
										<span class="glyphicon glyphicon-remove"></span>
									</button>
									
									<button title="Iniciar"
										data-bind="visible: isCadastrada(), click:  $parent.iniciar"
										type="submit" class="btn">
										<span class="glyphicon glyphicon-user"></span>
									</button>
								
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
            <!-- Popup para Nova Tarefa  -->
			<div id="modalCriarTarefa" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span>
							</button>
							<h4 class="modal-title">Nova Tarefa</h4>
						</div>
						
						<div class="modal-body">
							<div class="alert alert-danger" id="mensagem-modal"style="display: none;"></div>
							
							<form id="formNovaTarefa" method="post">
								<table>
									<tbody>
										<tr>
											<td>Título:</td>
											<td><input type="text" name="titulo" id="titulo"
												data-bind="value: $root.novaTarefa().titulo"
												style="width: 300px" maxlength="50"></td>
										</tr>
										<tr>
											<td>Descrição:</td>
											<td><textarea name="descricao" id="descricao"
													data-bind="value: $root.novaTarefa().descricao"
													style="width: 300px; height: 100px" maxlength="200"></textarea></td>
										</tr>
										<tr>
											<td>Data Devida:</td>
											<td><input type="text" readonly="readonly"
												name="dataDevida" id="dataDevida"
												data-bind="value: $root.novaTarefa().dataDevida"
												style="width: 100px"></td>

										</tr>
									</tbody>
								</table>
							</form>
						</div>
						
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Fechar</button>
							<button type="button" class="btn btn-primary"
								data-bind="click:  $root.salvarTarefa">Salvar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>
			var isAdmin = '${isAdmin}';
			var usuario = '<sec:authentication property="principal.username" htmlEscape="false"/>';
		</script>

		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/jquery.min.js"/>' /></script>
		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/jquery-ui.min.js"/>' /></script>
		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/moment.js"/>' /></script>
		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/bootstrap.min.js"/>' /></script>
		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/knockout.js"/>' /></script>
		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/sockjs.js"/>' /></script>
		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/stomp.js"/>' /></script>

		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/controle-tarefas-utils.js"/>' /></script>
		<script type="text/javascript" charset="utf-8"
			src='<c:url value="/js/controle-tarefas.js"/>' /></script>

	</div>
</body>
</html>

