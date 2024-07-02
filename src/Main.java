import aplicacao.ACMERobots;
import dados.Cliente;
import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

public class Main {

	public static void main(String[] args) {
		new ACMERobots(new Clientela(), new Locacoes(), new Robos());

	}

}
