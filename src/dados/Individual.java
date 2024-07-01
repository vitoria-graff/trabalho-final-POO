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
		return 0;
	}

    @Override
    public int compareTo(Cliente o) {
        return Integer.compare(this.getCodigo(), o.getCodigo());
    }
}
