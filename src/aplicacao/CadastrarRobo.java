package aplicacao;

import dados.*;
import dados.colecoes.Robos;

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
    private Robos robos;
    private ACMERobots acmeRobots;

    public CadastrarRobo( ACMERobots acmeRobots){
        super();
        this.acmeRobots=acmeRobots;
        this.robos=acmeRobots.getRobos();

        confirmarButton.addActionListener(this);
        mostrarDadosButton.addActionListener(this);
        limparButton.addActionListener(this);
        finalizarButton.addActionListener(this);

        tipo=new ButtonGroup();
        tipo.add(agricolaRadioButton);
        tipo.add(industrialRadioButton);
        tipo.add(domesticoRadioButton);
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
            if (robos.getRobos().isEmpty()) {
                textArea1.append("Nenhum cliente cadastrado.\n");
            } else {
            Collections.sort(robos.getRobos());
            textArea1.setText("");
            for (Robo robo:robos.getRobos()) {
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
                textArea1.append("--------------------------" + "\n");
            }
        }}
        else if (e.getSource()==finalizarButton){
            acmeRobots.setContentPane(acmeRobots.getPanel());
            acmeRobots.setSize(800, 600);
        }
    }

    private void cadastrarRobo(){
        if(textID.getText().trim().isEmpty() || textModelo.getText().trim().isEmpty() || textDiaria.getText().trim().isEmpty() || !agricolaRadioButton.isSelected() && !industrialRadioButton.isSelected()&& !domesticoRadioButton.isSelected()){
            textArea1.append("Erro! Todos os campos devem estar preenchidos.\n");
            return;
        }
        try {
            int id = Integer.parseInt(textID.getText().trim());
            String modelo = textModelo.getText().trim();
            double diaria = Double.parseDouble(textDiaria.getText().trim());
            if (existeID(id)) {
                textArea1.append("Erro! Já existe um robô com esse ID.\n");
            } else {
                if (industrialRadioButton.isSelected()) {
                    String setor = JOptionPane.showInputDialog(panel, "Digite o Setor:");
                    if (setor == null || setor.isEmpty()) {
                        throw new IllegalArgumentException("Erro! Setor não pode estar vazio.");
                    }
                    robos.getRobos().add(new Industrial(id, modelo, diaria, setor));
                } else if (domesticoRadioButton.isSelected()) {
                    try {
                        int nivel = Integer.parseInt(JOptionPane.showInputDialog(panel, "Digite o nível:"));
                        robos.getRobos().add(new Domestico(id, modelo, diaria, nivel));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Erro! Nível deve ser um número inteiro.");
                    }
                } else if (agricolaRadioButton.isSelected()) {
                    try {
                        double area = Double.parseDouble(JOptionPane.showInputDialog(panel, "Digite a área:"));
                        String uso = JOptionPane.showInputDialog(panel, "Digite o uso:");
                        robos.getRobos().add(new Agricola(id, modelo, diaria, area, uso));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Erro! Área deve ser um número.");
                    }
                }}
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
        for(Robo robo: robos.getRobos()){
            if (robo.getId()== id){
                return true;
            }
        }
        return false;
    }
}

