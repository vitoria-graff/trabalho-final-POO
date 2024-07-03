package aplicacao;

import dados.Cliente;
import dados.Locacao;
import dados.Robo;
import dados.Status;
import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ACMERobots extends JFrame implements ActionListener {
    private JPanel panel1;
    private JButton cadastrarRoboButton;
    private JButton button3;
    private JButton cadastrarClienteButton;
    private JButton cadastrarLocacaoButton;
    private JButton processarLocaçõesButton;
    private JPanel panel;
    private JButton finalizarButton;
    private JButton relatoriogeralbutton;
    private ImageIcon imageIcon;
    private Clientela clientela;
    private Locacoes locacoes;
    private Robos robos;
    private CadastrarCliente cadastrarCliente;
    private CadastrarRobo cadastrarRobo;
    private CadastrarLocacao cadastrarLocacao;
    private EscolhaCliente escolhaCliente;
    private RelatorioGeral relatorioGeral;

    public ACMERobots(Clientela clientela, Locacoes locacoes, Robos robos){
        super();
        imageIcon = new ImageIcon("icon.png");
        this.robos = robos;
        this.locacoes = locacoes;
        this.clientela = clientela;
        this.cadastrarRobo = new CadastrarRobo(this);
        this.cadastrarCliente = new CadastrarCliente(this);
        this.cadastrarLocacao = new CadastrarLocacao(this, escolhaCliente);
        this.escolhaCliente = new EscolhaCliente(this);
        this.relatorioGeral=new RelatorioGeral(this);
        this.setContentPane(panel);
        this.setSize(800, 600);
        this.setTitle("ACMERobots");
        this.setLocationRelativeTo(null);
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

        cadastrarRoboButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarPainel(1);
            }
        });
        cadastrarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarPainel(2);
            }
        });
        cadastrarLocacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarPainel(3);
            }
        });
        processarLocaçõesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarLocacoes();
            }
        });
        relatoriogeralbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarPainel(4);
            }
        });
        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void mudarPainel(int painel) {
        switch (painel) {
            case 1:
                this.setContentPane(cadastrarRobo.getPanel());
                this.setSize(800, 400);
                break;

            case 2:
                this.setContentPane(cadastrarCliente.getPanel());
                this.setSize(800, 400);
                break;

            case 3:
                this.setContentPane(escolhaCliente.getPanel());
                this.setSize(800, 400);
                break;

            case 4:
                this.setContentPane(relatorioGeral.getPanel());
                this.setSize(800,400);
                break;

    }}

    public JPanel getPanel() {
        return panel;
    }

    public Clientela getClientela() {
        return clientela;
    }

    public Robos getRobos() {
        return robos;
    }

    public Locacoes getLocacoes() {
        return locacoes;
    }

    public EscolhaCliente getEscolhaCliente() {
        return escolhaCliente;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private void processarLocacoes() {
        if (locacoes.getLocacoes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma locação pendente para processar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Locacao> pendentes = locacoes.getLocacoesPendentes();

        for (Locacao locacao : pendentes) {
            ArrayList<Robo> robosParaLocar = locacao.getRobos();
            boolean todosDisponiveis = true;

            for (Robo robo : robosParaLocar) {
                if (!robo.estaDisponivel()) {
                    todosDisponiveis = false;
                    break;
                }
            }

            if (todosDisponiveis) {
                for (Robo robo : robosParaLocar) {
                    robo.setDisponivel(false);
                }
                locacao.setSituacao(Status.EXECUTANDO);
            } else {
                for (Robo robo : robosParaLocar) {
                    robo.setDisponivel(true);
                }
                locacao.setSituacao(Status.CADASTRADA);
                JOptionPane.showMessageDialog(this, "A locação " + locacao.getNumero() + " não pôde ser processada. Robô(s) não disponível(eis).", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
