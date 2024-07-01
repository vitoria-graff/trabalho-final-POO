package dados;

import java.util.ArrayList;

public abstract class Cliente implements Comparable<Cliente> {
	private int codigo;
	private String nome;
	private ArrayList<Cliente>clientes;
	public Cliente(int codigo, String nome){
		this.codigo=codigo;
		this.nome=nome;
	}


	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}
	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public abstract double calculaDesconto(int quantidadeRobos);

	@Override
	public abstract int compareTo(Cliente o);
}
