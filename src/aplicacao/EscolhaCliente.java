package aplicacao;

import dados.Cliente;
import dados.colecoes.Clientela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EscolhaCliente {
    private JPanel panel;
    private JButton voltarButton;
    private JButton continuarButton;
    private JTextArea textArea1;
    private JButton limparButton;
    private JComboBox comboBox1;
    private Clientela clientela;
    private ACMERobots acmeRobots;
    private Cliente clienteSelecionado;
    private Cliente cliente;
    private CadastrarLocacao cadastrarLocacao;

    public EscolhaCliente(ACMERobots acmeRobots){
        this.acmeRobots=acmeRobots;
        this.clientela= acmeRobots.getClientela();
        this.cadastrarLocacao= new CadastrarLocacao(acmeRobots, this);

        System.out.println("Número de clientes carregados: " + clientela.getClientes().size());
        atualizarListaClientes();

        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean clienteEncontrado = false;
                int codigo = (Integer) comboBox1.getSelectedItem();
                for (Cliente cliente : clientela.getClientes()) {
                    if (cliente.getCodigo() == codigo) {
                        clienteSelecionado = cliente;
                        clienteEncontrado = true;

                        acmeRobots.setContentPane(cadastrarLocacao.getPanel());
                        acmeRobots.setSize(600, 400);

                        break;
                    }
                }}
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeRobots.setSize(800, 600);
                acmeRobots.setContentPane(acmeRobots.getPanel());
            }
        });

        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void atualizarListaClientes() {
        SwingUtilities.invokeLater(() -> {
            textArea1.setText("");
            comboBox1.removeAllItems();
            if (clientela.getClientes().isEmpty()) {
                textArea1.append("Nenhum cliente cadastrado.\n");
            } else {
                for (Cliente cliente : clientela.getClientes()) {
                    textArea1.append(cliente.toString() + "\n");
                    comboBox1.addItem(cliente.getCodigo());
                }
            }
        });
}}

