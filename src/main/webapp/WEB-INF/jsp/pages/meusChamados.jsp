<!-- HEADER -->
<jsp:include page="includes/header.jsp" />
<!-- HEADER -->
<!-- TAGS -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!-- TAGS -->
<!-- INICIO BODY -->

<!-- DOWNLOAD EXCEL -->
<jsp:include page="includes/jquery/excel/downloadExcel.jsp" />
<!-- DOWNLOAD EXCEL -->


<script>

function acao(valor){
	document.getElementById("acao").value = valor;
}

</script>


<!-- start: page -->
<section class="panel">
					<header class="panel-heading">
						<div class="panel-actions">
							<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
							<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
						</div>
		
						<h2 class="panel-title">Meus Chamados</h2>
						</header>
						<div class="panel-body">
							<table class="table table-bordered table-striped mb-none" id="datatable-default" style="overflow:auto">
									<thead>
										<tr>
											<th style="max-width:3px">Chamado</th>
											<th>Previsão de Fechamento</th>
											<th>Status</th>
											<th>Massivo</th>
											<th>Abertura</th>
											<th>Solicitante</th>
											<th>Atendente</th>
											<th>Grupo</th>
											<th>Assunto</th>
											<th>Descrição</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${chamados }" var="p">
											<tr class="gradeX">
												<td><a href="/atenderChamado/${p.numeroChamado }">${p.numeroChamado }</a></td>
												<td>${p.previsaoFechamento }</td>
												<td>
												<c:if test="${p.status.nome == 'Encerrado'}">
													<span>${p.status.nome }</span>
												</c:if>
												<c:if test="${p.status.nome == 'Em Andamento'}">
													<span style="color:green">${p.status.nome }</span>
												</c:if>
												<c:if test="${p.status.nome == 'Aberto'}">
													<span style="color:red">${p.status.nome }</span>
												</c:if>
												<c:if test="${p.status.nome == 'Pendente Usuário' || p.status.nome == 'Pendente Usuário'}">
													<span style="color:orange">${p.status.nome }</span>
												</c:if>
												</td>
												<c:if test="${p.massivo }">
													<td style="color:red">Sim</td>
												</c:if>
												<c:if test="${!p.massivo }">
													<td style="color:blue">Não</td>
												</c:if>
												<td>
												${p.dataAbertura }
												</td>
												<td>${p.solicitante.nome }</td>
												<td>${p.atendente.nome }</td>
												<td>${p.destinoGrupo.nome }</td>
												<td>${p.destinoAssunto.nome }</td>
												<td>${p.descricaoChamado }</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="panel-footer">
								<button type="button" class="btn btn-primary" onclick="tableToExcel('datatable-default', 'Documento')">Download</button>
							</div>
						</section>

<!-- end: page -->
	</section>
</div>







<!-- FIM BODY -->
</div>
<!-- FIM BODY -->
<!-- MAIN NO HEADER -->
</main>
<!-- HEADER -->
<jsp:include page="includes/footer.jsp" />
<!-- HEADER -->