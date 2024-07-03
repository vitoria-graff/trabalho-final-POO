package aplicacao;

import dados.*;
import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class MostrarLocacoes {
    private JPanel panel;
    private JTextArea textArea1;
    private JButton mostrarDadosButton;
    private JButton voltarButton;
    private ACMERobots acmeRobots;
    private Locacoes locacoes;
    private Clientela clientela;
    private Robos robos;

    public MostrarLocacoes(ACMERobots acmeRobots) {
        this.acmeRobots = acmeRobots;
        this.locacoes = acmeRobots.getLocacoes();
        this.clientela = acmeRobots.getClientela();
        this.robos = acmeRobots.getRobos();

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeRobots.setSize(800, 600);
                acmeRobots.setContentPane(acmeRobots.getPanel());
            }
        });
        mostrarDadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarLocacoes();
            }
        });

    }

    public JPanel getPanel() {
        return panel;
    }

    private void mostrarLocacoes() {
        SwingUtilities.invokeLater(() -> {
            textArea1.setText("");
            if (locacoes.getLocacoes().isEmpty()) {
                textArea1.append("Nenhuma locação cadastrada.\n");
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                for (Locacao locacao : locacoes.getLocacoes()) {
                    Cliente cliente = locacoes.getClienteByLocacao(locacao.getNumero());
                    Robo robo = locacoes.getRoboByLocacao(locacao.getNumero());

                    textArea1.append("Número: " + locacao.getNumero() + "\n");
                    textArea1.append("Cliente: " + cliente.getNome() + "\n");

                    if (cliente instanceof Individual) {
                        Individual individual = (Individual) cliente;
                        textArea1.append("Tipo: Individual\n");
                        textArea1.append("CPF: " + individual.getCpf() + "\n");
                    } else if (cliente instanceof Empresarial) {
                        Empresarial empresarial = (Empresarial) cliente;
                        textArea1.append("Tipo: Empresarial\n");
                        textArea1.append("Ano de fundação: " + empresarial.getAno() + "\n");
                    }

                    textArea1.append("Situação: " + locacao.getSituacao() + "\n");
                    textArea1.append("Data Início: " + sdf.format(locacao.getDataInicio()) + "\n");
                    textArea1.append("Data Fim: " + locacao.getDataFim() + "\n");
                    textArea1.append("Valor: " + locacao.calculaValorFinal() + "\n");

                    if (robo != null) {
                        textArea1.append("Robô:\n");
                        textArea1.append("ID: " + robo.getId() + "\n"+"Modelo: " + robo.getModelo()+ "\n" );

                        if (robo instanceof Domestico) {
                            Domestico domestico = (Domestico) robo;
                            textArea1.append("Tipo: Doméstico\n");
                            textArea1.append("Nível: " + domestico.getNivel() + "\n");
                        } else if (robo instanceof Industrial) {
                            Industrial industrial = (Industrial) robo;
                            textArea1.append("Tipo: Industrial\n");
                            textArea1.append("Setor: " + industrial.getSetor() + "\n");
                        } else if (robo instanceof Agricola) {
                            Agricola agricola = (Agricola) robo;
                            textArea1.append("Tipo: Agrícola\n");
                            textArea1.append("Área: " + agricola.getArea() + "\n");
                            textArea1.append("Uso: " + agricola.getUso() + "\n");
                        }
                    } else {
                        textArea1.append("Nenhum robô alocado.\n");
                    }

                    textArea1.append("--------------------------\n");
                }
            }
        });
    }
}
