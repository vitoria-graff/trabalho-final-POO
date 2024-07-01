package GUI;

import dados.Cliente;
import dados.Empresarial;
import dados.Individual;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    ArrayList<Cliente> clientes = new ArrayList<>();

    public CadastrarCliente(){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        confirmarButton.addActionListener(this);
        mostrarDadosButton.addActionListener(this);
        limparButton.addActionListener(this);
        finalizarButton.addActionListener(this);

        tipo=new ButtonGroup();
        tipo.add(individualRadioButton);
        tipo.add(empresarialRadioButton);

        JFrame frame = new JFrame();
        frame.setContentPane(getPanel());
        frame.setSize(600,400);
        frame.setTitle("ACMERescue");
        ImageIcon imageIcon = new ImageIcon("icon.png");
        frame.setLocationRelativeTo(null);
        frame.setIconImage(imageIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
            Collections.sort(clientes);
            textArea1.setText("");
            for (Cliente cliente : clientes) {
                textArea1.append("Código: " + cliente.getCodigo() + "\n");
                textArea1.append("Nome: " + cliente.getNome() + "\n");
                if (cliente instanceof Individual) {
                    Individual individual = (Individual) cliente;
                    textArea1.append("Tipo: Individual\n");
                    textArea1.append("CPF: " + individual.getCpf() + "\n");
                } else if (cliente instanceof Empresarial) {
                    Empresarial empresarial = (Empresarial) cliente;
                    textArea1.append("Tipo: Empresarial\n");
                    textArea1.append("Ano de Fundação: " + empresarial.getAno() + "\n");
                }
                textArea1.append("\n");
            }
        }
        else if (e.getSource()==finalizarButton){
            System.exit(0);
        }
    }
    private void cadastarCliente(){
        try {
            int codigo = Integer.parseInt(textCodigo.getText());
            String nome = textNome.getText();
            if (nome.isEmpty() || (!individualRadioButton.isSelected() && !empresarialRadioButton.isSelected())) {
                textArea1.append("Erro! Todos os campos devem estar preenchidos.\n");
                return;
            }
            if (existeCodigo(codigo)) {
                textArea1.append("Erro! Já existe um cliente com esse código.\n");
            } else {
                if (individualRadioButton.isSelected()) {
                    String cpf = JOptionPane.showInputDialog(panel, "Digite o CPF do cliente:");
                    if (cpf != null && !cpf.isEmpty()) {
                        clientes.add(new Individual(codigo, nome, cpf));
                        Collections.sort(clientes);
                        textArea1.append("Cliente cadastrado com sucesso.\n");
                    } else {
                        textArea1.append("Erro! CPF não pode estar vazio.\n");
                    }
                } else if (empresarialRadioButton.isSelected()) {
                    try {
                        int ano = Integer.parseInt(JOptionPane.showInputDialog(panel, "Digite o ano de fundação da empresa:"));
                        clientes.add(new Empresarial(codigo, nome, ano));
                        Collections.sort(clientes);
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

    private boolean existeCodigo(int codigo){
        for(Cliente cliente: clientes){
            if (cliente.getCodigo()==codigo){
                return true;
            }
        }
        return false;
    }
}

