package aplicacao;

import dados.Cliente;
import dados.Locacao;
import dados.Robo;
import dados.Status;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CadastrarLocacao implements ActionListener {
    private JPanel panel1;
    private JTextField textNumero;
    private JTextField textDataIni;
    private JButton confirmarButton;
    private JButton mostrarDadosButton;
    private JButton limparButton;
    private JButton finalizarButton;
    private JTextArea textArea1;
    private JPanel panel;
    private JTextField textDataFim;
    private JComboBox comboSituacao;
    private Robos robos;
    private Locacoes locacoes;
    private ACMERobots acmeRobots;
    private EscolhaCliente escolhaCliente;

    public CadastrarLocacao(ACMERobots acmeRobots, EscolhaCliente escolhaCliente) {
        this.acmeRobots = acmeRobots;
        this.locacoes = acmeRobots.getLocacoes();
        this.robos = acmeRobots.getRobos();
        this.escolhaCliente=escolhaCliente;

        confirmarButton.addActionListener(this);
        mostrarDadosButton.addActionListener(this);
        limparButton.addActionListener(this);
        finalizarButton.addActionListener(this);

        for (Status status : Status.values()) {
            comboSituacao.addItem(status);
        }
        comboSituacao.setEnabled(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmarButton) {
            cadastrarLocacao();
        } else if (e.getSource() == limparButton) {
            textNumero.setText("");
            comboSituacao.setSelectedIndex(0);
            textDataIni.setText("");
            textDataFim.setText("");
            textArea1.setText("");
        } else if (e.getSource() == mostrarDadosButton) {
            mostrarDados();
        } else if (e.getSource() == finalizarButton) {
            acmeRobots.setContentPane(acmeRobots.getPanel());
            acmeRobots.setSize(800, 600);
        }
    }

    private void cadastrarLocacao() {
        if (textNumero.getText().trim().isEmpty() || comboSituacao.getSelectedItem() == null || textDataIni.getText().trim().isEmpty() || textDataFim.getText().trim().isEmpty()) {
            textArea1.append("Erro! Todos os campos devem estar preenchidos.\n");
            return;
        }
        try {
            int numero = Integer.parseInt(textNumero.getText().trim());
            Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(textDataIni.getText().trim());
            int dataFim = Integer.parseInt(textDataFim.getText().trim());

            if (locacoes.existeLocacao(numero)) {
                textArea1.append("Erro! Já existe uma locação com esse número.\n");
            } else {
                Robo robo = selecionarRobo();
                Cliente cliente = escolhaCliente.getClienteSelecionado();
                if (robo != null) {
                    Status situacao = Status.CADASTRADA;
                    Locacao locacao = new Locacao(numero, situacao, dataIni, dataFim);
                    locacoes.cadastrarLocacao(locacao, robo,cliente);
                    textArea1.append("Locação cadastrada com sucesso.\n");
                } else {
                    textArea1.append("Erro! Nenhum robô selecionado.\n");
                }
            }

            textNumero.setText("");
            comboSituacao.setSelectedIndex(0);
            textDataIni.setText("");
            textDataFim.setText("");
        } catch (NumberFormatException e) {
            textArea1.append("Erro! Número deve ser um valor inteiro.\n");
        } catch (ParseException e) {
            textArea1.append("Erro! Data deve estar no formato dd/MM/yyyy.\n");
        } catch (Exception e) {
            textArea1.append("Erro inesperado: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void mostrarDados() {
        if (locacoes.getLocacoes().isEmpty()) {
            textArea1.append("Nenhuma locação cadastrada.\n");
        } else {
            ArrayList<Locacao> locacoesOrdenadas = new ArrayList<>(locacoes.getLocacoes());
            locacoesOrdenadas.sort(null);
            for (Locacao locacao : locacoesOrdenadas) {
                Robo robo = locacoes.getRoboByLocacao(locacao.getNumero());
                textArea1.append("Número: " + locacao.getNumero() + "\n");
                textArea1.append("Situação: " + locacao.getSituacao() + "\n");
                textArea1.append("Data Início: " + new SimpleDateFormat("dd/MM/yyyy").format(locacao.getDataInicio()) + "\n");
                textArea1.append("Data Fim: " + locacao.getDataFim() + "\n");
                if (robo != null) {
                    textArea1.append("Robô: " + robo.getModelo() + "-" + robo.getId() +"\n");
                }
                textArea1.append("--------------------------\n");
            }
        }
    }

    private Robo selecionarRobo() {
        ArrayList<String> opcoes = new ArrayList<>();
        for (Robo robo : robos.getRobos()) {
            opcoes.add(robo.getId() + " - " + robo.getModelo());
        }
        if (opcoes.isEmpty()) {
            textArea1.append("Nenhum robô disponível para seleção.");
            return null;
        }
        String opcaoSelecionada = (String) JOptionPane.showInputDialog(panel, "Selecione um robô:", "Selecionar Robô", JOptionPane.PLAIN_MESSAGE, null, opcoes.toArray(), opcoes.get(0));
        if (opcaoSelecionada != null) {
            int id = Integer.parseInt(opcaoSelecionada.split(" - ")[0]);
            for (Robo robo : robos.getRobos()) {
                if (robo.getId() == id) {
                    return robo;
                }
            }
        }
        return null;
    }
}


