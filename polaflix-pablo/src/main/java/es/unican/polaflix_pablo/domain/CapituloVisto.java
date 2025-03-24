package es.unican.polaflix_pablo.domain;

import java.util.Objects;

public class CapituloVisto {
    private final Capitulo capitulo;

    /**
     * Constructor de la clase CapituloVisto.
     * 
     * @param capitulo El capitulo que ha sido visto
     * @see Capitulo
     */
    public CapituloVisto(Capitulo capitulo) {
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
        // Hashcode de 100 para evitar colisiones con el hashcode de Capitulo
        return Objects.hash(capitulo, 100);
    }
}
