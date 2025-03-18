package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

import es.unican.polaflix_pablo.domain.Capitulo;
import es.unican.polaflix_pablo.domain.Serie;

public class Temporada {
    private final int numeroTemporada;
    private final Serie serie;
    private final List<Capitulo> capitulos = new ArrayList<>();

    public Temporada(int numeroTemporada, Serie serie) {
        this.numeroTemporada = numeroTemporada;
        this.serie = serie;
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
