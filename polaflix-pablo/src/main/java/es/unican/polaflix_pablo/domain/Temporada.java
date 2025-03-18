package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

public class Temporada {
    private final int numeroTemporada;
    private final Serie serie;
    private final List<Capitulo> capitulos = new ArrayList<>();

    public Temporada(int numeroTemporada, Serie serie) {
        this.numeroTemporada = numeroTemporada;
        this.serie = serie;
    }

    public void addCapitulo(Capitulo capitulo) {
        capitulos.add(capitulo.getNumeroCapitulo() - 1, capitulo);
    }

    public Capitulo getCapitulo(int numero) {
        return capitulos.get(numero - 1);
    }

    // Getters
    public int getNumeroTemporada() {
        return numeroTemporada;
    }

    public Serie getSerie() {
        return serie;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }
}
