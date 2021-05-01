package brandaoti.sistema.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Chamado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //Esse número é o ID automático gerado.
	
	@Column
	private Boolean ativo = true;
	
	@Column
	private String numeroChamado;
	
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String descricaoChamado;
	
	@Column(columnDefinition = "NVARCHAR(MAX)")
	private String historicoChamado;
	
	@OneToOne
	private StatusChamado status;
	
	@OneToOne
	private Usuario solicitante;
	
	@OneToOne
	private Usuario atendente;
	
	@OneToOne
	private Grupo destinoGrupo;

	@OneToOne
	private Assunto destinoAssunto;

	@Column
	private String telefone;
	
	@Column
	private String celular;
	
	@Column
	private String email;
	
	@Column
	private LocalDateTime dataAbertura = LocalDateTime.now();
	
	@Column
	private LocalDateTime previsaoFechamento;
	
	@Column
	private LocalDateTime dataFechamento;
	
	@Column
	private Boolean vencido = false;
	
	@Column
	private Boolean massivo = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroChamado() {
		return numeroChamado;
	}

	public void setNumeroChamado(String numeroChamado) {
		this.numeroChamado = numeroChamado;
	}

	public String getDescricaoChamado() {
		return descricaoChamado;
	}

	public void setDescricaoChamado(String descricaoChamado) {
		this.descricaoChamado = descricaoChamado;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	public Grupo getDestinoGrupo() {
		return destinoGrupo;
	}

	public void setDestinoGrupo(Grupo destinoGrupo) {
		this.destinoGrupo = destinoGrupo;
	}

	public Assunto getDestinoAssunto() {
		return destinoAssunto;
	}

	public void setDestinoAssunto(Assunto destinoAssunto) {
		this.destinoAssunto = destinoAssunto;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataAbertura() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String hojeFormatado = dataAbertura.format(formatter);
			return hojeFormatado;
		}catch(Exception e) {}
		return null;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getDataFechamento() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String valor = dataFechamento.format(formatter);
			return valor;
		}catch(Exception e) {}
		return null;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Boolean getMassivo() {
		return massivo;
	}

	public void setMassivo(Boolean massivo) {
		this.massivo = massivo;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public void setStatus(StatusChamado status) {
		this.status = status;
	}

	public Usuario getAtendente() {
		return atendente;
	}

	public void setAtendente(Usuario atendente) {
		this.atendente = atendente;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getHistoricoChamado() {
		return historicoChamado;
	}

	public void setHistoricoChamado(String historicoChamado) {
		this.historicoChamado = historicoChamado;
	}

	public String getPrevisaoFechamento() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String valor = previsaoFechamento.format(formatter);
			return valor;
		}catch(Exception e) {}
		return null;
	}
	
	public LocalDateTime getPrevisaoFechamentoAnalise() {
		return previsaoFechamento;
	}

	public void setPrevisaoFechamento(LocalDateTime previsaoFechamento) {
		this.previsaoFechamento = previsaoFechamento;
	}

	public Boolean getVencido() {
		return vencido;
	}

	public void setVencido(Boolean vencido) {
		this.vencido = vencido;
	}
	
	
	
	
}
