package aplicacao;

import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ACMERobots extends JFrame implements ActionListener {
    private JPanel panel1;
    private JButton cadastrarRoboButton;
    private JButton button3;
    private JButton button4;
    private JButton cadastrarClienteButton;
    private JButton cadastrarLocacaoButton;
    private JButton button7;
    private JPanel panel;
    private JButton finalizarButton;
    private ImageIcon imageIcon;
    private Clientela clientela;
    private Locacoes locacoes;
    private Robos robos;
    private CadastrarCliente cadastrarCliente;
    private CadastrarRobo cadastrarRobo;
    private CadastrarLocacao cadastrarLocacao;
    private escolhaCliente escolhaCliente;

    public ACMERobots(Clientela clientela, Locacoes locacoes, Robos robos){
        super();
        imageIcon = new ImageIcon("icon.png");
        this.robos = robos;
        this.locacoes = locacoes;
        this.clientela = clientela;
        this.cadastrarRobo = new CadastrarRobo(this);
        this.cadastrarCliente = new CadastrarCliente(this);
        this.cadastrarLocacao = new CadastrarLocacao(this, escolhaCliente);
        this.escolhaCliente = new escolhaCliente(this);
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

    public aplicacao.escolhaCliente getEscolhaCliente() {
        return escolhaCliente;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
