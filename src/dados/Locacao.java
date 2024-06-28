package dados;

import java.util.Date;

public class Locacao {
	private int numero;
	private Status situacao;
	private Date dataInicio;
	private int dataFim;

	public Locacao(int numero, Status situacao, Date dataInicio, int dataFim) {
		this.numero = numero;
		this.situacao = situacao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
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

	public int getDataFim() {
		return dataFim;
	}

	public double calculaValorFinal() {
		return 0;
	}

}
