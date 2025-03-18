package es.unican.polaflix_pablo.domain;

import java.util.Date;

public class Cargo {
    private final Date fechaCargo = new Date();
    private final String nombreSerie;
    private final String capituloTemporada;
    private final float importe;

    public Cargo(String nombreSerie, String capituloTemporada, float importe) {
        this.nombreSerie = nombreSerie;
        this.capituloTemporada = capituloTemporada;
        this.importe = importe;
    }

    // Getters
    public Date getFechaCargo() {
        return fechaCargo;
    }

    public String getNombreSerie() {
        return nombreSerie;
    }

    public String getCapituloTemporada() {
        return capituloTemporada;
    }

    public float getImporte() {
        return importe;
    }
}
