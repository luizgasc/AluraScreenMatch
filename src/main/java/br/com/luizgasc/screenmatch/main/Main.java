package br.com.luizgasc.screenmatch.main;

import br.com.luizgasc.screenmatch.model.DadoEpisodio;
import br.com.luizgasc.screenmatch.model.DadoSerie;
import br.com.luizgasc.screenmatch.model.DadoTemporada;
import br.com.luizgasc.screenmatch.model.Episodio;
import br.com.luizgasc.screenmatch.service.ConsumoApi;
import br.com.luizgasc.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

private final String ENDERECO="http://www.omdbapi.com/?t=";
private final String API_KEY="&apikey=f2ffdff";
private ConsumoApi consumoApi = new ConsumoApi();
private ConverteDados conversor = new ConverteDados();

    private Scanner leitura = new Scanner(System.in);
    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
        DadoSerie dados = conversor.obterDados(json,
                DadoSerie.class);
        System.out.println(dados);

        List<DadoTemporada> temporadas = new ArrayList<>();
        for(int i=1;i<=dados.totalTemporadas();i++){
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ","+")+"&season="+i + API_KEY);
            DadoTemporada dadoTemporada =conversor.obterDados(json,DadoTemporada.class);
            temporadas.add(dadoTemporada);
        }
        temporadas.forEach(System.out::println);

        for(int i=0; i < dados.totalTemporadas();i++){
            List<DadoEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for(int j = 0; j< episodiosTemporada.size();j++){
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }

        //utilização de lambda
        temporadas.forEach(t -> t.episodios().forEach(e-> System.out.println(e.titulo())));

        List<DadoEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primeiro Filtro(NA) "+e))
//                .sorted(Comparator.comparing(DadoEpisodio::avaliacao).reversed())
//                .peek(e-> System.out.println("Ordenação "+e))
//                .limit(10)
//                .peek(e-> System.out.println("Limit "+e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Mapeamento com Uppercase "+e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(d-> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
//
//        System.out.println("Digite a sua busca por título: ");
//        var busca = leitura.nextLine();
//
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(busca.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("resultado da sua busca foi: " + episodioBuscado.get().getTitulo() + " da temporada " + episodioBuscado.get().getTemporada());
//        }else{
//            System.out.println("Episodio não encontraodo");
//        }



//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = leitura.nextInt();
//
//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: "+ e.getTemporada()+
//                                " Episódio: " + e.getTitulo() +
//                                " Data de Lançamento: "+ e.getDataLancamento().format(dtf)
//                        )
//                );

        Map<Integer,Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e-> e.getAvaliacao()>0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e-> e.getAvaliacao()>0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: "+ est.getAverage());
        System.out.println("Melhor episódio: "+ est.getMax());
        System.out.println("Pior episódio: "+ est.getMin());
        System.out.println("Quantidade: "+ est.getCount());



    }
}
