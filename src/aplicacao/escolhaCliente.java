package aplicacao;

import dados.Cliente;
import dados.colecoes.Clientela;

import javax.swing.*;
import java.util.ArrayList;

public class escolhaCliente {
    private JPanel panel;
    private JComboBox comboBox1;
    private JButton voltarButton;
    private JButton continuarButton;
    private Clientela clientela;
    private ACMERobots acmeRobots;

    public escolhaCliente( ACMERobots acmeRobots){
        this.acmeRobots=acmeRobots;
        this.clientela= acmeRobots.getClientela();

        atualizarComboBox();

    }
    public JPanel getPanel() {
        return panel;
    }
    private void atualizarComboBox() {
        comboBox1.removeAllItems();
        for (Cliente cliente : clientela.getClientes()) {
            comboBox1.addItem(cliente.getNome());
        }
    }
}
