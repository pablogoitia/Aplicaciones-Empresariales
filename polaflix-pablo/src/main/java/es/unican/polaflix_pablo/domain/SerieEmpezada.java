package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

import es.unican.polaflix_pablo.domain.CapituloVisto;
import es.unican.polaflix_pablo.domain.Serie;

public class SerieEmpezada {
    private final Serie serie;
    private final List<CapituloVisto> capitulosVistos = new ArrayList<>();

    public SerieEmpezada(Serie serie) {
        this.serie = serie;
    }

    // Getters
    public Serie getSerie() {
        return serie;
    }

    public List<CapituloVisto> getCapitulosVistos() {
        return capitulosVistos;
    }
}
