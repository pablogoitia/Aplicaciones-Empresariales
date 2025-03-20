package es.unican.polaflix_pablo.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Usuario {
    // Datos del usuario
    private String nombreUsuario;
    private String contrasena;
    private String iban;

    // Listas de series
    private final List<Serie> seriesPendientes = new ArrayList<>();
    private final List<SerieEmpezada> seriesEmpezadas = new ArrayList<>();
    private final List<Serie> seriesTerminadas = new ArrayList<>();

    // Informacion de facturacion
    private boolean tieneCuotaFija = false;
    private final List<Factura> facturas = new ArrayList<>();

    /**
     * Constructor de la clase Usuario.
     * 
     * @param nombreUsuario nombre de usuario unico para identificar al usuario
     * @param contrasena contrasena del usuario para autenticacion
     * @param iban numero de cuenta bancaria del usuario en formato IBAN
     */
    public Usuario(String nombreUsuario, String contrasena, String iban) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.iban = iban;
    }

    /**
     * Anade una serie a la lista de series pendientes del usuario.
     * 
     * @param serie La serie que se desea anadir a la lista de pendientes
     * @return true si la serie se anadio correctamente, false si la serie ya estaba pendiente
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
     * Si la serie del capitulo esta en la lista de pendientes, la mueve a la lista de series empezadas.
     * Si el capitulo no se habia visto previamente, lo carga a la factura del mes actual.
     * 
     * @param capitulo El capitulo que se va a marcar como visto
     * @return true si el capitulo se ha marcado como visto correctamente, 
     *         false si la serie del capitulo no está en la lista de series empezadas
     */
    public boolean verCapitulo(Capitulo capitulo) {
        Serie s = null;
        SerieEmpezada se = null;
        boolean no_visto = false;
        Factura f;

        // Si la serie esta en la lista de pendientes, la movemos a la lista de empezadas
        if ((s = getSeriePendiente(capitulo.getTemporada().getSerie())) != null) {
            se = movPendienteAEmpezadas(s);
        }

        // Si la serie no esta en la lista de empezadas, devolvemos false
        if ((se = getSerieEmpezada(capitulo.getTemporada().getSerie())) == null) {
            return false;
        }

        no_visto = se.addCapituloVisto(capitulo);
        
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
     * Mueve una serie de la lista de series pendientes a la lista de series empezadas.
     * Implica crea una nueva SerieEmpezada con la serie proporcionada.
     * 
     * @param serie La serie que se desea mover de pendiente a empezada
     * @return SerieEmpezada La nueva instancia de SerieEmpezada creada
     */
    private SerieEmpezada movPendienteAEmpezadas(Serie serie) {
        // Creamos la serie empezada y la agregamos a la lista de series empezadas
        SerieEmpezada serieEmpezada = new SerieEmpezada(serie);
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
     * Busca una serie especifica en la lista de series pendientes del usuario.
     * 
     * @param serie La serie que se desea buscar en la lista de series pendientes
     * @return La serie si se encuentra en la lista de pendientes, null si no se encuentra
     */
    public Serie getSeriePendiente(Serie serie) {
        for (Serie s : seriesPendientes) {
            if (s.getNombre().equals(serie.getNombre())) {
                return s;
            }
        }
        return null;
    }

    /**
     * Busca y devuelve una SerieEmpezada especifica del usuario.
     * 
     * @param serie La Serie que se desea buscar entre las series empezadas del usuario
     * @return La SerieEmpezada correspondiente si existe, null en caso contrario
     */
    public SerieEmpezada getSerieEmpezada(Serie serie) {
        for (SerieEmpezada se : seriesEmpezadas) {
            if (se.getSerie().getNombre().equals(serie.getNombre())) {
                return se;
            }
        }
        return null;
    }

    // Getters y Setters
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

    public List<Serie> getSeriesPendientes() {
        return seriesPendientes;
    }

    public List<SerieEmpezada> getSeriesEmpezadas() {
        return seriesEmpezadas;
    }

    public List<Serie> getSeriesTerminadas() {
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
}
