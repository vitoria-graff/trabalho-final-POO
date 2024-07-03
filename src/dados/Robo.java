package dados;

public abstract class Robo implements Comparable<Robo> {
	private int id;
	private String modelo;
	private double valorDiario;

	public Robo(int id, String modelo, double valorDiario) {
		this.id = id;
		this.modelo = modelo;
		this.valorDiario = valorDiario;
	}

	public int getId() {
		return id;
	}

	public String getModelo() {
		return modelo;
	}

	public double getValorDiario() {
		return valorDiario;
	}

	public abstract double calculaLocacao(int dias);

	@Override
	public abstract int compareTo(Robo o);
}
