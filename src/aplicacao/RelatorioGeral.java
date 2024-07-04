package aplicacao;

import dados.*;
import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RelatorioGeral extends JFrame implements ActionListener {
    private JPanel panel;
    private JTextArea textArea;
    private JButton voltarButton;
    private JButton mostrarDadosButton;
    private ACMERobots acmeRobots;
    private Clientela clientela;
    private Robos robos;
    private Locacoes locacoes;
    public RelatorioGeral(ACMERobots acmeRobots) {
        this.acmeRobots = acmeRobots;
        this.robos = acmeRobots.getRobos();
        this.clientela=acmeRobots.getClientela();
        this.locacoes=acmeRobots.getLocacoes();

        mostrarDadosButton.addActionListener(this);
        voltarButton.addActionListener(this);
    }

    private void exibirRelatorio() {
        boolean algumDadoCadastrado = false;

        if (!robos.getRobos().isEmpty()) {
            for (Robo robo : robos.getRobos()) {
                textArea.append(robo.toString() + "\n");
                if (robo instanceof Domestico) {
                    Domestico domestico = (Domestico) robo;
                    textArea.append("Tipo: Doméstico\n");
                    textArea.append("Nível: " + domestico.getNivel() + "\n");
                } else if (robo instanceof Industrial) {
                    Industrial industrial = (Industrial) robo;
                    textArea.append("Tipo: Industrial\n");
                    textArea.append("Setor: " + industrial.getSetor() + "\n");
                } else if (robo instanceof Agricola) {
                    Agricola agricola = (Agricola) robo;
                    textArea.append("Tipo: Agrícola\n");
                    textArea.append("Área: " + agricola.getArea() + "\n");
                    textArea.append("Uso: " + agricola.getUso() + "\n");
                }
                textArea.append("--------------------------\n");
            }
            algumDadoCadastrado = true;

        if (!clientela.getClientes().isEmpty()) {
            for (Cliente cliente : clientela.getClientes()) {
                textArea.append(cliente.toString() + "\n");
                if (cliente instanceof Individual) {
                    Individual individual = (Individual) cliente;
                    textArea.append("Tipo: Individual\n");
                    textArea.append("CPF: " + individual.getCpf() + "\n");
                } else if (cliente instanceof Empresarial) {
                    Empresarial empresarial = (Empresarial) cliente;
                    textArea.append("Tipo: Empresarial\n");
                    textArea.append("Ano de fundação: " + empresarial.getAno() + "\n");
                }
                textArea.append("--------------------------\n");
            }
            algumDadoCadastrado = true;
        }

        if (!locacoes.getLocacoesPendentes().isEmpty()) {
            for (Locacao locacao : locacoes.getLocacoesPendentes()) {
                textArea.append(locacao.toString() + "\n");
            }
            algumDadoCadastrado = true;
        }

        if (!algumDadoCadastrado) {
            textArea.append("Erro! Nenhum dado cadastrado.\n");
        }
    }}

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==mostrarDadosButton){
            exibirRelatorio();
        }
        else if (e.getSource()==voltarButton){
            acmeRobots.setSize(800, 600);
            acmeRobots.setContentPane(acmeRobots.getPanel());
        }

    }
}
