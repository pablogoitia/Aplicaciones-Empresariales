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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private final String numeroFactura;

    private final Date fechaFactura;
    private double importeTotal = 0;
    
    @ManyToOne
    @JoinTable(
        name = "Usuario_Factura",
        joinColumns = @JoinColumn(name = "factura"),
        inverseJoinColumns = @JoinColumn(name = "usuario")
    )
    private Usuario usuario; // Con tabla intermedia para evitar eliminacion en cascada

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
        numeroFactura = "POLAFLIX_" + getMes() + "_" + getAnio() + "_" + usuario.getId();
        this.usuario = usuario;
    }

    // Constructor vacio para JPA
    public Factura() {
        fechaFactura = null;
        numeroFactura = null;
        this.usuario = null;
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

    /**
     * Obtiene el anio de la factura.
     * El metodo utiliza la fecha de factura almacenada y extrae el anio.
     * 
     * @return el numero del anio (YYYY) de la factura
     */
    public int getAnio() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFactura);
        return cal.get(Calendar.YEAR);
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
