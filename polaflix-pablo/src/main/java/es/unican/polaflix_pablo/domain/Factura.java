package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.service.Views;

import java.util.Calendar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Factura {
    private static final double IMPORTE_SUSCRIPCION = 20.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView({ Views.Factura.class })
    private final int mes;

    @JsonView({ Views.Factura.class })
    private final int anio;

    @ManyToOne
    @JsonBackReference
    private final Usuario usuario;

    @JsonView({ Views.Factura.class })
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @JsonManagedReference
    private final List<Cargo> cargos = new ArrayList<>();

    /**
     * Constructor de la clase Factura.
     * 
     * @param usuario El usuario al que se le genera la factura
     * @see Usuario
     */
    public Factura(Usuario usuario) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaActual());
        mes = cal.get(Calendar.MONTH) + 1;
        anio = cal.get(Calendar.YEAR);
        this.usuario = usuario;
    }

    // Constructor vacio para JPA
    public Factura() {
        this.usuario = null;
        this.mes = 0;
        this.anio = 0;
    }

    // Importe total derivado
    @JsonView({ Views.Factura.class })
    public double getImporteTotal() {
        if (usuario.isSuscrito()) {
            return IMPORTE_SUSCRIPCION;
        }
        return cargos.stream().mapToDouble(Cargo::getImporte).sum();
    }

    /**
     * Anade un nuevo cargo a la factura basado en el capitulo visualizado.
     * 
     * @param capitulo El capitulo que genera el cargo en la factura
     * @return El cargo creado y anadido a la factura
     */
    public Cargo addCargo(Capitulo capitulo) {
        double importe = capitulo.getTemporada().getSerie().getCategoria().getImporteCapitulo();
        Cargo cargo = new Cargo(this, capitulo.getTemporada().getSerie().getNombre(),
                capitulo.getTemporada().getNumeroTemporada() + "x" + capitulo.getNumeroCapitulo(),
                importe);
        cargos.add(cargo);

        return cargo;
    }

    /**
     * Obtiene la fecha actual.
     * 
     * @return La fecha actual
     */
    private Date fechaActual() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    // Getters y Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    /**
     * Obtiene el mes de la factura.
     * El metodo utiliza la fecha de factura almacenada y extrae el mes.
     * 
     * @return el numero del mes (1-12), donde 1 es enero y 12 es diciembre
     */
    public int getMes() {
        return mes;
    }

    /**
     * Obtiene el anio de la factura.
     * El metodo utiliza la fecha de factura almacenada y extrae el anio.
     * 
     * @return el numero del anio (YYYY) de la factura
     */
    public int getAnio() {
        return anio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Factura factura = (Factura) o;
        return mes == factura.mes &&
                anio == factura.anio &&
                (usuario != null ? usuario.equals(factura.usuario) : factura.usuario == null);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(mes, anio, usuario);
    }
}
