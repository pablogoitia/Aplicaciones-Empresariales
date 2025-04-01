package es.unican.polaflix_pablo.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class CapituloVisto {
    @ManyToOne
    private final SerieEmpezada serieEmpezada;

    @OneToOne
    private final Capitulo capitulo;

    /**
     * Constructor de la clase CapituloVisto.
     * 
     * @param capitulo El capitulo que ha sido visto
     * @see Capitulo
     */
    public CapituloVisto(SerieEmpezada serieEmpezada, Capitulo capitulo) {
        this.serieEmpezada = serieEmpezada;
        this.capitulo = capitulo;
    }

    // Getter
    public Capitulo getCapitulo() {
        return capitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof CapituloVisto) {
            CapituloVisto capituloVisto = (CapituloVisto) o;
            return capitulo.equals(capituloVisto.capitulo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serieEmpezada, capitulo);
    }
}
