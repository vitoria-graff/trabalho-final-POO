package dados;

public class Domestico extends Robo {

	private int nivel;

	public Domestico(int id, String modelo, double valorDiario, int nivel) {
		super(id, modelo, valorDiario);
		this.nivel=nivel;
	}

	public int getNivel() {
		return nivel;
	}

	@Override
	public double calculaLocacao(int dias) {
		double locacaoDiaria = 0.0;
		if(getModelo().equals("1")){
			locacaoDiaria = 10;
		} else if(getModelo().equals("2")){
			locacaoDiaria = 20;
		} else if(getModelo().equals("3")){
			locacaoDiaria = 50;
		}

		double valorTotal = dias * locacaoDiaria;
		return valorTotal;
	}



	@Override
	public int compareTo(Robo o) {
		return Integer.compare(this.getId(), o.getId());
	}
}
