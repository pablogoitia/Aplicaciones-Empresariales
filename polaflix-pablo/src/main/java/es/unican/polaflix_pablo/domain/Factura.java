package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private final String numeroFactura;

    private static int contadorFacturas = 0;
    private final Date fechaFactura;
    private double importeTotal = 0;
    
    @ManyToOne
    private final Usuario usuario;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private final List<Cargo> cargos = new ArrayList<>();

    /**
     * Constructor de la clase Factura.
     * 
     * @param usuario El usuario al que se le genera la factura
     * @see Usuario
     */
    public Factura(Usuario usuario) {
        fechaFactura = ultimoDiaMesActual();
        numeroFactura = "POLAFLIX_" + getMes() + "_" + contadorFacturas;
        contadorFacturas++;
        this.usuario = usuario;
    }

    /**
     * Anade un nuevo cargo a la factura basado en el capitulo visualizado.
     * 
     * @param capitulo El capitulo que genera el cargo en la factura
     * @return El cargo creado y anadido a la factura
     */
    public Cargo addCargo(Capitulo capitulo) {
        Cargo cargo = new Cargo(this, capitulo.getTemporada().getSerie().getNombre(),
                capitulo.getTemporada().getNumeroTemporada() + "x" + capitulo.getNumeroCapitulo(),
                capitulo.getTemporada().getSerie().getCategoria().getImporteCapitulo());
        importeTotal += cargo.getImporte();
        cargos.add(cargo);
        return cargo;
    }

    /**
     * Obtiene el ultimo dia del mes actual.
     * 
     * @return El ultimo dia del mes actual
     */
    private Date ultimoDiaMesActual() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * Obtiene el mes de la factura.
     * El metodo utiliza la fecha de factura almacenada y extrae el mes.
     * 
     * @return el numero del mes (1-12), donde 1 es enero y 12 es diciembre
     */
    public int getMes() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFactura);
        return cal.get(Calendar.MONTH) + 1;
    }

    // Getters y Setters
    public String getNumeroFactura() {
        return numeroFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && o instanceof Factura) {
            Factura f = (Factura) o;
            return f.getNumeroFactura().equals(numeroFactura);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return numeroFactura.hashCode();
    }
}
