package aplicacao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ACMERobots implements ActionListener {
    private JPanel panel1;
    private JButton button1;
    private JButton cadastrarRoboButton;
    private JButton button3;
    private JButton button4;
    private JButton cadastrarClienteButton;
    private JButton cadastrarLocacaoButton;
    private JButton button7;
    private JPanel panel;

    public ACMERobots(){
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
        cadastrarClienteButton.addActionListener(this);
        cadastrarRoboButton.addActionListener(this);
        cadastrarLocacaoButton.addActionListener(this);

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
        if (e.getSource()==cadastrarClienteButton){
            CadastrarCliente cadastrarCliente =new CadastrarCliente();
            cadastrarCliente.getPanel();
        }
        else if (e.getSource()==cadastrarRoboButton){
            CadastrarRobo cadastrarRobo = new CadastrarRobo();
            cadastrarRobo.getPanel();
        } else if (e.getSource()==cadastrarLocacaoButton){
            escolhaCliente escolhaCliente=new escolhaCliente();
            escolhaCliente.getPanel();
        }
    }
    public void mostrarTelaPrincipal() {
        JFrame principalFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        principalFrame.setVisible(true);
    }
}
