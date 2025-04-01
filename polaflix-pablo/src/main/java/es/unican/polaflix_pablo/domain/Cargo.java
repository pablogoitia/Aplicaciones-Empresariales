package es.unican.polaflix_pablo.domain;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Cargo {
    @ManyToOne
    private final Factura factura;
    
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
    public Cargo(Factura factura, String nombreSerie, String capituloTemporada, double importe) {
        this.factura = factura;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof Cargo) {
            Cargo cargo = (Cargo) o;
            return factura.equals(cargo.factura) && nombreSerie.equals(cargo.nombreSerie)
                    && capituloTemporada.equals(cargo.capituloTemporada);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(factura, nombreSerie, capituloTemporada);
    }
}
