package dados;

import dados.colecoes.Locacoes;

import java.util.ArrayList;

public abstract class Cliente implements Comparable<Cliente> {
	private int codigo;
	private String nome;
	private Locacao locacao;
	 Cliente(int codigo, String nome){
		this.codigo=codigo;
		this.nome=nome;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}


	public abstract double calculaDesconto(int quantidadeRobos);

	@Override
	public abstract int compareTo(Cliente o);

	@Override
	public String toString() {
		return "\nCliente:\n" + "Código: " + codigo + "\nNome: " + nome;
	}
}
