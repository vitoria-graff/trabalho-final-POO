package aplicacao;

import dados.Locacao;
import dados.Robo;
import dados.Status;
import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ACMERobots extends JFrame implements ActionListener {
    private JPanel panel1;
    private JButton cadastrarRoboButton;
    private JButton consultarLocaçõesButton;
    private JButton cadastrarClienteButton;
    private JButton cadastrarLocacaoButton;
    private JButton processarLocaçõesButton;
    private JPanel panel;
    private JButton finalizarButton;
    private JButton relatoriogeralbutton;
    private JButton alterarSituaçãoButton;
    private JButton carregarDadosButton;
    private ImageIcon imageIcon;
    private Clientela clientela;
    private Locacoes locacoes;
    private Robos robos;
    private CadastrarCliente cadastrarCliente;
    private CadastrarRobo cadastrarRobo;
    private CadastrarLocacao cadastrarLocacao;
    private EscolhaCliente escolhaCliente;
    private RelatorioGeral relatorioGeral;
    private MostrarLocacoes mostrarLocacoes;
    private CarregarDados carregarDados;

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
        this.mostrarLocacoes=new MostrarLocacoes(this);
        this.carregarDados=new CarregarDados(this);
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
        consultarLocaçõesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarPainel(5);
            }
        });
        alterarSituaçãoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarSituacao();
            }
        });
        carregarDadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudarPainel(6);
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

            case 5:
                this.setContentPane(mostrarLocacoes.getPanel());
                this.setSize(800,400);
                break;

            case 6:
                this.setContentPane(carregarDados.getPanel());
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
        private void alterarSituacao() {
            String numeroLocacaoStr = JOptionPane.showInputDialog("Digite o número da locação:");
            if (numeroLocacaoStr == null || numeroLocacaoStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Número da locação não pode ser vazio.");
                return;
            }

            int numeroLocacao;
            try {
                numeroLocacao = Integer.parseInt(numeroLocacaoStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número da locação inválido.");
                return;
            }

            Locacao locacao = null;
            for (Locacao l : locacoes.getLocacoes()) {
                if (l.getNumero() == numeroLocacao) {
                    locacao = l;
                    break;
                }
            }

            if (locacao == null) {
                JOptionPane.showMessageDialog(null, "Nenhuma locação encontrada com o número indicado.");
                return;
            }

            if (locacao.getSituacao() == Status.FINALIZADA || locacao.getSituacao() == Status.CANCELADA) {
                JOptionPane.showMessageDialog(null, "A locação está FINALIZADA ou CANCELADA e não pode ser alterada.");
                return;
            }

            JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());
            statusComboBox.setSelectedItem(locacao.getSituacao());

            int result = JOptionPane.showConfirmDialog(null, statusComboBox, "Selecione a nova situação", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Status novaSituacao = (Status) statusComboBox.getSelectedItem();
                locacao.setSituacao(novaSituacao);
                JOptionPane.showMessageDialog(null, "Situação da locação alterada com sucesso.");
            }
    }


}

