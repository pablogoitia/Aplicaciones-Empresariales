package es.unican.polaflix_pablo.domain;

import es.unican.polaflix_pablo.domain.Capitulo;

public class CapituloVisto {
    private final Capitulo capitulo;

    public CapituloVisto(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    // Getter
    public Capitulo getCapitulo() {
        return capitulo;
    }
}
