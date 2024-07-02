package dados.colecoes;

import dados.Cliente;
import dados.Locacao;

import java.util.ArrayList;

public class Locacoes {
    private ArrayList<Locacao> locacoes;

    public Locacoes() {
        locacoes = new ArrayList<>();
    }

    public boolean cadastrarLocacao(Locacao locacao) {
        for (Locacao l : locacoes) {
            if (l.getNumero() == locacao.getNumero()) {
                return false;
            }
        }
        locacoes.add(locacao);
        return true;
    }
    public boolean existeLocaco(int numero) {
        for (Locacao l : locacoes) {
            if (l.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Locacao> getLocacoes() {
        return locacoes;
    }
}
