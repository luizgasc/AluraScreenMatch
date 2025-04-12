package br.com.luizgasc.screenmatch.main;

import br.com.luizgasc.screenmatch.models.Filme;
import br.com.luizgasc.screenmatch.models.Serie;
import br.com.luizgasc.screenmatch.models.Titulo;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.*;

public class main {
    public static void main(String[] args) {
        Filme filmeDoPaulo = new Filme("Dogville",2003);
        filmeDoPaulo.avalia(10);
        Filme outroFilme = new Filme("Avatar",2022);
        outroFilme.avalia(6);
        Serie lost = new Serie("Lost",2000);
        lost.avalia(8);
        Filme meuFilme = new Filme("O poderoso chefão",1970);
        meuFilme.avalia(8);

        Filme f1 = filmeDoPaulo;

        List<Titulo> lista = new ArrayList<>();
        lista.add(filmeDoPaulo);
        lista.add(meuFilme);
        lista.add(outroFilme);
        lista.add(lost);

        for (Titulo item: lista){
            System.out.println(item.getNome());
            if (item instanceof Filme filme){
                System.out.println("Classificação "+filme.getClassificacao());
            }
        }
        List<String> buscaPorArtista = new ArrayList<>();
        buscaPorArtista.add("Adam Sandler");
        buscaPorArtista.add("Paulo");
        buscaPorArtista.add("Jacqueline");
        System.out.println(buscaPorArtista);

        Collections.sort(buscaPorArtista);
        System.out.println(buscaPorArtista);

        Collections.sort(lista);
        System.out.println(lista);

        lista.sort(Comparator.comparing(Titulo::getAnoDeLancamento));
        System.out.println(lista);



   ; }
}
