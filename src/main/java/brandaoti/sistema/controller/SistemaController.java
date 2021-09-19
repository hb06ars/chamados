package brandaoti.sistema.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import brandaoti.sistema.dao.AssuntoDao;
import brandaoti.sistema.dao.ChamadoDao;
import brandaoti.sistema.dao.GrupoDao;
import brandaoti.sistema.dao.PerfilDao;
import brandaoti.sistema.dao.StatusChamadoDao;
import brandaoti.sistema.dao.UsuarioDao;
import brandaoti.sistema.excel.ProcessaExcel;
import brandaoti.sistema.excel.Tabela;
import brandaoti.sistema.model.Objeto;
import brandaoti.sistema.model.Perfil;
import brandaoti.sistema.model.StatusChamado;
import brandaoti.sistema.model.Assunto;
import brandaoti.sistema.model.Chamado;
import brandaoti.sistema.model.Grupo;
import brandaoti.sistema.model.Usuario;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class SistemaController extends HttpServlet {
		
		private static final long serialVersionUID = 1L;
		@Autowired
		private UsuarioDao usuarioDao;
		@Autowired
		private PerfilDao perfilDao;
		@Autowired
		private GrupoDao grupoDao;
		@Autowired
		private AssuntoDao assuntoDao;
		@Autowired
		private StatusChamadoDao statusChamadoDao;
		@Autowired
		private ChamadoDao chamadoDao;
		
		//public static Usuario usuarioSessao;
		//public static String atualizarPagina = null;
		//public static Boolean logado = false;
		//public static String itemMenu = "home";
		//public static String paginaAtual = "Dashboard";
		//public static String iconePaginaAtual = "fa fa-home";
		
		
		public void validarVencidos() {
			//Validando os chamados vencidos
			List<Chamado> geral = chamadoDao.findAll();
			for(Chamado c : geral) {
				if(c.getPrevisaoFechamentoAnalise().isBefore(LocalDateTime.now())) {
					c.setVencido(true);
					chamadoDao.save(c);
				} else {
					c.setVencido(false);
					chamadoDao.save(c);
				}
			}
		}
		
		public String gerarChamado( Usuario usuarioSessao) {
			Random gerador = new Random();
	    	Calendar data = Calendar.getInstance();
	    	int ano = data.get(Calendar.YEAR);
	    	int mes = data.get(Calendar.MONTH);
	    	mes++;
	    	int m = mes;
	    	int dia = data.get(Calendar.DAY_OF_MONTH);
	        int hora = data.get(Calendar.HOUR_OF_DAY); 
	        int min = data.get(Calendar.MINUTE);
	        int seg = data.get(Calendar.SECOND);
	        int numero = gerador.nextInt(100);
	        String chamado = ""+ano+m+dia+hora+min+seg+numero+usuarioSessao.getId();
	        return chamado;
		}
		
		public String gerarMatricula(Usuario usuarioSessao) {
			Random gerador = new Random();
	    	Calendar data = Calendar.getInstance();
	    	int ano = data.get(Calendar.YEAR);
	    	int mes = data.get(Calendar.MONTH);
	    	mes++;
	    	int m = mes;
	    	int dia = data.get(Calendar.DAY_OF_MONTH);
	        int hora = data.get(Calendar.HOUR_OF_DAY); 
	        int min = data.get(Calendar.MINUTE);
	        int seg = data.get(Calendar.SECOND);
	        int numero = gerador.nextInt(100);
	        String matricula = ""+ano+m+dia+hora+min+seg+numero+usuarioSessao.getId();
	        return matricula;
		}
		
		@RequestMapping(value = {"/","/login"}, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET) // Pagina de Vendas
		public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "nome", required = false, defaultValue = "Henrique Brandão") String nome) throws SQLException { //Funcao e alguns valores que recebe...
			//Caso não haja registros
			String link = "/pages/deslogar";
			HttpSession session = request.getSession();
			ModelAndView modelAndView = new ModelAndView(link);
			Usuario usuarioSessao = new Usuario();
			Boolean logado = false;
			String itemMenu = "";
			
			List<Usuario> u = usuarioDao.buscarTudo();
			List<Perfil> p = perfilDao.buscarTudo();
			List<Grupo> pl = grupoDao.buscarTudo();
			List<Assunto> as = assuntoDao.buscarTudo();
			List<StatusChamado> st = statusChamadoDao.buscarTudo();
			List<Chamado> ch = chamadoDao.buscarTudo();
			if(p == null || p.size() == 0) {
				Perfil perfil = new Perfil();
				perfil.setAtivo(true);
				perfil.setCodigo("1");
				perfil.setNome("Administrador");
				perfil.setAdmin(true);
				perfil.setFuncionario(true);
				perfil.setCliente(true);
				perfilDao.save(perfil);
				
				perfil = new Perfil();
				perfil.setAtivo(true);
				perfil.setCodigo("2");
				perfil.setNome("Cliente");
				perfil.setAdmin(false);
				perfil.setFuncionario(false);
				perfil.setCliente(true);
				perfilDao.save(perfil);
				
				perfil = new Perfil();
				perfil.setAtivo(true);
				perfil.setCodigo("3");
				perfil.setNome("Funcionário");
				perfil.setAdmin(false);
				perfil.setFuncionario(true);
				perfil.setCliente(false);
				perfilDao.save(perfil);
				
				
			}
			if(pl == null || pl.size() == 0) {
				Grupo grupo = new Grupo();
				grupo.setCodigo("1");
				grupo.setNome("Suporte Técnico");
				grupo.setAtivo(true);
				grupo.setCliente(false);
				grupo.setGestor(false);
				grupo.setFuncionario(true);
				grupo.setAdministrador(false);
				grupo.setDescricao("Suporte técnico 24hs");
				grupoDao.save(grupo);
				grupo = new Grupo();
				grupo.setCodigo("2");
				grupo.setNome("Desenvolvimento");
				grupo.setAtivo(true);
				grupo.setCliente(false);
				grupo.setGestor(false);
				grupo.setFuncionario(true);
				grupo.setAdministrador(false);
				grupo.setDescricao("Equipe de Desenvolvimento");
				grupoDao.save(grupo);
				grupo = new Grupo();
				grupo.setCodigo("3");
				grupo.setNome("Admnistração");
				grupo.setAtivo(true);
				grupo.setCliente(false);
				grupo.setGestor(false);
				grupo.setFuncionario(false);
				grupo.setAdministrador(true);
				grupo.setDescricao("Funcionário");
				grupoDao.save(grupo);
				grupo = new Grupo();
				grupo.setCodigo("4");
				grupo.setNome("Governança");
				grupo.setAtivo(true);
				grupo.setCliente(false);
				grupo.setGestor(true);
				grupo.setFuncionario(false);
				grupo.setAdministrador(false);
				grupo.setDescricao("Admnistrador");
				grupoDao.save(grupo);
			}
			
			if(as == null || as.size() == 0) {
				Assunto a = new Assunto();
				a.setNome("PDV não funciona");
				a.setDescricao("PDV não funciona");
				a.setGrupo(grupoDao.buscarFuncionario().get(0));
				assuntoDao.save(a);
				a = new Assunto();
				a.setNome("Reiniciar o PDV.");
				a.setDescricao("Reiniciar o PDV.");
				a.setGrupo(grupoDao.buscarFuncionario().get(0));
				assuntoDao.save(a);
			}
			
			if(st == null || st.size() == 0) {
				StatusChamado s = new StatusChamado();
				s.setCodigo("1");
				s.setNome("Aberto");
				s.setAberto(true);
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("2");
				s.setPendente(true);
				s.setNome("Em Andamento");
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("3");
				s.setPendente(true);
				s.setNome("Pendente Usuário");
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("4");
				s.setPendente(true);
				s.setNome("Pendente Suporte");
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("5");
				s.setEncerrado(true);
				s.setNome("Encerrado");
				statusChamadoDao.save(s);
			}
			
			
			if(u == null || u.size() == 0) {
				/* Descomentar.
				Usuario usu = new Usuario();
				usu.setAtivo(true);
				usu.setMatricula("adm");
				usu.setSenha("adm");
				usu.setNome("Admnistrador");
				usu.setPerfil(perfilDao.buscarAdm().get(0));
				usuarioDao.save(usu);
				*/
				
				// Excluir ----------------------------------------------------------------------------------------------------
				// Henrique
				Usuario h = new Usuario();
				h.setAtivo(true);
				h.setMatricula("adm");
				h.setCpf("22233344455");
				h.setEmail("teste@teste.com");
				h.setSenha("adm");
				h.setNome("Henrique");
				h.setTelefone("(11)98931-6271");
				h.setCelular("(11)98931-6271");
				h.setEndereco("Teste...");
				h.setCep("00000-000");
				h.setBairro("Jd da Alegria");
				h.setDataNascimento(LocalDate.now());
				h.setBairro("São Paulo");
				h.setEstado("SP");
				h.setGrupo(grupoDao.buscarAdministrador().get(0));
				h.setPerfil(perfilDao.buscarAdm().get(0));
				usuarioDao.save(h);
				
				Usuario d = new Usuario();
				d.setAtivo(true);
				d.setMatricula("123");
				d.setCpf("11122233344");
				d.setEmail("teste@teste.com");
				d.setSenha("123");
				d.setNome("Douglas");
				d.setTelefone("(11)99999-9999");
				d.setCelular("(11)99999-9999");
				d.setEndereco("Teste...");
				d.setCep("00000-000");
				d.setBairro("Jd da Alegria");
				d.setCidade("São Paulo");
				d.setEstado("SP");
				d.setDataNascimento(LocalDate.now());
				d.setPerfil(perfilDao.buscarFuncionario().get(0));
				h.setGrupo(grupoDao.buscarFuncionario().get(0));
				d.setPathImagem("https://instagram.fcgh11-1.fna.fbcdn.net/v/t51.2885-19/s150x150/121259006_145932033910851_5986443377023843247_n.jpg?_nc_ht=instagram.fcgh11-1.fna.fbcdn.net&_nc_ohc=K7QuPMx_HTsAX9F8mer&tp=1&oh=4f66c284e537eb8c6a37a16ecfa2d339&oe=605329B4");
				usuarioDao.save(d);
				
				// Rafael
				Usuario r = new Usuario();
				r.setAtivo(true);
				r.setMatricula("456");
				r.setCpf("11122233355");
				r.setEmail("teste@teste.com");
				r.setSenha("456");
				r.setNome("Rafael");
				r.setTelefone("(11)99999-9999");
				r.setCelular("(11)99999-9999");
				r.setEndereco("Teste...");
				r.setCep("00000-000");
				r.setBairro("Jd da Alegria");
				r.setCidade("São Paulo");
				d.setDataNascimento(LocalDate.now());
				r.setEstado("SP");
				r.setPathImagem("https://instagram.fcgh11-1.fna.fbcdn.net/v/t51.2885-19/s150x150/147640101_432656427934162_7502532548051698688_n.jpg?_nc_ht=instagram.fcgh11-1.fna.fbcdn.net&_nc_ohc=1AwA0-HsIfEAX_ePNx8&tp=1&oh=c494c72522c18470b00e66fa92c9e1b7&oe=6052EE90");
				r.setPerfil(perfilDao.buscarFuncionario().get(0));
				h.setGrupo(grupoDao.buscarFuncionario().get(0));
				usuarioDao.save(r);
				
				if(ch.size() == 0) {
					Chamado c = new Chamado();
					c.setStatus(statusChamadoDao.buscarAberto().get(0));
					c.setNumeroChamado("2021430172620581");
					c.setDescricaoChamado("Chamado de Teste");
					c.setDestinoGrupo(assuntoDao.findAll().get(0).getGrupo());
					c.setDestinoAssunto(assuntoDao.findAll().get(0));
					c.setEmail(usuarioDao.findAll().get(0).getEmail());
					c.setSolicitante(usuarioDao.findAll().get(0));
					c.setTelefone(usuarioDao.findAll().get(0).getTelefone());
					c.setCelular(usuarioDao.findAll().get(0).getCelular());
					c.setStatus(statusChamadoDao.buscarAberto().get(0));
					c.setPrevisaoFechamento(LocalDateTime.now());
					chamadoDao.save(c);
				}
				
				validarVencidos();
				
				
				//al.setPerfil(perfilDao.buscarSomenteAluno().get(0));
				//usuarioDao.save(al);
				
				// Excluir ----------------------------------------------------------------------------------------------------

			}
			
			
			logado = false;
			link = "index";
			itemMenu = link;
			modelAndView = new ModelAndView(link); //JSP que irÃ¡ acessar
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/deslogar", method = {RequestMethod.POST, RequestMethod.GET}) // Link que irÃ¡ acessar...
		public void deslogar(HttpServletRequest request, HttpServletResponse response) throws IOException { //Funcao e alguns valores que recebe...
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("/");
		}
		
		
		@RequestMapping(value = "/deletando", method = {RequestMethod.GET, RequestMethod.POST}) // Pagina de Alteração de Perfil
		public ModelAndView deletando(HttpServletRequest request, HttpServletResponse response, String tabela,Integer id) { //Função e alguns valores que recebe...
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "Clientes";
			String iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				link = "pages/clientes";
				//Caso esteja logado.
				if(tabela.equals("usuario")) {
					modelAndView = new ModelAndView(link);
					paginaAtual = "Clientes";
					Usuario objeto = usuarioDao.findById(id).get();
					objeto.setAtivo(false);
					usuarioDao.save(objeto);
					List<Usuario> usuarios = usuarioDao.buscarTudo();
					modelAndView.addObject("usuarios", usuarios);
					List<Grupo> grupos = grupoDao.buscarTudo();
					modelAndView.addObject("grupos", grupos);
					atualizarPagina = "/clientes";
				}
				if(tabela.equals("funcionario")) {
					modelAndView = new ModelAndView(link);
					paginaAtual = "Funcionários";
					Usuario objeto = usuarioDao.findById(id).get();
					objeto.setAtivo(false);
					usuarioDao.save(objeto);
					List<Usuario> usuarios = usuarioDao.buscarFuncionarios();
					modelAndView.addObject("usuarios", usuarios);
					List<Grupo> grupos = grupoDao.buscarTudo();
					modelAndView.addObject("grupos", grupos);
					atualizarPagina = "/funcionarios";
				}
				if(tabela.equals("grupos")) {
					link = "pages/grupos";
					modelAndView = new ModelAndView(link);
					paginaAtual = "Cadastrar novo Grupo";
					Grupo objeto = grupoDao.findById(id).get();
					objeto.setAtivo(false);
					grupoDao.save(objeto);
					List<Grupo> pl = grupoDao.buscarTudo();
					modelAndView.addObject("grupos", pl);
					atualizarPagina = "/grupos";
				}
				if(tabela.equals("assuntos")) {
					link = "pages/assuntos";
					modelAndView = new ModelAndView(link);
					paginaAtual = "Cadastrar novo Assunto";
					Assunto objeto = assuntoDao.findById(id).get();
					objeto.setAtivo(false);
					assuntoDao.save(objeto);
					List<Assunto> pl = assuntoDao.buscarTudo();
					modelAndView.addObject("assunto", pl);
					atualizarPagina = "/assuntos";
				}
			}
			modelAndView.addObject("atualizarPagina", atualizarPagina);
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			return modelAndView; 
		}
		
		
		
		/* SALVAR EXCEL */
		@RequestMapping(value = "/upload/excel", method = {RequestMethod.POST, RequestMethod.GET}) // Pagina de Alteração de Perfil
		public ModelAndView uploadExcel(HttpServletRequest request, HttpServletResponse response, Model model, String tabelaUsada, @ModelAttribute MultipartFile file) throws Exception, IOException { //Função e alguns valores que recebe...
			System.out.println("file: "+file);
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "Clientes";
			String iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Home";
				iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
				link = "pages/home";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				/* USAR DEPOIS
				ProcessaExcel processaExcel = new ProcessaExcel();
				List<Tabela> tabelas = processaExcel.uploadExcel(file);
		    	String conteudo="";
		   		Integer finalLinha = 0;
		   		Aula a = new Aula();
		   		int coluna = 0;
				switch (tabelaUsada) {  
				 case "aulas" : // CASO SEJA AULAS ---------------------
					 	link = "pages/aulas");
					 	modelAndView = new ModelAndView(link);
					 	paginaAtual = "Aulas";
						iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
					 	try {
					 		aulaDao.deleteAll();
					 		String inicio="";
			   				String fim="";
				   			for(int i=0; i < tabelas.size(); i++) {
				   				coluna = tabelas.get(i).getColuna();
				   				conteudo = tabelas.get(i).getConteudo();
				   				if(coluna == 0) inicio = conteudo;
				   				if(coluna == 1) fim = conteudo;
				   				
				   				if(coluna == 2) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Segunda");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 3) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Terça");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 4) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Quarta");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 5) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Quinta");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 6) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Sexta");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 7) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Sábado");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 8) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Domingo");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				
				   				if(finalLinha >= 8) {
				   					finalLinha = -1;
				   				}
				   				finalLinha++;
				   			}
				   		} catch(Exception e) {
				   			System.out.println("Erro: "+ e);
				   		}
					 	List<Aula> aulas = aulaDao.buscarTudo();
					 	modelAndView.addObject("aulas", aulas);
					 	List<Aula> h = aulaDao.buscarhorarios();
					 	List<Horario> horarios = new ArrayList<Horario>();
					 	String ultimoHorarioInicio = "";
					 	String ultimoHorarioFim = "";
					 	for(Aula aul : h) {
					 		if(!((ultimoHorarioInicio.equals(aul.getInicio())) && (ultimoHorarioFim.equals(aul.getFim())))) {
					 			ultimoHorarioInicio = aul.getInicio();
					 			ultimoHorarioFim = aul.getFim();
					 			Horario hr = new Horario();
					 			hr.setInicio(aul.getInicio());
					 			hr.setFim(aul.getFim());
					 			horarios.add(hr);
					 		}
					 	}
					 	modelAndView.addObject("horarios", horarios);
					}
			*/
			}
			System.out.println("Validado");
			return modelAndView; //retorna a variavel
			
		}
		
		
		@RequestMapping(value = "/home", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView home(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "usuarioVal", defaultValue = "") String usuarioVal, @RequestParam(value = "senhaVal", defaultValue = "") String senhaVal) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "Clientes";
			String iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			link = "home";
			itemMenu = link;
			if(usuarioSessao.getId() == null) {
				Usuario u = usuarioDao.fazerLogin(usuarioVal, senhaVal);
				usuarioSessao = u;
			}
			if((usuarioSessao != null && usuarioSessao.getId() != null) || logado) {
				logado=true;
				session.setAttribute("usuarioSessao",usuarioSessao);
				session.setAttribute("logado",logado);
				if(usuarioSessao != null && usuarioSessao.getPerfil() != null && usuarioSessao.getPerfil().getAdmin()) {
					paginaAtual = "Administrador";
					iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
					link = "pages/home"; //Colocar regra se for ADM ou Aluno.
				} else {
					paginaAtual = "Consulta";
					iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
					link = "pages/home"; //Colocar regra se for ADM ou Aluno.
				}
			} else {
				logado=false;
				link = "pages/deslogar"; 
			}
			modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				if(usuarioSessao != null && usuarioSessao.getPerfil() != null && usuarioSessao.getPerfil().getAdmin()) {
					List<Chamado> todosChamados = chamadoDao.buscarTudo();
					List<Chamado> todosAbertos = chamadoDao.buscarAbertos();
					List<Chamado> todosVencidos= chamadoDao.buscarVencidos();
					List<Chamado> todosAndamentos = chamadoDao.buscarAndamentos();
					List<Chamado> todosEncerrados = chamadoDao.buscarEncerrados();
					
					modelAndView.addObject("todosChamados", todosChamados.size());
					modelAndView.addObject("todosAbertos", todosAbertos.size());
					modelAndView.addObject("todosVencidos", todosVencidos.size());
					modelAndView.addObject("todosAndamentos", todosAndamentos.size());
					modelAndView.addObject("todosEncerrados", todosEncerrados.size());
					
				}
				
			}
			return modelAndView; //retorna a variavel
		}

		
		
		
		@RequestMapping(value = "/clientes", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView clientes(HttpServletRequest request, HttpServletResponse response, Usuario cliente, String acao ) throws SQLException, ParseException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			
			if(logado) {
				paginaAtual = "Clientes";
				iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
				link = "pages/clientes";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				
				List<Grupo> grupos = grupoDao.buscarTudo();
				modelAndView.addObject("grupos", grupos);			
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//Gerando matrícula aleatória
				String matriculaPadrao = gerarMatricula(usuarioSessao);
				modelAndView.addObject("matriculaPadrao", matriculaPadrao);
				
				Boolean repetido = false;
				if(usuarioDao.buscarClientesRepetidos(cliente.getMatricula(), cliente.getCpf()).size() > 0) {
					repetido = true;
				}
				if(cliente.getMatricula() != null && (acao.equals("salvar")) && !repetido) {
					try {
						Usuario a = new Usuario();
						a = cliente;
						a.setSenha(cliente.getCpf().replace(".", "").replace("-", ""));
						a.setPerfil(perfilDao.buscarSomenteCliente().get(0));
						usuarioDao.save(a);
						usuarioDao.save(a);
					} catch(Exception e) {
					modelAndView.addObject("erro", e);
					}
				} else if (cliente.getMatricula() != null && (acao.equals("atualizar")) && repetido){
					Usuario a = usuarioDao.buscarMatricula(cliente.getMatricula());
					a.setNome(cliente.getNome());
					a.setDataNascimento(cliente.getDataNascimento());
					a.setTelefone(cliente.getTelefone());
					a.setCelular(cliente.getCelular());
					a.setEndereco(cliente.getEndereco());
					a.setEmail(cliente.getEmail());
					a.setPathImagem(cliente.getPathImagem());
					a.setCep(cliente.getCep());
					a.setBairro(cliente.getBairro());
					a.setCidade(cliente.getCidade());
					a.setEstado(cliente.getEstado());
					a.setGrupo(cliente.getGrupo());
					usuarioDao.save(a);
					
				} else if(cliente.getMatricula() != null && (acao.equals("salvar")) && repetido) {
					modelAndView.addObject("erro", "Já existe este CPF / Matrícula.");
				}
				List<Usuario> usuarios = usuarioDao.buscarClientes();
				modelAndView.addObject("usuarios", usuarios);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/funcionarios", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView funcionarios(HttpServletRequest request, HttpServletResponse response, Usuario funcionario, String acao, String perfil_codigo, String grupo_codigo) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Funcionários";
				iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
				link = "pages/funcionarios";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//Gerando matrícula aleatória
				String matriculaPadrao = gerarMatricula(usuarioSessao);
				modelAndView.addObject("matriculaPadrao", matriculaPadrao);
				
				Boolean repetido = false;
				if(usuarioDao.buscarFuncionariosRepetidos(funcionario.getMatricula(), funcionario.getCpf()).size() > 0) {
					repetido = true;
				}
				if(funcionario.getMatricula() != null && (acao.equals("salvar")) && !repetido) {
					try {
						atualizarPagina = "/funcionarios";
						Usuario a = new Usuario();
						a = funcionario;
						a.setSenha(funcionario.getCpf().replace(".", "").replace("-", ""));
						a.setPerfil(perfilDao.buscarCodigo(perfil_codigo));
						a.setGrupo(grupoDao.buscarCodigo(grupo_codigo));
						usuarioDao.save(a);
						modelAndView.addObject("atualizarPagina", atualizarPagina);
					} catch(Exception e) {
						modelAndView.addObject("erro", e);
						System.out.println("Erro: "+e);
					}
				} else if (funcionario.getMatricula() != null && (acao.equals("atualizar")) && repetido){
					Usuario a = usuarioDao.buscarMatricula(funcionario.getMatricula());
					a.setNome(funcionario.getNome());
					a.setDataNascimento(funcionario.getDataNascimento());
					a.setTelefone(funcionario.getTelefone());
					a.setCelular(funcionario.getCelular());
					a.setEndereco(funcionario.getEndereco());
					a.setEmail(funcionario.getEmail());
					a.setPathImagem(funcionario.getPathImagem());
					a.setCep(funcionario.getCep());
					a.setBairro(funcionario.getBairro());
					a.setCidade(funcionario.getCidade());
					a.setEstado(funcionario.getEstado());
					a.setGrupo(funcionario.getGrupo());
					a.setPerfil(perfilDao.buscarCodigo(perfil_codigo));
					a.setGrupo(grupoDao.buscarCodigo(grupo_codigo));
					usuarioDao.save(a);
				} else if(funcionario.getMatricula() != null && (acao.equals("salvar")) && repetido) {
					modelAndView.addObject("erro", "Já existe este CPF / Matrícula.");
				}
				List<Usuario> usuarios = usuarioDao.buscarFuncionarios();
				modelAndView.addObject("usuarios", usuarios);
				
				List<Grupo> grupos = grupoDao.buscarTudo();
				modelAndView.addObject("grupos", grupos);
				
			}
			return modelAndView; //retorna a variavel
		}
		
		
		
		@RequestMapping(value = "/aniversariantes", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView aniversariantes(HttpServletRequest request, HttpServletResponse response ) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Aniversariantes";
				iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
				link = "pages/aniversariantes";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//Caso esteja logado.
				List<Usuario> usuarios = usuarioDao.buscarAniversariantes();
				modelAndView.addObject("usuarios", usuarios);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		
		@RequestMapping(value = "/grupos", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView grupos(HttpServletRequest request, HttpServletResponse response, String acao, Grupo grupos) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Grupo";
				iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
				link = "pages/grupos";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//... Salvando dados.
				if(acao != null) {
					Grupo p = new Grupo();
					if(acao.equals("atualizar")) {
						p = grupoDao.buscarCodigo(grupos.getCodigo());
					}
					p.setAtivo(true);
					p.setCodigo(grupos.getCodigo());
					p.setDescricao(grupos.getDescricao());
					p.setNome(grupos.getNome());
					grupoDao.save(p);
				}
				atualizarPagina = "/grupos";
				List<Grupo> gruposTodos = grupoDao.buscarTudo();
				modelAndView.addObject("grupos", gruposTodos);
			}
			return modelAndView; //retorna a variavel
		}
		
		@RequestMapping(value = "/todosChamados", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView todosChamados(HttpServletRequest request, HttpServletResponse response) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Todos os Chamados";
				iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
				link = "pages/todosChamados";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//... Salvando dados.
				validarVencidos();
				atualizarPagina = "/todosChamados";
				List<Chamado> chamados = chamadoDao.buscarTudo();
				modelAndView.addObject("chamados", chamados);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/meusChamados", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView meusChamados(HttpServletRequest request, HttpServletResponse response) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Meus Chamados";
				iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
				link = "pages/meusChamados";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//... Salvando dados.
				validarVencidos();
				atualizarPagina = "/meusChamados";
				List<Chamado> chamados = chamadoDao.buscarTudo();
				modelAndView.addObject("chamados", chamados);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/assuntos", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView assuntos(HttpServletRequest request, HttpServletResponse response, String acao, Assunto assuntos, Integer grupo_codigo) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Assunto";
				iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
				link = "pages/assuntos";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//... Salvando dados.
				if(acao != null) {
					Assunto p = new Assunto();
					if(acao.equals("atualizar")) {
						p = assuntoDao.findById(assuntos.getId()).get();
					}
					p.setGrupo(grupoDao.findById(grupo_codigo).get());
					p.setAtivo(true);
					p.setDescricao(assuntos.getDescricao());
					p.setNome(assuntos.getNome());
					p.setHorasAtendimento(assuntos.getHorasAtendimento());
					assuntoDao.save(p);
				}
				atualizarPagina = "/assuntos";
				
				List<Grupo> grupos = grupoDao.buscarTudo();
				modelAndView.addObject("grupos", grupos);
				
				List<Assunto> assuntoTodos = assuntoDao.buscarTudo();
				modelAndView.addObject("assuntos", assuntoTodos);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/alterarSenha", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView alterarSenha(HttpServletRequest request, HttpServletResponse response, Integer acao, String matricula,String senha,String novaSenha,String confirmaSenha) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Alterar Senha";
				iconePaginaAtual = "fa fa-key"; //Titulo do menuzinho.
				link = "pages/alterarSenha";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				String msg = "";
				//... Salvando dados.
				if(acao != null) {
					if(acao > 0) {
						Usuario u = usuarioDao.fazerLogin(matricula, senha);
						if(u != null && (novaSenha.equals(confirmaSenha)) ) {
							u.setSenha(novaSenha);
							usuarioDao.save(u);
							msg = "Alterado com sucesso.";
							modelAndView.addObject("msgOk", msg);
						} else {
							msg = "Usuário ou senha inválidos.";
							modelAndView.addObject("msg", msg);
						}
					}
				}
			}
			return modelAndView; //retorna a variavel
		}
		
		
		
		@RequestMapping(value = "/novoChamado", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView novoChamado(HttpServletRequest request, HttpServletResponse response, Integer acao, Chamado chamado, Integer assunto_codigo, Integer grupo_codigo) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Novo Chamado";
				iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
				link = "pages/novoChamado";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				String msg = "";
				//... Salvando dados.
				if(chamado.getNumeroChamado() != null) {
					Chamado c = new Chamado();
					Grupo g = grupoDao.findById(grupo_codigo).get();
					Assunto a = assuntoDao.findById(assunto_codigo).get();
					c.setStatus(statusChamadoDao.buscarAberto().get(0));
					c.setDestinoGrupo(g);
					c.setDestinoAssunto(a);
					c.setCelular(chamado.getCelular());
					c.setTelefone(chamado.getTelefone());
					c.setDescricaoChamado(chamado.getDescricaoChamado());
					c.setEmail(chamado.getEmail());
					c.setNumeroChamado(chamado.getNumeroChamado());
					c.setSolicitante(usuarioSessao);
					c.setStatus(statusChamadoDao.buscarAberto().get(0));
					c.setPrevisaoFechamento(LocalDateTime.now().plusHours(a.getHorasAtendimento()));
					chamadoDao.save(c);
				}
				
				String chamadoPadrao = gerarChamado(usuarioSessao);
				modelAndView.addObject("chamadoPadrao", chamadoPadrao);
				
				List<Grupo> grupos = grupoDao.buscarTudo();
				modelAndView.addObject("grupos", grupos);
				
				List<Assunto> assuntos = assuntoDao.buscarTudo();
				modelAndView.addObject("assuntos", assuntos);
				
				modelAndView.addObject("usuarioSessao", usuarioSessao);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = {"/atenderChamado/{chamado}", "/atenderChamado"}, produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView verChamado(HttpServletRequest request, HttpServletResponse response, @PathVariable( required = false ) String chamado ) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Chamado";
				iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
				link = "pages/atenderChamado";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				//... Salvando dados.
				if(chamado != null) {
					try {
						Chamado c = chamadoDao.buscarChamado(chamado).get(0);
						modelAndView.addObject("chamado", c);
					} catch(Exception e) {
						modelAndView.addObject("erro", "Chamado não encontrado");
					}
				} else {
					modelAndView.addObject("inicio", "inicio");
				}
				
				List<Grupo> grupos = grupoDao.buscarTudo();
				modelAndView.addObject("grupos", grupos);
				List<Assunto> assuntos = assuntoDao.buscarTudo();
				modelAndView.addObject("assuntos", assuntos);
				List<StatusChamado> statusChamado = statusChamadoDao.buscarTudo();
				modelAndView.addObject("statusChamado", statusChamado);
				modelAndView.addObject("usuarioSessao", usuarioSessao);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		
		@RequestMapping(value = "/atualizarChamado", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView atualizarChamado(HttpServletRequest request, HttpServletResponse response, Integer acao, Chamado chamado, Integer assunto_codigo, Integer grupo_codigo, Integer status_codigo) throws SQLException {
			HttpSession session = request.getSession();
			Boolean logado = false;
			String paginaAtual = "";
			String iconePaginaAtual = ""; //Titulo do menuzinho.
			String link = "pages/deslogar";
			String itemMenu = link;
			String atualizarPagina = "";
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			Usuario usuarioSessao = new Usuario();
			if(session.getAttribute("usuarioSessao") != null) {
				usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");
			}
			if(session.getAttribute("logado") != null) {
				logado = (Boolean) session.getAttribute("logado");
			}
			if(logado) {
				paginaAtual = "Chamado";
				iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
				link = "pages/atenderChamado";
				itemMenu = link;
				modelAndView = new ModelAndView(link); //JSP que irá acessar.
				modelAndView.addObject("usuario", usuarioSessao);
				modelAndView.addObject("paginaAtual", paginaAtual); 
				modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
				
				String msg = "";
				//... Salvando dados.
				if(chamado.getNumeroChamado() != null) {
					Chamado c = chamadoDao.buscarChamado(chamado.getNumeroChamado()).get(0);
					Grupo g = grupoDao.findById(grupo_codigo).get();
					Assunto a = assuntoDao.findById(assunto_codigo).get();
					StatusChamado s = statusChamadoDao.findById(status_codigo).get();
					if(s.getEncerrado()) {
						c.setDataFechamento(LocalDateTime.now());
					}
					c.setDestinoGrupo(g);
					c.setDestinoAssunto(a);
					c.setStatus(s);
					c.setCelular(chamado.getCelular());
					c.setTelefone(chamado.getTelefone());
					c.setEmail(chamado.getEmail());
					c.setNumeroChamado(chamado.getNumeroChamado());
					
					String hist = "";
					if(chamado.getHistoricoChamado() != null) {
						hist = chamado.getHistoricoChamado();
					}
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
					String agora = LocalDateTime.now().format(formatter);
					String frase = "-------------------------------------------------<br>";
					frase = frase + "Atualizado por: "+usuarioSessao.getNome()+ "<br>";
					frase = frase + "Horário: "+agora+"<br>";
					frase = frase+"Observação: "+hist;
					frase = frase+"<br>Status: "+s.getNome()+"<br>";
					if(c.getHistoricoChamado() != null) {
						c.setHistoricoChamado(frase+c.getHistoricoChamado());
					} else {
						c.setHistoricoChamado(frase);
					}
					c.setSolicitante(usuarioSessao);
					c.setAtendente(usuarioSessao);
					chamadoDao.save(c);
				}
				
				Chamado c = chamadoDao.buscarChamado(chamado.getNumeroChamado()).get(0);
				modelAndView.addObject("chamado", c);
				List<Grupo> grupos = grupoDao.buscarTudo();
				modelAndView.addObject("grupos", grupos);
				List<Assunto> assuntos = assuntoDao.buscarTudo();
				modelAndView.addObject("assuntos", assuntos);
				List<StatusChamado> statusChamado = statusChamadoDao.buscarTudo();
				modelAndView.addObject("statusChamado", statusChamado);
				
				
				modelAndView.addObject("usuarioSessao", usuarioSessao);
			}
			return modelAndView; //retorna a variavel
		}
		
		
}
	
	
	




