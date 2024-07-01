package aplicacao;

import dados.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class CadastrarRobo implements ActionListener {
    private JPanel panel;
    private JTextField textID;
    private JTextField textModelo;
    private JRadioButton domesticoRadioButton;
    private JRadioButton industrialRadioButton;
    private JButton confirmarButton;
    private JButton mostrarDadosButton;
    private JButton limparButton;
    private JButton finalizarButton;
    private JTextArea textArea1;
    private JRadioButton agricolaRadioButton;
    private JTextField textDiaria;
    private ButtonGroup tipo;
    ArrayList<Robo> robos = new ArrayList<>();

    public CadastrarRobo(){
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

        confirmarButton.addActionListener(this);
        mostrarDadosButton.addActionListener(this);
        limparButton.addActionListener(this);
        finalizarButton.addActionListener(this);

        tipo=new ButtonGroup();
        tipo.add(agricolaRadioButton);
        tipo.add(industrialRadioButton);
        tipo.add(domesticoRadioButton);

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
        if (e.getSource()==confirmarButton){
            cadastrarRobo();
        }
        else if (e.getSource()==limparButton){
            textID.setText("");
            textModelo.setText("");
            textArea1.setText("");
        }
        else if (e.getSource()==mostrarDadosButton){
            Collections.sort(robos);
            textArea1.setText("");
            for (Robo robo:robos) {
                textArea1.append("ID: " + robo.getId() + "\n");
                textArea1.append("Nome: " + robo.getModelo() + "\n");
                textArea1.append("Diária: " + robo.getValorDiario() + "\n");
                if (robo instanceof Domestico) {
                    Domestico domestico = (Domestico) robo;
                    textArea1.append("Tipo: Doméstico\n");
                    textArea1.append("Nivel: " + domestico.getNivel() + "\n");
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
                textArea1.append("\n");
            }
        }
        else if (e.getSource()==finalizarButton){
            System.exit(0);
        }
    }
    private void cadastrarRobo() {
        try {
            int id = Integer.parseInt(textID.getText());
            String modelo = textModelo.getText();
            double diaria = Double.parseDouble(textDiaria.getText());

            if (modelo.isEmpty() || (!agricolaRadioButton.isSelected() && !industrialRadioButton.isSelected() && !domesticoRadioButton.isSelected())) {
                throw new IllegalArgumentException("Erro! Todos os campos devem estar preenchidos.");
            }

            if (existeID(id)) {
                throw new IllegalArgumentException("Erro! Já existe um robô com esse ID.");
            }

            if (industrialRadioButton.isSelected()) {
                String setor = JOptionPane.showInputDialog(panel, "Digite o Setor:");
                if (setor == null || setor.isEmpty()) {
                    throw new IllegalArgumentException("Erro! Setor não pode estar vazio.");
                }
                robos.add(new Industrial(id, modelo, diaria, setor));
            } else if (domesticoRadioButton.isSelected()) {
                try {
                    int nivel = Integer.parseInt(JOptionPane.showInputDialog(panel, "Digite o nível:"));
                    robos.add(new Domestico(id, modelo, diaria, nivel));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Erro! Nível deve ser um número inteiro.");
                }
            } else if (agricolaRadioButton.isSelected()) {
                try {
                    double area = Double.parseDouble(JOptionPane.showInputDialog(panel, "Digite a área:"));
                    String uso = JOptionPane.showInputDialog(panel, "Digite o uso:");
                    robos.add(new Agricola(id, modelo, diaria, area, uso));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Erro! Área deve ser um número.");
                }
            }

            Collections.sort(robos);
            textArea1.append("Robô cadastrado com sucesso.\n");

            textID.setText("");
            textModelo.setText("");
            textDiaria.setText("");

        } catch (NumberFormatException e) {
            textArea1.append("Erro! ID e Diária devem ser números válidos.\n");
        } catch (IllegalArgumentException e) {
            textArea1.append(e.getMessage() + "\n");
        } catch (Exception e) {
            textArea1.append("Erro inesperado: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private boolean existeID(int id){
        for(Robo robo: robos){
            if (robo.getId()== id){
                return true;
            }
        }
        return false;
    }
}

