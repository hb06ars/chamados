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

function cancelar(){
	document.getElementById("matricula").value = ${matriculaPadrao };
	document.getElementById("nome").value = '';
	document.getElementById("cpf").value = '';
	document.getElementById("dataNascimento").value = '';
	document.getElementById("telefone").value = '';
	document.getElementById("celular").value = '';
	document.getElementById("email").value = '';
	document.getElementById("endereco").value = '';
	document.getElementById("bairro").value = '';
	document.getElementById("cidade").value = '';
	document.getElementById("estado").value = '';
	document.getElementById("cep").value = '';
	document.getElementById("pathImagem").value = '';

	document.getElementById("acao").value = '';
	document.getElementById("atualizar").style.display = "none";
	document.getElementById("salvar").style.display = "block";
	document.getElementById("cancelar").style.display = "none";
}

function editar(id){
	document.getElementById("acao").value = 'atualizar';
	document.getElementById("atualizar").style.display = "block";
	document.getElementById("salvar").style.display = "none";
	document.getElementById("cancelar").style.display = "block";
	
	var inicio = 'x';
	var fim = 'x';
	<c:forEach items="${usuarios }" var="u" varStatus="s">
		if(${u.id} == id){
			document.getElementById("matricula").value = '${u.matricula}';
			document.getElementById("nome").value = '${u.nome}';
			document.getElementById("cpf").value = '${u.cpf}';
			document.getElementById("dataNascimento").value = '${u.dataNascimento}';
			document.getElementById("telefone").value = '${u.telefone}';
			document.getElementById("celular").value = '${u.celular}';
			document.getElementById("email").value = '${u.email}';
			document.getElementById("endereco").value = '${u.endereco}';
			document.getElementById("bairro").value = '${u.bairro}';
			document.getElementById("cidade").value = '${u.cidade}';
			document.getElementById("estado").value = '${u.estado}';
			document.getElementById("cep").value = '${u.cep}';
			document.getElementById("pathImagem").value = '${u.pathImagem}';
			document.getElementById("grupo").value = '${u.grupo.id}';
		}
	</c:forEach>

		
}


</script>








<div id="tabelaContrato" style="display:none;">
<table border="1"> 
            <tr>
                <th colspan="6"><b>CONTRATO ACADEMIA</b><br></br></th>
            </tr>
            <tr>
                <td colspan="6">&nbsp</td>
            </tr>
            <tr>
                <td colspan="6"><b>Cliente</b> </td>
            </tr>
            <tr>
                <td>Matr�cula: </td><td colspan="2" id="co_matricula">-</td>
                <td>CPF: </td><td colspan="2" id="co_cpf">-</td>
            </tr>
            <tr>
                <td>Nome: </td><td colspan="2" id="co_nome">-</td>
                <td>Nasc: </td><td colspan="2" id="co_nasc">-</td>
            </tr>
            <tr>
                <td>Tel: </td><td colspan="2" id="co_tel">-</td>
                <td>Cel: </td><td colspan="2" id="co_cel">-</td>
            </tr>
            <tr>
                <td>Email: </td><td colspan="5" id="co_email">-</td>
            </tr>
            <tr>
                <td>Endere�o: </td><td colspan="2" id="co_endereco">-</td>
                <td>Bairro: </td><td colspan="2" id="co_bairro">-</td>
            </tr>
            <tr>
                <td>Cidade: </td><td colspan="2" id="co_cidade">-</td>
                <td>Estado: </td><td colspan="2" id="co_estado">-</td>
            </tr>
            <tr>
                <td>CEP: </td><td colspan="5" id="co_cep">-</td>
            </tr>
            <tr>
                <td colspan="6">&nbsp</td>
            </tr>
            <tr>
                <td colspan="6"><b>Contrato</b> </td>
            </tr>
            <tr>
                <td>Grupo: </td><td colspan="5" id="co_grupo">-</td>
            </tr>
            <tr>
                <td>Obs: </td><td colspan="5" id="co_obs">-</td>
            </tr>
            <tr>
                <td>In�cio Contrato: </td><td colspan="2" id="co_inicio">-</td>
                <td>Fim Contrato: </td><td colspan="2" id="co_fim">-</td>
            </tr>
            <tr>
                <td>Total Contrato: </td><td colspan="5" id="co_totalContrato">-</td>
            </tr>
            <tr>
                <td>Sinal: </td><td colspan="2" style="color:red" id="co_sinal">-</td>
                <td>Desconto: </td><td colspan="2" style="color:red" id="co_desconto">-</td>
            </tr>
            <tr>
                <td><b>Total a Pagar: </b></td><td colspan="5" id="co_total"><b>-</b></td>
            </tr>
            <tr>
                <td colspan="6">&nbsp</td>
            </tr>
             <tr>
                <td colspan="6"><b>Formas de Pagamento</b> </td>
            </tr>
            <tr>
                <td>Parcelas: </td><td id="co_parcelas">-</td>
                <td>Vencimento dia: </td><td id="co_vencimento">-</td>
                <td>Valor da Parcela: </td><td id="co_valorDaParcela">-</td>
            </tr>
            <tr>
                <td colspan="6">&nbsp</td>
            </tr>
             <tr>
                <td colspan="6"><b>Assinatura</b> </td>
            </tr>
            <tr>
                <td ><br><br>______________________________________ <br> <div  id="co_nomeCliente" align="center">-</div></td>
                <td colspan="4">&nbsp</td>
                 <td><br><br>______________________________________ <br> <div align="center">${usuario.nome}</div></td>
            </tr>
            <tr>
                <td colspan="6"><div id="dataHojeContrato" align="center"></div> </td>
            </tr>
            
            <tr>
        </table>
</div>



<!-- start: page -->
<c:if test="${usuario.perfil.admin == true}">
<div class="row">
<form action="/clientes" method="post" accept-charset="utf-8">
	<div class="col-md-12">
		<div data-collapsed="0" class="panel">
			<div class="panel-heading">
				<div class="panel-title">
					<div class="panel-actions">
						<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
						<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
					</div>
					<h2 class="panel-title" id="">Cadastrar novo Cliente</h2>
				</div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-3 form-group">
						<input type="number" placeholder="Matr�cula" name="matricula" id="matricula" class="form-control" value="${matriculaPadrao }"required>
					</div>
					<div class="col-md-4 form-group">
						<input type="text" placeholder="Nome" name="nome" id="nome" class="form-control" required>
					</div>
					<div class="col-md-2 form-group">
						<input type="text" id="cpf" name="cpf" maxlength="14" placeholder="CPF" minlength="14" onkeydown="javascript: fMasc( this, mCPF );" class="form-control" required>
					</div>
					<div class="col-md-3 form-group">
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-birthday-cake"></i>
							</span>
							<input type="date" name="dataNascimento" id="dataNascimento" class="form-control" required/>
						</div>
					</div>
					<div class="col-md-4 form-group">
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-phone"></i>
							</span>
							<input type="text" id="telefone" name="telefone" placeholder="Telefone" maxlength="14" minlength="13" onkeydown="javascript: fMasc( this, mTel );" class="form-control" >
						</div>
					</div>
					<div class="col-md-4 form-group">
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-phone"></i>
							</span>
							<input type="text" id="celular" name="celular" placeholder="Celular" maxlength="14" minlength="14" onkeydown="javascript: fMasc( this, mTel );" class="form-control" >
						</div>
					</div>
					<div class="col-md-4 form-group">
						<div class="input-group">
							<span class="input-group-addon">
								<i class="fa fa-envelope"></i>
							</span>
							<input type="email" name="email" id="email" class="form-control" placeholder="eg.: email@email.com" />
						</div>
					</div>
					<div class="col-md-4 form-group">
						<input type="text" placeholder="Endere�o" id="endereco" name="endereco" class="form-control">
					</div>
					<div class="col-md-3 form-group">
						<input type="text" placeholder="Bairro" id="bairro" name="bairro" class="form-control">
					</div>
					<div class="col-md-3 form-group">
						<input type="text" placeholder="Cidade" id="cidade" name="cidade" class="form-control">
					</div>
					<div class="col-md-2 form-group">
						<input type="text" placeholder="Estado" id="estado" maxlength="2" minlength="2" name="estado" class="form-control">
					</div>
					<div class="col-md-2 form-group">
						<input type="text" id="cep" name="cep" placeholder="99999-999" maxlength="9" minlength="9" onkeydown="javascript: fMasc( this, mCEP );" class="form-control" >
					</div>
					<div class="col-md-10 form-group">
						<input type="text" placeholder="Link da Foto" id="pathImagem" name="pathImagem" class="form-control">
					</div>
					
					
					
					
					<div class="col-md-2 form-group" id="salvar">
						<input type="submit" class="btn btn-primary" onclick="acao('salvar')" value="Criar">
					</div>
					<div class="col-md-2 form-group" id="atualizar" style="display:none">
						<input type="submit" class="btn btn-primary" onclick="acao('atualizar')" value="Atualizar">
					</div>
					<div class="col-md-2 form-group" id="cancelar" style="display:none">
						<input type="button" class="btn btn-danger" onclick="cancelar()" value="Voltar">
					</div>
					<div class="col-md-2 form-group" id="contrato" style="display:none">
						<input type="button" class="btn btn-success" onclick="verContrato()" value="Ver Contrato">
					</div>
					<input type="hidden" id="acao" name="acao" value="salvar">
				</div>
			</div>
		</div>
	</div>
</form>
</div>
</c:if>





<section class="panel">
							<header class="panel-heading">
								<div class="panel-actions">
									<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
									<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
								</div>
						
								<h2 class="panel-title">Registro dos Clientes</h2>
							</header>
							<div class="panel-body">
								<table class="table table-bordered table-striped mb-none" id="datatable-default" style="overflow:auto">
									<thead>
										<tr>
											<c:if test="${usuario.perfil.admin == true}">
											<th>Editar</th>
											<th>Matr�cula</th>
											<th>Nome</th>
											<th>Telefone</th>
											<th>Celular</th>
											<th>Email</th>
											<th>Endere�o</th>
											<th>Bairro</th>
											<th>Cidade</th>
											<th>Estado</th>
											<th>CPF</th>
											<th>Data de Nascimento</th>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${usuarios }" var="u">
											<tr class="gradeX">
												<c:if test="${usuario.perfil.admin == true}">
													<td>
														<i class="fa fa-trash" onclick="modalDeletar('usuario', ${u.id}) "></i> &nbsp
														<i class="fa fa-pencil" onclick="editar(${u.id }) "></i>
													</td>
												</c:if>
												<td>${u.matricula }</td>
												<td>${u.nome }</td>
												<td>${u.telefone }</td>
												<td>${u.celular }</td>
												<td>${u.email }</td>
												<td>${u.endereco }</td>
												<td>${u.bairro }</td>
												<td>${u.cidade }</td>
												<td>${u.estado }</td>
												<td>${u.cpf }</td>
												
												<c:if test="${usuario.perfil.funcionario == true || usuario.perfil.professor == true}">
													<td>
													<c:set var="nascimento" value="${fn:substring(u.dataNascimento, 8, 10)}/${fn:substring(u.dataNascimento, 5, 7)}/${fn:substring(u.dataNascimento, 0, 4)}" />
													${nascimento }
													</td>
												</c:if>
											
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