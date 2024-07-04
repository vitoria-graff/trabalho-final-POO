package aplicacao;

import dados.*;
import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarregarDadosIniciais {
    private JPanel painel;
    private JTextField nomeArquivo;
    private JButton botaoConfirmar;
    private JButton botaoVoltar;
    private JTextArea AreaDados;
    private ACMERobots acmeRobots;
    private Clientela clientela;
    private Robos roboslist;
    private Locacoes locacoeslist;

    public CarregarDadosIniciais(ACMERobots acmeRobots) {
        super();
        this.acmeRobots = acmeRobots;
        this.clientela=acmeRobots.getClientela();
        this.locacoeslist=acmeRobots.getLocacoes();
        this.roboslist=acmeRobots.getRobos();

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

                int numero = Integer.parseInt(dados[0].trim());
                Status situacao = Status.valueOf(dados[1].trim());
                Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(dados[2].trim());
                Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(dados[3].trim());
                int codigoCliente = Integer.parseInt(dados[4].trim());

                Locacao locacao = new Locacao(numero, situacao, dataIni, dataFim);

                // IDs dos robôs começam a partir do índice 5
                for (int i = 5; i < dados.length; i++) {
                    int idRobo = Integer.parseInt(dados[i].trim());
                    Robo robo = acmeRobots.getRobos().getRoboById(idRobo);
                    locacao.setRobo(robo);
                    if (robo != null) {
                        Cliente cliente = acmeRobots.getClientela().getClienteByCodigo(codigoCliente);

                        if (cliente != null) {
                            locacao.setCliente(cliente);
                            acmeRobots.getLocacoes().cadastrarLocacao(locacao, robo, cliente);

                        } else {
                            JOptionPane.showMessageDialog(null, "Cliente não encontrado para o código: " + codigoCliente);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Robô não encontrado para o ID: " + idRobo);
                    }
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro de leitura do arquivo: " + ex.getMessage());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao converter datas: " + ex.getMessage());
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
 }
