<!doctype html>
<html class="fixed">
	<head>

		<!-- Basic -->
		<meta charset="UTF-8">
		<title>Chamados</title>
		<meta name="keywords" content="HTML5 Admin Template" />
		<meta name="description" content="Porto Admin - Responsive HTML5 Template">
		<meta name="author" content="okler.net">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="shortcut icon" href="assets/images/logo.png" type="image/ico">
		<!-- Mobile Metas -->
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<!-- Web Fonts  -->
		<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">
		<!-- Vendor CSS -->
		<link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="/assets/vendor/font-awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="/assets/vendor/magnific-popup/magnific-popup.css" />
		<link rel="stylesheet" href="/assets/vendor/bootstrap-datepicker/css/datepicker3.css" />
		<!-- Specific Page Vendor CSS -->
		<link rel="stylesheet" href="/assets/vendor/jquery-ui/css/ui-lightness/jquery-ui-1.10.4.custom.css" />
		<link rel="stylesheet" href="/assets/vendor/bootstrap-multiselect/bootstrap-multiselect.css" />
		<link rel="stylesheet" href="/assets/vendor/morris/morris.css" />
		<!-- Theme CSS -->
		<link rel="stylesheet" href="/assets/stylesheets/theme.css" />
		<!-- Skin CSS -->
		<link rel="stylesheet" href="/assets/stylesheets/skins/default.css" />
		<!-- Theme Custom CSS -->
		<link rel="stylesheet" href="/assets/stylesheets/theme-custom.css">
		<!-- Loading -->
		<link rel="stylesheet" href="/assets/vendor/loading/loading.css" />
		<!-- Head Libs -->
		<script src="/assets/vendor/modernizr/modernizr.js"></script>
		
		<!-- Tables -->
		<link rel="stylesheet" href="/assets/vendor/select2/select2.css" />
		<link rel="stylesheet" href="/assets/vendor/jquery-datatables-bs3/assets/css/datatables.css" />
		
		
		
		
	</head>
	<body>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<!-- screen loader -->
	    <div class="load_container-fluid h-100 loader-display">
	        <div class="load_row h-100">
	            <div class="align-self-center load_col">
	                <div class="logo-loading">
	                    <div class="icon icon-100 load_mb-4">
	                        <img src="/assets/images/logo.png" alt="" class="load_w-100">
	                    </div>
	                    <h4 class="text-default"></h4>
	                    <div class="loader-ellipsis">
	                        <div></div>
	                        <div></div>
	                        <div></div>
	                        <div></div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>

		<section class="body">
			<!-- start: header -->
			<header class="header">
				<div class="logo-container">
					<a href="/home" class="logo">
						<img src="/assets/images/logo.png" height="35" alt="Porto Admin" />
					</a>
					<div class="visible-xs toggle-sidebar-left" data-toggle-class="sidebar-left-opened" data-target="html" data-fire-event="sidebar-left-opened">
						<i class="fa fa-bars" aria-label="Toggle sidebar"></i>
					</div>
				</div>
			</header>
			<!-- end: header -->

			<div class="inner-wrapper">
				<!-- start: sidebar -->
				<aside id="sidebar-left" class="sidebar-left">
				
					<div class="sidebar-header">
						<div class="sidebar-title">
							Navega��o
						</div>
						<div class="sidebar-toggle hidden-xs" data-toggle-class="sidebar-left-collapsed" data-target="html" data-fire-event="sidebar-left-toggle">
							<i class="fa fa-bars" aria-label="Toggle sidebar"></i>
						</div>
					</div>
				
					<div class="nano">
						<div class="nano-content">
							<nav id="menu" class="nav-main" role="navigation">
								<ul class="nav nav-main">
									<li class="nav-active">
										<a href="/home">
											<i class="fa fa-home" aria-hidden="true"></i>
											<span>Home</span>
										</a>
									</li>
									<li class="nav-parent">
										<a>
											<i class="fa fa-cogs" aria-hidden="true"></i>
											<span>Chamados</span>
										</a>
										<ul class="nav nav-children">
											<li>
												<a href="/atenderChamado">Pesquisar Chamado</a>
											</li>
											<li>
												<a href="/novoChamado">Novo Chamado</a>
											</li>
											<li>
												<a href="/meusChamados">Meus Chamados</a>
											</li>
											<li>
												<a href="/todosChamados">Todos Chamados</a>
											</li>
										</ul>
									</li>
									
									<c:if test="${usuario.perfil.admin == true }">
										<li class="nav-parent">
											<a>
												<i class="fa fa-user" aria-hidden="true"></i>
												<span>Admnistra��o</span>
											</a>
											<ul class="nav nav-children">
												<li>
													<a href="/clientes">Clientes</a>
												</li>
												<li>
													<a href="/funcionarios">Funcion�rios</a>
												</li>
												<li>
													<a href="/grupos">Grupos</a>
												</li>
												<li>
													<a href="/assuntos">Assuntos</a>
												</li>
											</ul>
										</li>
									</c:if>
									
									<li class="nav-parent">
											<a>
												<i class="fa fa-info-circle" aria-hidden="true"></i>
												<span>Contatos</span>
											</a>
											<ul class="nav nav-children">
												<li>
													<a href="https://www.facebook.com">Facebook</a>
												</li>
												<li>
													<a href="https://www.instagram.com">Instagram</a>
												</li>
												<li>
													<a href="https://wa.me/5511989376271">WhatsApp</a>
												</li>
												<li>
													<a style="cursor:pointer" onclick="modalContato()" >Funcionamento</a>
												</li>
												<li>
													<a style="cursor:pointer" onclick="modalAutor()" >Autor</a>
												</li>
											</ul>
										</li>
										
									<li>
										<a href="/alterarSenha" >
											<i class="fa fa-key" aria-hidden="true"></i>
											<span>Alterar senha</span>
										</a>
										<a href="/deslogar" >
											<i class="fa fa-power-off" aria-hidden="true"></i>
											<span>Sair</span>
										</a>
									</li>
								</ul>
							</nav>
				
							
						</div>
				
					</div>
				
				</aside>
				<!-- end: sidebar -->

				<section role="main" class="content-body">
					<header class="page-header">
						<h2>${paginaAtual }</h2>
					
						<div class="right-wrapper pull-right">
							<ol class="breadcrumbs">
								<li>
									<a href="index.html">
										<i class="${iconePaginaAtual }"></i>
									</a>
								</li>
								<li><span>${paginaAtual }</span></li>
							</ol>
					
							<a class="sidebar-right-toggle" data-open="sidebar-right"><i class="fa fa-chevron-left"></i></a>
						</div>
					</header>

					
					<!-- MODAIS -->
					<jsp:include page="modal.jsp" />
					
					
					<!--  ATUALIZAR P�GINA  -->
					<c:if test="${atualizarPagina != null }">
						<script>window.location.href='${atualizarPagina}';</script>
					</c:if>
					<!--  FIM ATUALIZAR P�GINA  -->
					
					<!--  ERRO  -->
					<c:if test="${erro != null}">
						<div class="alert alert-danger">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">�</button>
							<strong>Erro!</strong><br>${erro }
						</div>
					</c:if>
					<!--  FIM ERRO  -->
					
					