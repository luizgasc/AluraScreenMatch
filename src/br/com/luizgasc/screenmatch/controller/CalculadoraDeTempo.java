package br.com.luizgasc.screenmatch.controller;


import br.com.luizgasc.screenmatch.models.Titulo;

public class CalculadoraDeTempo {
    private int tempoTotal;

    public int getTempoTotal(){
        return tempoTotal;
    }

    public void inclui(Titulo t){
        tempoTotal += t.getDuracaoEmMinutos();
    }

    }



