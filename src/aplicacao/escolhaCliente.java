package aplicacao;

import dados.Cliente;

import javax.swing.*;
import java.util.ArrayList;

public class escolhaCliente {
    private JPanel panel;
    private JComboBox comboBox1;
    private JButton voltarButton;
    private JButton continuarButton;
    private Cliente cliente;
    private ArrayList<Cliente>clientes;

    public escolhaCliente(){
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

        clientes=new ArrayList<>();

        JFrame frame = new JFrame();
        frame.setContentPane(getPanel());
        frame.setSize(600,400);
        frame.setTitle("ACMERescue");

        comboBox1 = new JComboBox<>();
        for (Cliente cliente: clientes){
            comboBox1.addItem(cliente.getNome());
        }

        ImageIcon imageIcon = new ImageIcon("icon.png");
        frame.setLocationRelativeTo(null);
        frame.setIconImage(imageIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public JPanel getPanel() {
        return panel;
    }
}
