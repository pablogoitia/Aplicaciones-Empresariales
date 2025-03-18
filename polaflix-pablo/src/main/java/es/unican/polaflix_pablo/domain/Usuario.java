package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

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

    public Usuario(String nombreUsuario, String contrasena, String iban) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.iban = iban;
    }

    public boolean addSeriePendiente(Serie serie) {
        if (seriesPendientes.contains(serie)) {
            return false;
        }
        // Si no esta en la lista, la agregamos
        seriesPendientes.add(serie);
        return true;
    }

    public boolean verCapitulo(Capitulo capitulo) {
        Serie s = null;
        SerieEmpezada se = null;
        boolean no_visto = false;
        Factura f = facturas.getLast();

        // Si la serie esta en la lista de empezadas, agregamos el capitulo a la lista de capitulos vistos
        if ((se = getSerieEmpezada(capitulo.getTemporada().getSerie())) != null) {
            se.addCapituloVisto(capitulo);
        } else if ((s = getSeriePendiente(capitulo.getTemporada().getSerie())) != null) {
            // Si la serie esta en la lista de pendientes, la movemos a la lista de empezadas y agregamos el capitulo a la lista de capitulos vistos
            se = movPendienteAEmpezadas(s);
            no_visto = se.addCapituloVisto(capitulo);

            // Si el capitulo no se habia visto antes, se agrega el cargo a la factura
            if (no_visto) {
                f.addCargo(capitulo);
                facturas.add(f);
            }
        } else {
            return false;
        }

        // Si no esta en ninguna lista, devolvemos false
        return true;
    }

    private SerieEmpezada movPendienteAEmpezadas(Serie serie) {
        // Creamos la serie empezada
        SerieEmpezada serieEmpezada = new SerieEmpezada(serie);
        // La añadimos a la lista de series empezadas
        seriesEmpezadas.add(serieEmpezada);
        // La eliminamos de la lista de series pendientes
        seriesPendientes.remove(serie);
        return serieEmpezada;
    }

    public Serie getSeriePendiente(Serie serie) {
        for (Serie s : seriesPendientes) {
            if (s.getNombre().equals(serie.getNombre())) {
                return s;
            }
        }
        return null;
    }

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
