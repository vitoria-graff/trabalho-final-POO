package aplicacao;

import dados.*;
import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class SalvarDados implements ActionListener {
    private ACMERobots acmeRobots;
    private Clientela clientela;
    private Robos robos;
    private Locacoes locacoes;
    private JPanel painel;
    private JTextField nomeArquivo;
    private JButton botaoConfirmar;
    private JButton botaoVoltar;
    private JTextArea AreaDados;

    public SalvarDados(ACMERobots acmeRobots){
        this.acmeRobots=acmeRobots;
        this.clientela=acmeRobots.getClientela();
        this.robos=acmeRobots.getRobos();
        this.locacoes=acmeRobots.getLocacoes();

        botaoConfirmar.addActionListener(this);
        botaoVoltar.addActionListener(this);
    }
    public void salvarTodosOsDados(String nomeArquivo) throws IOException {
        try {

            BufferedWriter escritorRobos = new BufferedWriter(new FileWriter(nomeArquivo + "-ROBOS.CSV"));
            BufferedWriter escritorClientes = new BufferedWriter(new FileWriter(nomeArquivo + "-CLIENTES.CSV"));
            BufferedWriter escritorLocacoes = new BufferedWriter(new FileWriter(nomeArquivo + "-LOCACOES.CSV"));

            for (Robo robo : acmeRobots.getRobos().getRobos()) {
                if (robo instanceof Domestico) {
                    escritorRobos.write(String.format("%d;%s;1;%d", robo.getId(), robo.getModelo(), ((Domestico) robo).getNivel()));
                } else if (robo instanceof Industrial) {
                    escritorRobos.write(String.format("%d;%s;2;%s", robo.getId(), robo.getModelo(), ((Industrial) robo).getSetor()));
                } else if (robo instanceof Agricola) {
                    escritorRobos.write(String.format("%d;%s;3;%.2f;%s", robo.getId(), robo.getModelo(), ((Agricola) robo).getArea(), ((Agricola) robo).getUso()));
                }
                escritorRobos.newLine();
            }
            for (Cliente cliente : acmeRobots.getClientela().getClientes()) {
                if (cliente instanceof Individual) {
                    escritorClientes.write(String.format("%d;%s;1;%s", cliente.getCodigo(), cliente.getNome(), ((Individual) cliente).getCpf()));
                } else if (cliente instanceof Empresarial) {
                    escritorClientes.write(String.format("%d;%s;2;%d", cliente.getCodigo(), cliente.getNome(), ((Empresarial) cliente).getAno()));
                }
                escritorClientes.newLine();
            }

            for (Locacao locacao : acmeRobots.getLocacoes().getLocacoes()) {
                escritorLocacoes.write(String.format("%d;%s;%s;%s;%d", locacao.getNumero(), locacao.getSituacao(),
                        new SimpleDateFormat("dd/MM/yyyy").format(locacao.getDataInicio()),
                        new SimpleDateFormat("dd/MM/yyyy").format(locacao.getDataFim()),
                        locacao.getCliente().getCodigo()));

                for (Robo robo : locacao.getRobos()) {
                    escritorLocacoes.write(";" + robo.getId());
                }
                escritorLocacoes.newLine();
            }

            escritorRobos.close();
            escritorClientes.close();
            escritorLocacoes.close();

            JOptionPane.showMessageDialog(null, "Dados salvos com sucesso.");
            mostrarDadosCadastrados();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar dados. Verifique o nome do arquivo e tente novamente.");
            ex.printStackTrace();
        }
    }
    public void mostrarDadosCadastrados() {
        StringBuilder dadosCadastrados = new StringBuilder();
        dadosCadastrados.append("===== Clientes Cadastrados =====\n");
        for (Cliente cliente : clientela.getClientes()) {
            dadosCadastrados.append(cliente.toString()).append( "\n");
            if (cliente instanceof Individual) {
                Individual individual = (Individual) cliente;
                dadosCadastrados.append("Tipo: Individual\n");
                dadosCadastrados.append("CPF: " + individual.getCpf() + "\n");
            } else if (cliente instanceof Empresarial) {
                Empresarial empresarial = (Empresarial) cliente;
                dadosCadastrados.append("Tipo: Empresarial\n");
                dadosCadastrados.append("Ano de fundação: " + empresarial.getAno() + "\n");
            }
        }
        dadosCadastrados.append("\n===== Robôs Cadastrados =====\n");
        for (Robo robo : acmeRobots.getRobos().getRobos()) {
            dadosCadastrados.append(robo.toString()).append("\n");
            if (robo instanceof Domestico) {
                Domestico domestico = (Domestico) robo;
                dadosCadastrados.append("Tipo: Doméstico\n");
                dadosCadastrados.append("Nível: ").append(domestico.getNivel()).append("\n");
            } else if (robo instanceof Industrial) {
                Industrial industrial = (Industrial) robo;
                dadosCadastrados.append("Tipo: Industrial\n");
                dadosCadastrados.append("Setor: ").append(industrial.getSetor()).append("\n");
            } else if (robo instanceof Agricola) {
                Agricola agricola = (Agricola) robo;
                dadosCadastrados.append("Tipo: Agrícola\n");
                dadosCadastrados.append("Área: ").append(agricola.getArea()).append("\n");
                dadosCadastrados.append("Uso: ").append(agricola.getUso()).append("\n");
            }
            dadosCadastrados.append("--------------------------\n");
        }
        dadosCadastrados.append("\n===== Locações Cadastradas =====\n");
        for (Locacao locacao : acmeRobots.getLocacoes().getLocacoesPendentes()) {
            dadosCadastrados.append(locacao.toString()).append("\n");
        }
        AreaDados.setText(dadosCadastrados.toString());
    }

    public JPanel getPanel() {
        return painel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==botaoConfirmar){
            try {
                salvarTodosOsDados(nomeArquivo.getText().trim());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource()==botaoVoltar){
            acmeRobots.setSize(800, 600);
            acmeRobots.setContentPane(acmeRobots.getPanel());
        }
    }
}
