package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

public class SerieEmpezada {
    private final Serie serie;
    private final List<CapituloVisto> capitulosVistos = new ArrayList<>();

    public SerieEmpezada(Serie serie) {
        this.serie = serie;
    }

    public boolean addCapituloVisto(Capitulo capitulo) {
        CapituloVisto capituloVisto = new CapituloVisto(capitulo);

        // Si el capitulo no esta en la lista de capitulos vistos, lo añade
        if (!capitulosVistos.contains(capituloVisto)) {
            capitulosVistos.add(new CapituloVisto(capitulo));
            return true;
        }
        return false;
    }

    // Getters
    public Serie getSerie() {
        return serie;
    }

    public List<CapituloVisto> getCapitulosVistos() {
        return capitulosVistos;
    }
}
