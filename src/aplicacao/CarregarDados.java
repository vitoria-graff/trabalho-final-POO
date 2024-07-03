package aplicacao;

import dados.GerenciadorDados;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarregarDados {
    private JPanel painel;
    private JTextField nomeArquivo;
    private JButton botaoConfirmar;
    private JButton botaoVoltar;
    private JTextArea AreaDados;
    private GerenciadorDados gerenciadorDados;
    private ACMERobots acmeRobots;

    public CarregarDados(ACMERobots acmeRobots) {
        super();
        this.acmeRobots = acmeRobots;
        gerenciadorDados = new GerenciadorDados();


        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    gerenciadorDados.carregarDados(nomeArquivo.getText());
                    AreaDados.append("Dados cadastrados!");
                }catch (Exception ex){
                    System.out.println("Erro: " + ex);
                }
            }
        });
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acmeRobots.setSize(800, 600);
                acmeRobots.setContentPane(acmeRobots.getPanel());
            }
        });
    }

    public JPanel getPanel() {
        return painel;
    }
}
