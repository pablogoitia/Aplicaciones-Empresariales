package es.unican.polaflix_pablo.domain;

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
        CapituloVisto that = (CapituloVisto) o;

        return getCapitulo().equals(that.capitulo);
    }
}
