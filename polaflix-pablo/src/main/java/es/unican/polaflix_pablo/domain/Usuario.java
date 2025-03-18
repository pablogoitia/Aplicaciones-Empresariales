package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

import es.unican.polaflix_pablo.domain.SerieEmpezada;
import es.unican.polaflix_pablo.domain.Factura;

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
