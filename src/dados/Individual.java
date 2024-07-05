package dados;

public class Individual extends Cliente {

	private String cpf;

	public Individual(int codigo, String nome, String cpf) {
		super(codigo, nome);
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	@Override
	public double calculaDesconto(int quantidadeRobos) {
		double desconto = 0;
		if(quantidadeRobos > 1){
			desconto = 5/100;
		}
		return desconto;
	}

    @Override
    public int compareTo(Cliente o) {
        return Integer.compare(this.getCodigo(), o.getCodigo());
    }
}
