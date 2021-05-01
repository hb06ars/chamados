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

<script>
function redirecionar(link){
	window.location.href=link;
}
</script>
									    	
<!-- start: page -->
<div class="row">
	<div class="col-md-6 col-lg-12 col-xl-6">
		<section class="panel">
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-8">
						<div class="chart-data-selector" id="salesSelectorWrapper">
							<h2>
								<strong>
									<select class="form-control" id="salesSelector">
										<option value="Manha" >CHAMADOS</option>
									</select>
								</strong>
							</h2>
							<div id="salesSelectorItems" class="chart-data-selector-items mt-sm">
								<!-- Flot: Sales Porto Admin -->
								<div class="chart chart-sm" data-sales-rel="Manha" id="flotDashSales1" class="chart-active"></div>
								<script>
									var flotDashSales1Data = [{
									    data: [
									    	["ABERTOS", ${todosAbertos }],
									    	["VENCIDOS", ${todosVencidos }],
									    	["ANDAMENTO", ${todosAndamentos }],
									    	["ENCERRADOS", ${todosEncerrados }],
									    ],
									    color: "#0088cc"
									}];
								</script>

								<!-- Flot: Sales Porto Drupal -->
								<div class="chart chart-sm" data-sales-rel="Tarde" id="flotDashSales2" class="chart-hidden"></div>
								<script>
									var flotDashSales2Data = [{
									    data: [
									    	<c:forEach items="${mesesTarde }" var="m">
									    	["${m.nome1 }", ${m.valor1 }],
									    	</c:forEach>
									    ],
									    color: "#2baab1"
									}];
								</script>
								
								<!-- Flot: Sales Porto Wordpress -->
								<div class="chart chart-sm" data-sales-rel="Noite" id="flotDashSales3" class="chart-hidden"></div>
								<script>
									var flotDashSales3Data = [{
									    data: [
									    	<c:forEach items="${mesesNoite }" var="m">
									    	["${m.nome1 }", ${m.valor1 }],
									    	</c:forEach>
									    ],
									    color: "#734ba9"
									}];
								</script>
								
								
								
								
								
							</div>

						</div>
					</div>
					<div class="col-lg-4 text-center">
						<h2 class="panel-title mt-md">Chamados perdidos</h2>
						<div class="liquid-meter-wrapper liquid-meter-sm mt-lg">
							<div class="liquid-meter">
								<meter min="0" max="100" value="${(todosVencidos  * 100) / todosChamados }" id="meterSales"></meter>
							</div>
							<div class="liquid-meter-selector" id="meterSalesSel">
								<a class="active">${todosVencidos } CHAMADO(S) PERDIDOS DE ${todosChamados }.</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<div class="col-md-6 col-lg-12 col-xl-6">
		<div class="row">
			<div class="col-md-12 col-lg-6 col-xl-6">
				<section class="panel panel-featured-left panel-featured-primary">
					<div class="panel-body">
						<div class="widget-summary">
							<div class="widget-summary-col widget-summary-col-icon">
								<div class="summary-icon bg-primary">
									<i class="fa fa-cogs"></i>
								</div>
							</div>
							<div class="widget-summary-col" style="cursor:pointer">
								<div class="summary">
									<h4 class="title">Chamados Abertos</h4>
									<div class="info">
										<strong class="amount">${todosAbertos }</strong>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
			<div class="col-md-12 col-lg-6 col-xl-6">
				<section class="panel panel-featured-left panel-featured-tertiary">
					<div class="panel-body">
						<div class="widget-summary">
							<div class="widget-summary-col widget-summary-col-icon">
								<div class="summary-icon bg-tertiary">
									<i class="fa fa-cogs"></i>
								</div>
							</div>
							<div class="widget-summary-col" style="cursor:pointer">
								<div class="summary">
									<h4 class="title">Chamados Encerrados</h4>
									<div class="info">
										<strong class="amount">${todosEncerrados }</strong>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
			<div class="col-md-12 col-lg-6 col-xl-6">
				<section class="panel panel-featured-left panel-featured-danger">
					<div class="panel-body">
						<div class="widget-summary">
							<div class="widget-summary-col widget-summary-col-icon">
								<div class="summary-icon bg-danger">
									<i class="fa fa-cogs"></i>
								</div>
							</div>
							<div class="widget-summary-col" style="cursor:pointer">
								<div class="summary">
									<h4 class="title">Chamados Vencidos</h4>
									<div class="info">
										<strong class="amount">${todosVencidos }</strong>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
			<div class="col-md-12 col-lg-6 col-xl-6">
				<section class="panel panel-featured-left panel-featured-success">
					<div class="panel-body">
						<div class="widget-summary">
							<div class="widget-summary-col widget-summary-col-icon">
								<div class="summary-icon bg-success">
									<i class="fa fa-cogs"></i>
								</div>
							</div>
							<div class="widget-summary-col" style="cursor:pointer">
								<div class="summary">
									<h4 class="title">Chamados Em Andamento</h4>
									<div class="info">
										<strong class="amount">${todosAndamentos }</strong>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</div>
	</div>
</div>





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