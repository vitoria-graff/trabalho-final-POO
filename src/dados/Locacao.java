package dados;

import java.util.ArrayList;
import java.util.Date;

public class Locacao {
	private int numero;
	private Status situacao;
	private Date dataInicio;
	private int dataFim;
	private ArrayList<Robo>robos;


	public Locacao(int numero, Status situacao, Date dataInicio, int dataFim) {
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

	public int getDataFim() {
		return dataFim;
	}

	public void setSituacao(Status situacao) {
		this.situacao = situacao;
	}

	public ArrayList<Robo> getRobos() {
		return robos;
	}

	public double calculaValorFinal() {
		return 0;
	}

}
