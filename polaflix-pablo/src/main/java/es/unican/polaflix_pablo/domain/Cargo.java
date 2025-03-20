package es.unican.polaflix_pablo.domain;

import java.util.Date;

public class Cargo {
    private final Date fechaCargo = new Date();
    private final String nombreSerie;
    private final String capituloTemporada;
    private final double importe;

    /**
     * Constructor de la clase Cargo.
     * 
     * @param nombreSerie       Nombre de la serie asociada al cargo
     * @param capituloTemporada Capitulo y temporada que genera el cargo
     * @param importe           Importe del cargo
     */
    public Cargo(String nombreSerie, String capituloTemporada, double importe) {
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

    public double getImporte() {
        return importe;
    }
}
