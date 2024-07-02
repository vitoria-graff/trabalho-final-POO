package dados.colecoes;

import dados.Cliente;

import java.util.ArrayList;

public class Clientela {
    private ArrayList<Cliente> clientes;

    public Clientela() {
        clientes = new ArrayList<>();
    }

    public boolean cadastrarCliente(Cliente cliente) {
        for (Cliente c : clientes) {
            if (c.getCodigo() == cliente.getCodigo()) {
                return false;
            }
        }
        clientes.add(cliente);
        return true;
    }
    public boolean existeCliente(int codigo) {
        for (Cliente c : clientes) {
            if (c.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

}
