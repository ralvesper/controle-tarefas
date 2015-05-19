<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
		<div class="row">
			
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					<div class="alert alert-danger" role="alert">
						<strong>${SPRING_SECURITY_LAST_EXCEPTION.message}</strong>
					</div>
				</c:if>
				
				<div class="account-wall">
					<h1 class="text-center login-title">
						<span class="glyphicon glyphicon-book"></span> Controle de Tarefas
					</h1>
					<c:url value="/login" var="loginUrl" />
					<form class="form-signin" action="${loginUrl}" method="post">
						
						<input type="email" class="form-control" placeholder="E-mail"
							id="username" name="username" required autofocus> 
						<input type="password" class="form-control" placeholder="Senha"
							id="password" name="password" required>
						<button class="btn btn-lg btn-primary btn-block" type="submit">	Entrar</button>
						<label class="checkbox pull-left"></label>
					
					</form>
				</div>
				
				<br />
				<!--Exibe a lista de usuários para teste  -->
				<c:if test="${exibeUsuarios}">
					<div class="alert alert-warning" id="mensagem">
						<table border="1" style="border-color: #c09853">
							<tr>
								<th colspan="3" style="text-align: center">Usuários</th>
							</tr>
							<tr>
								<th>Login</th>
								<th>Senha</th>
								<th>Perfil</th>
							</tr>
							<tr>
								<td>fulano@localhost</td>
								<td>secret</td>
								<td>Administrador</td>
							</tr>
							<tr>
								<td>sicrano@localhost</td>
								<td>secret</td>
								<td>Usuário</td>
							</tr>
							<tr>
								<td>beltrano@localhost</td>
								<td>secret</td>
								<td>Usuário</td>
							</tr>
							<tr>
								<td>johndoe@localhost</td>
								<td>secret</td>
								<td>Administrador</td>
							</tr>
						</table>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
