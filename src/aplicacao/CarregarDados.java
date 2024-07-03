package aplicacao;

import dados.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarregarDados {
    private JPanel painel;
    private JTextField nomeArquivo;
    private JButton botaoConfirmar;
    private JButton botaoVoltar;
    private JTextArea AreaDados;
    private ACMERobots acmeRobots;

    public CarregarDados(ACMERobots acmeRobots) {
        super();
        this.acmeRobots = acmeRobots;


        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String diretorioArquivos = nomeArquivo.getText().trim();
                    carregarDadosIniciais(diretorioArquivos);
                    mostrarDadosCadastrados();
                    JOptionPane.showMessageDialog(null, "Dados cadastrados com sucesso!");
                } catch (Exception ex) {
                    AreaDados.append("Erro ao carregar dados: ");
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
    public void carregarDadosIniciais(String diretorioArquivos) throws IOException, ParseException {
        carregarClientes(diretorioArquivos + "-CLIENTES.CSV");
        carregarRobos(diretorioArquivos + "-ROBOS.CSV");
        carregarLocacoes(diretorioArquivos + "-LOCACOES.CSV");
    }

    private void carregarClientes(String arquivoClientes) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoClientes))) {
            String linha;
            reader.readLine();
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int codigo = Integer.parseInt(dados[0]);
                String nome = dados[1];
                int tipo = Integer.parseInt(dados[2]);
                if (tipo == 1) {
                    String cpf = dados[3];
                    acmeRobots.getClientela().cadastrarCliente(new Individual(codigo, nome, cpf));
                } else if (tipo == 2) {
                    int ano = Integer.parseInt(dados[3]);
                    acmeRobots.getClientela().cadastrarCliente(new Empresarial(codigo, nome, ano));
                }
            }
        }
    }
    private void carregarRobos(String arquivoRobos) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoRobos))) {
            String linha;
            reader.readLine();
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                String modelo = dados[1];
                int tipo = Integer.parseInt(dados[2]);
                double valorDiario = 0;
                switch (tipo) {
                    case 1:
                        int nivel = Integer.parseInt(dados[3]);
                        acmeRobots.getRobos().cadastrarRobo(new Domestico(id, modelo, valorDiario, nivel));
                        break;
                    case 2:
                        String setor = dados[3];
                        acmeRobots.getRobos().cadastrarRobo(new Industrial(id, modelo, valorDiario, setor));
                        break;
                    case 3:
                        double area = Double.parseDouble(dados[3]);
                        String uso = dados[4];
                        acmeRobots.getRobos().cadastrarRobo(new Agricola(id, modelo, valorDiario, area, uso));
                        break;
                    default:
                        System.out.println("Tipo de robô inválido.");
                }
            }
        }
    }
    private void carregarLocacoes(String arquivoLocacoes) throws IOException, ParseException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoLocacoes))) {
            String linha;
            reader.readLine();
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                int numero = Integer.parseInt(dados[0]);
                Status situacao = Status.valueOf(dados[1]);
                Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(dados[2]);
                Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(dados[3]);
                int codigoCliente = Integer.parseInt(dados[4]);

                Locacao locacao = new Locacao(numero, situacao, dataIni, dataFim);

                for (int i = 5; i < dados.length; i++) {
                    int idRobo = Integer.parseInt(dados[i]);
                    Robo robo = acmeRobots.getRobos().getRoboById(idRobo);
                    if (robo != null) {
                        Cliente cliente = acmeRobots.getClientela().getClienteByCodigo(codigoCliente);
                        if (cliente != null) {
                            acmeRobots.getLocacoes().cadastrarLocacao(locacao, robo, cliente);
                        }
                    }
                }
            }
        }
    }
    public void mostrarDadosCadastrados() {
        StringBuilder dadosCadastrados = new StringBuilder();
        dadosCadastrados.append("===== Clientes Cadastrados =====\n");
        for (Cliente cliente : acmeRobots.getClientela().getClientes()) {
            dadosCadastrados.append(cliente.toString()).append("\n");
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
    public void salvarTodosOsDados(String nomeArquivo) {
        try (BufferedWriter escritorEvento = new BufferedWriter(new FileWriter(nomeArquivo + "-EVENTOS.CSV"));
             BufferedWriter escritorEquipe = new BufferedWriter(new FileWriter(nomeArquivo + "-EQUIPES.CSV"));
             BufferedWriter escritorEquipamento = new BufferedWriter(new FileWriter(nomeArquivo + "-EQUIPAMENTOS.CSV"));
             BufferedWriter escritorAtendimento = new BufferedWriter(new FileWriter(nomeArquivo + "-ATENDIMENTOS.CSV"))) {

            for (Evento evento : appEvento.getEventos()) {
                escritorEvento.write(evento.toString());
                escritorEvento.newLine();
            }

            for (Equipe equipe : appEquipe.getEquipes()) {
                escritorEquipe.write(equipe.toString());
                escritorEquipe.newLine();
            }

            for (Equipamento equipamento : appEquipamento.getEquipamentos()) {
                escritorEquipamento.write(equipamento.toString());
                escritorEquipamento.newLine();
            }

            for (Atendimento atendimento : appAtendimento.getAtendimentosPendentes()) {
                escritorAtendimento.write(atendimento.toString());
                escritorAtendimento.newLine();
            }

            JOptionPane.showMessageDialog(null, "Dados salvos com sucesso.");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar dados. Verifique o nome do arquivo e tente novamente.");
            ex.printStackTrace();
        }
    }
    public JPanel getPanel() {
        return painel;
    }
}
