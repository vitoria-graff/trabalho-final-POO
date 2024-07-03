package dados.colecoes;

import dados.Cliente;
import dados.Robo;

import java.util.ArrayList;

public class Robos {
    private ArrayList<Robo> robos;
    private Cliente cliente;

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Robos() {
        robos = new ArrayList<>();
    }

    public boolean cadastrarRobo(Robo robo) {
        for (Robo r : robos) {
            if (r.getId() == robo.getId()) {
                return false;
            }
        }
        robos.add(robo);
        return true;
    }
    public boolean existeRobo(int id) {
        for (Robo r: robos) {
            if (r.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Robo> getRobos() {
        return robos;
    }
    public Robo getRoboById(int id) {
        for (Robo robo : robos) {
            if (robo.getId() == id) {
                return robo;
            }
        }
        return null;
    }

}
