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
</script>


<!-- start: page -->
<div class="row">
<form action="/novoChamado" method="post" accept-charset="utf-8">
	<div class="col-md-12">
		<div data-collapsed="0" class="panel">
			<div class="panel-heading">
				<div class="panel-title">
					<div class="panel-actions">
						<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
						<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
					</div>
					<h2 class="panel-title" id="">NOVO CHAMADO</h2>
				</div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-3 form-group">
						<label>Chamado:</label>
						<input type="text" name="numeroChamado" placeholder="Matrícula" id="matricula" class="form-control" value="${chamadoPadrao }" readonly required>
					</div>
					<div class="col-md-5 form-group">
						<label>Solicitante:</label>
						<input type="text" placeholder="ID Solicitante" class="form-control" value="${usuarioSessao.nome }" disabled required>
					</div>
					<div class="col-md-4 form-group">
						<label>Perfil: </label>
						<input type="text" placeholder="Grupo" class="form-control" value="${usuarioSessao.perfil.nome }" disabled required>
					</div>
					
					<div class="col-md-4 form-group">
						<label>Telefone:</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-phone"></i>
							</span>
							<input type="text" id="telefone" value="${usuarioSessao.telefone }" name="telefone" placeholder="Telefone" maxlength="14" minlength="13" onkeydown="javascript: fMasc( this, mTel );" class="form-control" >
						</div>
					</div>
					<div class="col-md-4 form-group">
						<label>Celular:</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-phone"></i>
							</span>
							<input type="text" id="celular" value="${usuarioSessao.celular }" name="celular" placeholder="Celular" maxlength="14" minlength="14" onkeydown="javascript: fMasc( this, mTel );" class="form-control" >
						</div>
					</div>
					<div class="col-md-4 form-group">
						<label>Email:</label>
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-envelope"></i>
							</span>
							<input type="email" name="email" value="${usuarioSessao.email }" id="email" class="form-control" placeholder="eg.: email@email.com" />
						</div>
					</div>
					<div class="col-md-3 form-group">
						<label>Grupo: </label>
						<input type="text" placeholder="Grupo" class="form-control" value="${usuarioSessao.grupo.nome }" disabled required>
					</div>
					
					<div class="col-md-12 form-group-bordered">
						<h4>ABRIR CHAMADO PARA:</h4>
					</div>
					
					<div class="col-md-3 form-group">
						<label>Grupo:</label>
						<select id="grupo" name="grupo_codigo" class="form-control" onchange="listar()">
							<option value="0" >-- Grupo --</option>
							<c:forEach items="${grupos }" var="g">
								<option value="${g.id }" >${g.nome }</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="col-md-9 form-group">
						<label>Assunto do Chamado:</label>
						<select id="assunto" name="assunto_codigo" class="form-control" onchange="assuntoSelecionado()">
								<option value="0" >-- Selecione o Assunto --</option>
								<c:forEach items="${assuntos }" var="a">
									<option value="${a.id }" >${a.nome }</option>
								</c:forEach>
						</select>
					</div>
					
					<div class="col-md-3 form-group-bordered">
						Tempo de Atendimento:
						<input type="text" id="horasAtendimento" name="horasAtendimento" class="form-control" disabled required>
					</div>
					
					<div class="col-md-12 form-group-bordered">
						Descrição do Chamado:
					</div>
					<div class="col-md-12 form-group-bordered">
						<textarea class="form-control" name="descricaoChamado" rows="10" id="textareaDefault"></textarea>
					</div>
					
					
					<div class="col-md-12 form-group">
						<label>&nbsp</label>
					</div>
					<div class="col-md-2 form-group" id="salvar">
						<input type="submit" class="btn btn-primary" value="Criar">
					</div>
					
				</div>
			</div>
		</div>
	</div>
</form>
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