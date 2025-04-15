package br.com.luizgasc.screenmatch.main;

import br.com.luizgasc.screenmatch.model.*;
import br.com.luizgasc.screenmatch.service.ConsumoApi;
import br.com.luizgasc.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

private final String ENDERECO="http://www.omdbapi.com/?t=";
private final String API_KEY="&apikey=f2ffdff";
private List<DadoSerie> dadoSeries = new ArrayList<>();

private ConsumoApi consumo = new ConsumoApi();
private ConverteDados conversor = new ConverteDados();

private Scanner leitura = new Scanner(System.in);
public void exibeMenu() {
var opcao = -1;
    while (opcao != 0) {
        var menu = """
                1 - Buscar Séries
                2 - Buscar Episódios
                3 - Listar Séries
                
                0 - Sair
                """;


        System.out.println(menu);
        opcao = leitura.nextInt();
        leitura.nextLine();

        switch (opcao) {
            case 1:
                buscarSerieWeb();
                break;
            case 2:
                buscarEpisodioPorSerie();
            case 3:
                listarSeriesBuscadas();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida");
        }
    }
}

    private void listarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = dadoSeries.stream()
                        .map(d -> new Serie(d))
                                .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }

    private DadoSerie getDadoSerie() {
            System.out.println("Digite o nome da série para busca");
            var nomeSerie = leitura.nextLine();
            var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
            DadoSerie dados = conversor.obterDados(json, DadoSerie.class);
            return dados;
        }
        private void buscarSerieWeb(){
            DadoSerie dados = getDadoSerie();
            dadoSeries.add(dados);
            System.out.println(dados);

        }
        private void buscarEpisodioPorSerie(){
            DadoSerie dadoSerie = getDadoSerie();
            List<DadoTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= dadoSerie.totalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + dadoSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadoTemporada dadoTemporada = conversor.obterDados(json, DadoTemporada.class);
                temporadas.add(dadoTemporada);
            }
            temporadas.forEach(System.out::println);
        }
    }
