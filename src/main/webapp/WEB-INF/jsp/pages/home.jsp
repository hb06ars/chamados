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
								Clientes Presentes:
								<strong>
									<select class="form-control" id="salesSelector">
										<option value="Manha" >Manh�</option>
										<option value="Tarde" >Tarde</option>
										<option value="Noite" >Noite</option>
									</select>
								</strong>
							</h2>
							<div id="salesSelectorItems" class="chart-data-selector-items mt-sm">
								<!-- Flot: Sales Porto Admin -->
								<div class="chart chart-sm" data-sales-rel="Manha" id="flotDashSales1" class="chart-active"></div>
								<script>
									var flotDashSales1Data = [{
									    data: [
									    	<c:forEach items="${mesesManha }" var="m">
									    	["${m.nome1 }", ${m.valor1 }],
									    	</c:forEach>
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
						<h2 class="panel-title mt-md">Presentes ontem</h2>
						<div class="liquid-meter-wrapper liquid-meter-sm mt-lg">
							<div class="liquid-meter">
								<meter min="0" max="100" value="${(presentesOntem  * 100) / todosClientes }" id="meterSales"></meter>
							</div>
							<div class="liquid-meter-selector" id="meterSalesSel">
								<a class="active">${presentesOntem } CLIENTE(S) PRESENTES de ${todosClientes }.</a>
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
									<i class="fa fa-life-ring"></i>
								</div>
							</div>
							<div class="widget-summary-col" onclick="redirecionar('/clientes')" style="cursor:pointer">
								<div class="summary">
									<h4 class="title">Total de Clientes</h4>
									<div class="info">
										<strong class="amount">${todosClientes } clientes</strong>
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
									<i class="fa fa-shopping-cart"></i>
								</div>
							</div>
							<div class="widget-summary-col" onclick="redirecionar('/clientes')" style="cursor:pointer">
								<div class="summary">
									<h4 class="title">Novos clientes do m�s</h4>
									<div class="info">
										<strong class="amount">${novosDoMes } clientes</strong>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
			<div class="col-md-12 col-lg-6 col-xl-6">
				<section class="panel panel-featured-left panel-featured-quartenary">
					<div class="panel-body">
						<div class="widget-summary">
							<div class="widget-summary-col widget-summary-col-icon">
								<div class="summary-icon bg-quartenary">
									<i class="fa fa-user"></i>
								</div>
							</div>
							<div class="widget-summary-col" onclick="redirecionar('/aniversariantes')" style="cursor:pointer">
								<div class="summary">
									<h4 class="title">Aniversariantes</h4>
									<div class="info">
										<strong class="amount">${clientesAniversariantes }</strong>
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