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

function redirecionar(){
	var link = document.getElementById("pesqChamado").value;
	window.location.href = "/atenderChamado/"+link;
}

function assuntoSelecionado(){
	var id = document.getElementById("assunto").value;
	<c:forEach items='${assuntos}' var='a'>
		if(id == '${a.id}'){
			if(${a.horasAtendimento} <= 1){
				document.getElementById("horasAtendimento").value = ${a.horasAtendimento}+'h';	
			}
			else if(${a.horasAtendimento} > 1 && ${a.horasAtendimento} < 24){
				document.getElementById("horasAtendimento").value = ${a.horasAtendimento}+'hs';
			}
			else if(${a.horasAtendimento} == 24){
				document.getElementById("horasAtendimento").value = '1 dia (24hs)';
			}
			else if(${a.horasAtendimento} == 48){
				document.getElementById("horasAtendimento").value = '2 dias (48hs)';
			}
			else {
				document.getElementById("horasAtendimento").value = ${a.horasAtendimento}+'hs';
			}
			
		}
	</c:forEach>
}

function listar(){
	var id = document.getElementById("grupo").value;
	document.getElementById("assunto").options.length=0;
	var select_assunto = document.getElementById("assunto");
	
	var option_inicial = document.createElement("option");
	option_inicial.text = '-- Selecione o Assunto --';
	option_inicial.value = 0;
	select_assunto.add(option_inicial);
	
	<c:forEach items='${assuntos}' var='a'>
		if(id == '${a.grupo.id}'){
			var option = document.createElement("option");
			option.text = '${a.nome}';
			option.value = ${a.id};
			select_assunto.add(option);
		}
	</c:forEach>
	
}


function fMasc(objeto,mascara) {
	obj=objeto
	masc=mascara
	setTimeout("fMascEx()",1)
}
function fMascEx() {
	obj.value=masc(obj.value)
}
function mTel(tel) {
	tel=tel.replace(/\D/g,"")
	tel=tel.replace(/^(\d)/,"($1")
	tel=tel.replace(/(.{3})(\d)/,"$1)$2")
	if(tel.length == 9) {
		tel=tel.replace(/(.{1})$/,"-$1")
	} else if (tel.length == 10) {
		tel=tel.replace(/(.{2})$/,"-$1")
	} else if (tel.length == 11) {
		tel=tel.replace(/(.{3})$/,"-$1")
	} else if (tel.length == 12) {
		tel=tel.replace(/(.{4})$/,"-$1")
	} else if (tel.length > 12) {
		tel=tel.replace(/(.{4})$/,"-$1")
	}
	return tel;
}
function mCNPJ(cnpj){
	cnpj=cnpj.replace(/\D/g,"")
	cnpj=cnpj.replace(/^(\d{2})(\d)/,"$1.$2")
	cnpj=cnpj.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")
	cnpj=cnpj.replace(/\.(\d{3})(\d)/,".$1/$2")
	cnpj=cnpj.replace(/(\d{4})(\d)/,"$1-$2")
	return cnpj
}
function mCPF(cpf){
	cpf=cpf.replace(/\D/g,"")
	cpf=cpf.replace(/(\d{3})(\d)/,"$1.$2")
	cpf=cpf.replace(/(\d{3})(\d)/,"$1.$2")
	cpf=cpf.replace(/(\d{3})(\d{1,2})$/,"$1-$2")
	return cpf
}
function mCEP(cep){
	cep=cep.replace(/\D/g,"")
	cep=cep.replace(/^(\d{5})(\d)/,"$1-$2")
	return cep
}
function mNum(num){
	num=num.replace(/\D/g,"")
	return num
}

function acao(valor){
	document.getElementById("acao").value = valor;
}

function goBack() {
  window.history.back();
}

</script>

<!-- start: page -->
<c:if test="${erro != null || inicio != null }">
	<div class="row">
	<div class="col-md-12">
		<div data-collapsed="0" class="panel">
			<div class="panel-heading">
				<div class="panel-title">
					<div class="panel-actions">
						<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
						<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
					</div>
					<h2 class="panel-title" id="">PESQUISAR CHAMADO </h2>
				</div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-6 form-group">
						<label>Pesquisar Chamado:</label>
						<div class="input-group">
							<span onclick="redirecionar()" class="input-group-addon">
								<i class="fa fa-search"></i>
							</span>
							<input type="text" name="pesqChamado" placeholder="Insira um chamado para pesquisar." id="pesqChamado" class="form-control">
						</div>
					</div>
					<div class="col-md-12 form-group">
						<br><br><br><br><br>
						<div class="input-group">
							<a onclick="goBack()" class="btn btn-default">Voltar</a>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>
	
</c:if>

<c:if test="${erro == null && inicio == null }">
<div class="row">
<form action="/atualizarChamado" method="post" accept-charset="utf-8">
	<div class="col-md-12">
		<div data-collapsed="0" class="panel">
			<div class="panel-heading">
				<div class="panel-title">
					<div class="panel-actions">
						<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
						<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
					</div>
					<h2 class="panel-title" id="">CHAMADO ${chamado.numeroChamado }
					<c:if test="${chamado.status.nome == 'Aberto'}"><span style="color:blue">(${chamado.status.nome })</span></c:if>
					<c:if test="${chamado.status.nome == 'Em Andamento'}"><span style="color:green">(${chamado.status.nome })</span></c:if>
					<c:if test="${chamado.status.nome == 'Encerrado'}"><span style="color:red">(${chamado.status.nome })</span></c:if>
					<c:if test="${chamado.status.nome == 'Pendente Usuário'}"><span style="color:orange">(${chamado.status.nome })</span></c:if>
					<c:if test="${chamado.status.nome == 'Pendente Suporte'}"><span style="color:orange">(${chamado.status.nome })</span></c:if>
					</h2>
				</div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-6 form-group">
						<label>Pesquisar Chamado:</label>
						<div class="input-group">
							<span onclick="redirecionar()" class="input-group-addon">
								<i class="fa fa-search"></i>
							</span>
							<input type="text" name="pesqChamado" placeholder="Insira um chamado para pesquisar." id="pesqChamado" class="form-control">
						</div>
					</div>
					
					<div class="col-md-12 form-group"></div>
					
					<div class="col-md-3 form-group">
						<label>Chamado:</label>
						<input type="text" name="numeroChamado" placeholder="Matrícula" id="matricula" class="form-control" value="${chamado.numeroChamado }" readonly required>
					</div>
					<div class="col-md-5 form-group">
						<label>Solicitante:</label>
						<input type="text" placeholder="ID Solicitante" class="form-control" value="${chamado.solicitante.nome }" disabled required>
					</div>
					<div class="col-md-4 form-group">
						<label>Perfil: </label>
						<input type="text" placeholder="Grupo" class="form-control" value="${chamado.solicitante.perfil.nome }" disabled required>
					</div>
					
					<div class="col-md-4 form-group">
						<label>Telefone:</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-phone"></i>
							</span>
							<input type="text" id="telefone" value="${chamado.telefone }" name="telefone" placeholder="Telefone" maxlength="14" minlength="13" onkeydown="javascript: fMasc( this, mTel );" class="form-control" >
						</div>
					</div>
					<div class="col-md-4 form-group">
						<label>Celular:</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-phone"></i>
							</span>
							<input type="text" id="celular" value="${chamado.celular }" name="celular" placeholder="Celular" maxlength="14" minlength="14" onkeydown="javascript: fMasc( this, mTel );" class="form-control" >
						</div>
					</div>
					<div class="col-md-4 form-group">
						<label>Email:</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-envelope"></i>
							</span>
							<input type="email" name="email" value="${chamado.email }" id="email" class="form-control" placeholder="eg.: email@email.com" />
						</div>
					</div>
					<div class="col-md-3 form-group">
						<label>Grupo:</label>
						<select id="grupo" name="grupo_codigo" class="form-control" onchange="listar()">
							<option value="0" >-- Grupo --</option>
							<c:forEach items="${grupos }" var="g">
								<option value="${g.id }" <c:if test="${g.id == chamado.destinoGrupo.id}">selected</c:if> >
									${g.nome }
								</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="col-md-9 form-group">
						<label>Assunto do Chamado:</label>
						<select id="assunto" name="assunto_codigo" class="form-control" onchange="assuntoSelecionado()">
								<option value="0" >-- Selecione o Assunto --</option>
								<c:forEach items="${assuntos }" var="a">
									<option value="${a.id }" <c:if test="${a.id == chamado.destinoAssunto.id}">selected</c:if> >
										${a.nome }
									</option>
								</c:forEach>
						</select>
					</div>
					
					
					<div class="col-md-4 form-group">
						<label>Operador que está atendendo: </label>
						<input type="text" placeholder="Ninguém atendeu." class="form-control" value="${chamado.atendente.nome }" readonly required>
					</div>
					<div class="col-md-3 form-group-bordered">
						<label>Tempo de Atendimento:</label>
						<input type="text" id="horasAtendimento" value="${chamado.destinoAssunto.horasAtendimento }hs" name="horasAtendimento" class="form-control" disabled required>
					</div>
					<div class="col-md-3 form-group-bordered">
						<label>Status do Chamado:</label>
						<select id="status" name="status_codigo" class="form-control" >
								<c:forEach items="${statusChamado }" var="s">
									<option value="${s.id }" <c:if test="${s.id == chamado.status.id}">selected</c:if> >
										${s.nome }
									</option>
								</c:forEach>
						</select>
					</div>
					<div class="col-md-2 form-group-bordered">
						<label>Vencido?:</label>
						<c:if test="${chamado.vencido}">
							<input type="text" value="Sim" class="form-control" disabled>
						</c:if>
						<c:if test="${!chamado.vencido}">
							<input type="text" value="Não" class="form-control" disabled>
						</c:if>
					</div>
					<div class="col-md-12 form-group-bordered">
						<label>Atualizar Chamado:</label>
						<textarea class="form-control" name="historicoChamado" rows="5" id="textareaDefault" placeholder="Escreva aqui no chamado."></textarea>
					</div>
					<br>
					
					<div class="col-md-12 form-group-bordered">
						<br><b>Descrição do Chamado:</b><br>
					</div>
					<div class="col-md-12 form-group-bordered">
						<div class="autocomplete-suggestions">
						    <div class="autocomplete-suggestion">
						        <div class="txt">
						            ${chamado.descricaoChamado }
						        </div>
						    </div>
						</div>
					</div>
					
					<div class="col-md-4 form-group" id="salvar">
						<br>
						<c:if test="${chamado.status.aberto == true}">
							<input type="submit" class="btn btn-primary" value="ATENDER">
						</c:if>
						<c:if test="${chamado.status.pendente == true}">
							<input type="submit" class="btn btn-success" value="ATUALIZAR E ATENDER">
						</c:if>
						<c:if test="${chamado.status.encerrado == true}">
							<input type="submit" class="btn btn-danger" value="REABRIR">
						</c:if>
					</div>
					
					<div class="col-md-12 form-group-bordered">
						<br><b>Histórico do Chamado:</b><br>
					</div>
					<div class="col-md-12 form-group-bordered">
						<div class="autocomplete-suggestions">
						    <div class="autocomplete-suggestion">
						        <div class="txt">
						            ${chamado.historicoChamado }
						        </div>
						    </div>
						</div>
					</div>
					
					
					<div class="col-md-12 form-group">
						<label>&nbsp</label>
					</div>
					
					
				</div>
			</div>
		</div>
	</div>
</form>
</div>
</c:if>






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