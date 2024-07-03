package dados;

import dados.colecoes.Clientela;
import dados.colecoes.Locacoes;
import dados.colecoes.Robos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Scanner;

public class GerenciadorDados {
    private Clientela clientela;
    private Robos robos;
    private Locacoes locacoes;

    public GerenciadorDados(){
        clientela = new Clientela();
        robos = new Robos();
        locacoes = new Locacoes();
    }

    public boolean carregarDados(String nomeArquivo) throws Exception{
        carregarRobo(nomeArquivo);
        carregarCliente(nomeArquivo);
        carregarLocacao(nomeArquivo);
        return true;
    }

    public void carregarLocacao(String nomeArquivo) throws Exception {
        Path path = Paths.get(nomeArquivo + "-LOCACOES.CSV");
        try(BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())){
            String linha = null;

            while((linha = br.readLine()) != null){
                Scanner sc = new Scanner(linha).useDelimiter(";");
                String numero = sc.next();
                String situacao = sc.next();
                String dataInicio = sc.next();
                String dataFim = sc.next();
                String codigo = sc.next();
                String idRobos = sc.next();
                Locacao locacao = new Locacao(Integer.parseInt(numero), Status.valueOf(situacao), Date.valueOf(dataInicio), Integer.parseInt(dataFim));
                locacoes.cadastrarLocacao(locacao);

            }
        } catch (IOException e){
            System.err.format("Erro de E/S: " + e);
        }
    }

    public void carregarRobo(String nomeArquivo){
        Path path = Paths.get(nomeArquivo + "-ROBOS.CSV");
        try(BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())){
            String linha = null;

            while((linha = br.readLine()) != null){
                Scanner sc = new Scanner(linha).useDelimiter(";");
                String id = sc.next();
                String modelo = sc.next();
                String tipo = sc.next();
                switch (tipo){
                    case "1" -> {
                        String nivel = sc.next();
                        Domestico roboDomestico = new Domestico(Integer.parseInt(id), modelo, Integer.parseInt(tipo), Integer.parseInt(nivel));
                        robos.cadastrarRobo(roboDomestico);
                    }
                    case "2" -> {
                        String setor = sc.next();
                        Industrial roboIndustrial = new Industrial(Integer.parseInt(id), modelo, Integer.parseInt(tipo), setor);
                        robos.cadastrarRobo(roboIndustrial);
                    }
                    case "3" -> {
                        String area = sc.next();
                        String uso = sc.next();
                        Agricola roboAgricola = new Agricola(Integer.parseInt(id), modelo, Integer.parseInt(tipo), Double.parseDouble(area), uso);
                        robos.cadastrarRobo(roboAgricola);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Erro de E/S: " + e);
        }
    }

    public void carregarCliente(String nomeArquivo){
        Path path = Paths.get(nomeArquivo + "-CLIENTES.CSV");
        try(BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())){
            String linha = null;

            while((linha = br.readLine()) != null){
                Scanner sc = new Scanner(linha).useDelimiter(";");
                String codigo = sc.next();
                String nome = sc.next();
                String tipo = sc.next();
                switch (tipo){
                    case "1" -> {
                        String cpf = sc.next();
                        Individual clientePF = new Individual(Integer.parseInt(codigo), nome, cpf);
                        clientela.cadastrarCliente(clientePF);
                    }
                    case "2" -> {
                        String ano = sc.next();
                        Empresarial clientePJ = new Empresarial(Integer.parseInt(codigo), nome, Integer.parseInt(ano));
                        clientela.cadastrarCliente(clientePJ);
                    }
                }
            }
        } catch (IOException e){
            System.err.format("Erro de E/S:" + e);
        }
    }

}
