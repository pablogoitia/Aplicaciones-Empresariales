package es.unican.polaflix_pablo.domain;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.service.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private final Factura factura;

    @JsonView({Views.Factura.class})
    private final Date fechaCargo = new Date();
    
    @JsonView({Views.Factura.class})
    private final String nombreSerie;

    @JsonView({Views.Factura.class})
    private final String capituloTemporada;

    @JsonView({Views.Factura.class})
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

    // Constructor vacio para JPA
    public Cargo() {
        this.factura = null;
        this.nombreSerie = null;
        this.capituloTemporada = null;
        this.importe = 0.0;
    }

    // Getters
    public Factura getFactura() {
        return factura;
    }
    
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
