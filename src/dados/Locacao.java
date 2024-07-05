package dados;

import dados.colecoes.Locacoes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Locacao {
	private int numero;
	private Status situacao;
	private Date dataInicio;
	private Date  dataFim;
	private Robo robo;
	private Cliente cliente;

	private ArrayList<Robo>robos;


	public Locacao(int numero, Status situacao, Date dataInicio, Date dataFim) {
		this.numero = numero;
		this.situacao = situacao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.robos=new ArrayList<>();


	}

	public int getNumero() {
		return numero;
	}

	public Status getSituacao() {
		return situacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setSituacao(Status situacao) {
		this.situacao = situacao;
	}

	public ArrayList<Robo> getRobos() {
		return robos;
	}

	public double calculaValorFinal() {
		long diasEmMilissegundos = 86400000L;
		long diferencaDias = getDataFim().getTime() - getDataInicio().getTime();

		long diasLong = diasEmMilissegundos / diferencaDias;

		double valorFinal = robo.calculaLocacao((int) diasLong) * cliente.calculaDesconto(getRobos().size());
		return valorFinal;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setRobo(Robo robo) {
		this.robo = robo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		sb.append("Número: ").append(numero).append("\n")
				.append("Situação: ").append(situacao).append("\n")
				.append("Data Início: ").append(dateFormat.format(dataInicio)).append("\n")
				.append("Data Fim: ").append(dateFormat.format(dataFim)).append("\n")
				.append("Cliente: ").append(cliente != null ? cliente.getCodigo() : "Nenhum cliente associado").append("\n")
				.append("Robô: ").append(robo != null ? robo.getId() : "Nenhum robô associado").append("\n");
		return sb.toString();
	}
}
