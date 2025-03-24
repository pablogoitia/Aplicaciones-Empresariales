package es.unican.polaflix_pablo.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SerieEmpezada {
    private final Usuario usuario;
    private final Serie serie;
    private final Set<CapituloVisto> capitulosVistos = new HashSet<>();

    /**
     * Constructor de la clase SerieEmpezada.
     * 
     * @param serie La serie que el usuario ha comenzado a ver
     * @see Serie
     */
    public SerieEmpezada(Usuario usuario, Serie serie) {
        this.usuario = usuario;
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

    public Set<CapituloVisto> getCapitulosVistos() {
        return capitulosVistos;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof SerieEmpezada) {
            SerieEmpezada serieEmpezada = (SerieEmpezada) o;
            return usuario.equals(serieEmpezada.usuario) && serie.equals(serieEmpezada.serie);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, serie);
    }
}
