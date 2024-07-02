package aplicacao;

import dados.Cliente;
import dados.Empresarial;
import dados.Individual;
import dados.colecoes.Clientela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class CadastrarCliente implements ActionListener {
    private JPanel panel1;
    private JTextField textCodigo;
    private JTextField textNome;
    private JRadioButton individualRadioButton;
    private JRadioButton empresarialRadioButton;
    private JButton confirmarButton;
    private JButton mostrarDadosButton;
    private JButton limparButton;
    private JButton finalizarButton;
    private JTextArea textArea1;
    private JPanel panel;
    private ButtonGroup tipo;
    private ACMERobots acmeRobots;
    private Clientela clientela;

    public CadastrarCliente( ACMERobots acmeRobots){
        super();
        this.acmeRobots= acmeRobots;
        this.clientela = acmeRobots.getClientela();

        confirmarButton.addActionListener(this);
        mostrarDadosButton.addActionListener(this);
        limparButton.addActionListener(this);
        finalizarButton.addActionListener(this);

        tipo=new ButtonGroup();
        tipo.add(individualRadioButton);
        tipo.add(empresarialRadioButton);

    }
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==confirmarButton){
            cadastarCliente();
        }
        else if (e.getSource()==limparButton){
            textCodigo.setText("");
            textNome.setText("");
            textArea1.setText("");
        }
        else if (e.getSource()==mostrarDadosButton){
            mostrarDados();
        } else if (e.getSource()==finalizarButton){
            acmeRobots.setContentPane(acmeRobots.getPanel());
            acmeRobots.setSize(800, 600);

        }
    }
    private void cadastarCliente(){
        if(textCodigo.getText().trim().isEmpty() || textNome.getText().trim().isEmpty() || !empresarialRadioButton.isSelected() && !individualRadioButton.isSelected()){
            textArea1.append("Erro! Todos os campos devem estar preenchidos.\n");
            return;
        }
        try {
            int codigo = Integer.parseInt(textCodigo.getText().trim());
            String nome = textNome.getText().trim();
            if (existeCodigo(codigo)) {
                textArea1.append("Erro! Já existe um cliente com esse código.\n");
            } else {
                if (individualRadioButton.isSelected()) {
                    String cpf = JOptionPane.showInputDialog(panel, "Digite o CPF do cliente:");
                    if (cpf != null && !cpf.isEmpty()) {
                        clientela.getClientes().add(new Individual(codigo, nome, cpf));
                        textArea1.append("Cliente cadastrado com sucesso.\n");
                    } else {
                        textArea1.append("Erro! CPF não pode estar vazio.\n");
                    }
                } else if (empresarialRadioButton.isSelected()) {
                    try {
                        int ano = Integer.parseInt(JOptionPane.showInputDialog(panel, "Digite o ano de fundação da empresa:"));
                        clientela.getClientes().add(new Empresarial(codigo, nome, ano));
                        textArea1.append("Cliente cadastrado com sucesso.\n");
                    } catch (NumberFormatException e) {
                        textArea1.append("Erro! Ano deve ser um número inteiro.\n");
                    }
                }
            }
            textCodigo.setText("");
            textNome.setText("");
        } catch (NumberFormatException e) {
            textArea1.append("Erro! Código deve ser um número inteiro.\n");
        }
    }
    private void mostrarDados() {
        if (clientela.getClientes().isEmpty()) {
            textArea1.append("Nenhum cliente cadastrado.\n");
        } else {
            Collections.sort(clientela.getClientes());
            for (Cliente cliente : clientela.getClientes()) {
                textArea1.append("Código: " + cliente.getCodigo() + "\n");
                textArea1.append("Nome: " + cliente.getNome() + "\n");

                if (cliente instanceof Individual) {
                    Individual individual = (Individual) cliente;
                    textArea1.append("Tipo: Individual\n");
                    textArea1.append("CPF: " + individual.getCpf() + "\n");
                } else if (cliente instanceof Empresarial) {
                    Empresarial empresarial = (Empresarial) cliente;
                    textArea1.append("Tipo: Empresarial\n");
                    textArea1.append("Ano de fundação: " + empresarial.getAno() + "\n");
                }
                textArea1.append("--------------------------" + "\n");
            }
        }
    }


    private boolean existeCodigo(int codigo){
        for(Cliente cliente: clientela.getClientes()){
            if (cliente.getCodigo()==codigo){
                return true;
            }
        }
        return false;
    }

}

