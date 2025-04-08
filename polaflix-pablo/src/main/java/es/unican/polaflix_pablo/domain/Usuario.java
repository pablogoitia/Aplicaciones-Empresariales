package es.unican.polaflix_pablo.domain;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos del usuario
    @Column(unique = true)
    private String nombreUsuario;

    private String contrasena;
    private String iban;

    // Listas de series
    @OneToMany
    private final Set<Serie> seriesPendientes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private final Set<SerieEmpezada> seriesEmpezadas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private final Set<SerieEmpezada> seriesTerminadas = new LinkedHashSet<>();

    // Informacion de facturacion
    private boolean tieneCuotaFija = false;
    
    @OneToMany(mappedBy = "usuario", cascade={CascadeType.PERSIST, CascadeType.MERGE,
        CascadeType.REFRESH, CascadeType.DETACH})
    private final List<Factura> facturas = new ArrayList<>();

    /**
     * Constructor de la clase Usuario.
     * 
     * @param nombreUsuario nombre de usuario unico para identificar al usuario
     * @param contrasena    contrasena del usuario para autenticacion
     * @param iban          numero de cuenta bancaria del usuario en formato IBAN
     */
    public Usuario(String nombreUsuario, String contrasena, String iban) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.iban = iban;
    }

    // Constructor vacio para JPA
    public Usuario() {
        this.nombreUsuario = null;
        this.contrasena = null;
        this.iban = null;
    }

    /**
     * Anade una serie a la lista de series pendientes del usuario.
     * 
     * @param serie La serie que se desea anadir a la lista de pendientes
     * @return true si la serie se anadio correctamente, false si la serie ya estaba
     *         pendiente
     */
    public boolean addSeriePendiente(Serie serie) {
        if (seriesPendientes.contains(serie)) {
            return false;
        }
        // Si no esta en la lista, la agregamos
        seriesPendientes.add(serie);
        return true;
    }

    /**
     * Marca un capitulo como visto por el usuario.
     * Si la serie del capitulo esta en la lista de pendientes, la mueve a la lista
     * de series empezadas.
     * Si el capitulo no se habia visto previamente, lo carga a la factura del mes
     * actual.
     * 
     * @param capitulo El capitulo que se va a marcar como visto
     * @return true si el capitulo se ha marcado como visto correctamente,
     *         false si la serie del capitulo no está en la lista de series
     *         empezadas
     */
    public boolean verCapitulo(Capitulo capitulo) {
        Serie s = null;
        SerieEmpezada se = null;
        SerieEmpezada st = null;
        SerieEmpezada serieEmpezada = null;
        boolean no_visto = false;
        Factura f;

        // Si la serie esta en la lista de pendientes, la movemos a la lista de empezadas
        if ((s = getSeriePendiente(capitulo.getTemporada().getSerie())) != null) {
            serieEmpezada = movPendienteAEmpezadas(s);
        }

        // Si la serie no esta ni pendiente, ni empezada, ni terminada, devolvemos false
        if (serieEmpezada == null) {
            if ((se = getSerieEmpezada(capitulo.getTemporada().getSerie())) != null) {
                serieEmpezada = se;
            }
            else if ((st = getSerieTerminada(capitulo.getTemporada().getSerie())) != null) {
                serieEmpezada = st;
            }
            else {
                return false;
            }
        }

        // Si la serie esta empezada y el capitulo es el ultimo, la movemos a la lista de terminadas
        if (serieEmpezada.esUltimoCapitulo(capitulo)) {
            seriesTerminadas.add(serieEmpezada);
            seriesEmpezadas.remove(serieEmpezada);
        }

        no_visto = serieEmpezada.addCapituloVisto(capitulo);

        // Si el capitulo no se habia visto antes, se agrega el cargo a la factura
        if (no_visto) {
            // Si el mes actual no tiene factura, creamos una nueva
            f = getFacturaMesActual();
            if (f == null) {
                f = nuevaFactura();
            }
            f.addCargo(capitulo);
            facturas.add(f);
        }

        return true;
    }

    /**
     * Mueve una serie de la lista de series pendientes a la lista de series
     * empezadas. Implica crea una nueva SerieEmpezada con la serie proporcionada.
     * 
     * @param serie La serie que se desea mover de pendiente a empezada
     * @return SerieEmpezada La nueva instancia de SerieEmpezada creada
     */
    private SerieEmpezada movPendienteAEmpezadas(Serie serie) {
        // Creamos la serie empezada y la agregamos a la lista de series empezadas
        SerieEmpezada serieEmpezada = new SerieEmpezada(this, serie);
        seriesEmpezadas.add(serieEmpezada);

        // La eliminamos de la lista de series pendientes
        seriesPendientes.remove(serie);

        return serieEmpezada;
    }

    /**
     * Crea una nueva factura asociada al usuario actual.
     * La factura se anade automaticamente a la lista de facturas del usuario.
     * 
     * @return La nueva factura creada
     */
    public Factura nuevaFactura() {
        Factura f = new Factura(this);
        facturas.add(f);
        return f;
    }

    /**
     * Obtiene la factura del mes actual para el usuario.
     * 
     * @return La factura del mes actual existe, null en caso contrario
     */
    public Factura getFacturaMesActual() {
        int mesActual;
        int mesFactura;
        Factura f = null;
        Calendar cal = Calendar.getInstance();

        if (facturas.isEmpty()) {
            return null;
        }

        // Si la ultima factura es del mes actual, la devolvemos
        f = facturas.get(facturas.size() - 1);
        mesFactura = f.getMes();
        cal.setTime(new Date());
        mesActual = cal.get(Calendar.MONTH) + 1;

        if (mesFactura == mesActual) {
            return f;
        } else {
            return null;
        }
    }

    /**
     * Busca una serie especifica en el conjunto de series pendientes del usuario.
     * 
     * @param serie La serie que se desea buscar en la lista de series pendientes
     * @return La serie si se encuentra en la lista de pendientes, null si no se
     *         encuentra
     */
    public Serie getSeriePendiente(Serie serie) {
        for (Serie s : seriesPendientes) {
            if (s.equals(serie)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Busca y devuelve una SerieEmpezada especifica del usuario.
     * 
     * @param serie La Serie que se desea buscar entre las series empezadas del
     *              usuario
     * @return La SerieEmpezada correspondiente si existe, null en caso contrario
     */
    public SerieEmpezada getSerieEmpezada(Serie serie) {
        for (SerieEmpezada se : seriesEmpezadas) {
            if (se.getSerie().equals(serie)) {
                return se;
            }
        }
        return null;
    }
    
    /**
     * Busca y devuelve una SerieEmpezada especifica dentro de la lista de 
     * SeriesTerminadas.
     * 
     * @param serie La Serie que se desea buscar entre las series terminadas del
     *              usuario
     * @return La SerieEmpezada correspondiente si existe, null en caso contrario
     */
    public SerieEmpezada getSerieTerminada(Serie serie) {
        for (SerieEmpezada se : seriesTerminadas) {
            if (se.getSerie().equals(serie)) {
                return se;
            }
        }
        return null;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Set<Serie> getSeriesPendientes() {
        return seriesPendientes;
    }

    public Set<SerieEmpezada> getSeriesEmpezadas() {
        return seriesEmpezadas;
    }

    public Set<SerieEmpezada> getSeriesTerminadas() {
        return seriesTerminadas;
    }

    public boolean isTieneCuotaFija() {
        return tieneCuotaFija;
    }

    public void setTieneCuotaFija(boolean tieneCuotaFija) {
        this.tieneCuotaFija = tieneCuotaFija;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && o instanceof Usuario) {
            Usuario u = (Usuario) o;
            return nombreUsuario.equals(u.getNombreUsuario());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nombreUsuario.hashCode();
    }
}
