package dados.colecoes;

import dados.Cliente;
import dados.Locacao;
import dados.Robo;
import dados.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Locacoes {
    private ArrayList<Locacao> locacoes;
    private Map<Integer, Robo> locacaoRoboMap;
    private Map<Integer, Cliente> locacaoClienteMap;

    public Locacoes() {
        locacoes = new ArrayList<>();
        locacaoRoboMap = new HashMap<>();
        locacaoClienteMap = new HashMap<>();
    }

    public boolean cadastrarLocacao(Locacao locacao, Robo robo, Cliente cliente) {
        for (Locacao l : locacoes) {
            if (l.getNumero() == locacao.getNumero()) {
                return false;
            }
        }
        locacoes.add(locacao);
        locacaoRoboMap.put(locacao.getNumero(), robo);
        locacaoClienteMap.put(locacao.getNumero(), cliente);
        return true;
    }

    public boolean existeLocacao(int numero) {
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

    public Robo getRoboByLocacao(int numero) {
        return locacaoRoboMap.get(numero);
    }

    public Cliente getClienteByLocacao(int numero) {
        return locacaoClienteMap.get(numero);
    }

    public ArrayList<Locacao> getLocacoesPendentes() {
        ArrayList<Locacao> pendentes = new ArrayList<>();
        for (Locacao locacao : locacoes) {
            if (locacao.getSituacao() == Status.CADASTRADA) {
                pendentes.add(locacao);
            }
        }
        return pendentes;
    }

}
