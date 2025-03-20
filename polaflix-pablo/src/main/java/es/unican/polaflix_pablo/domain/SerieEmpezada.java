package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

public class SerieEmpezada {
    private final Serie serie;
    private final List<CapituloVisto> capitulosVistos = new ArrayList<>();

    /**
     * Constructor de la clase SerieEmpezada.
     * 
     * @param serie La serie que el usuario ha comenzado a ver
     * @see Serie
     */
    public SerieEmpezada(Serie serie) {
        this.serie = serie;
    }

    /**
     * Anade un capitulo a la lista de capitulos vistos.
     * 
     * @param capitulo El capitulo que se desea marcar como visto
     * @return true si el capitulo se anadio correctamente a la lista de vistos,
     *         false si el capitulo ya fue visto anteriormente
     * @see Capitulo
     */
    public boolean addCapituloVisto(Capitulo capitulo) {
        CapituloVisto capituloVisto = new CapituloVisto(capitulo);

        // Si el capitulo no esta en la lista de capitulos vistos, lo anade
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
