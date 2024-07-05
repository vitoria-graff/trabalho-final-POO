package dados;

public class Empresarial extends Cliente {

	private int ano;

	public Empresarial(int codigo, String nome, int ano) {
		super(codigo, nome);
		this.ano=ano;
	}

	public int getAno() {
		return ano;
	}

	@Override
	public double calculaDesconto(int quantidadeRobos) {
		double desconto = 0;
		if(quantidadeRobos >= 2 && quantidadeRobos <= 9){
			desconto = 3/100;
		} if(quantidadeRobos >= 10){
			desconto = 7/100;
		}
		return desconto;
	}

	@Override
	public int compareTo(Cliente o) {
		return Integer.compare(this.getCodigo(), o.getCodigo());
	}
}
