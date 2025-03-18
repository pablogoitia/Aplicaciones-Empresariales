package es.unican.polaflix_pablo.domain;

import java.util.Date;

import es.unican.polaflix_pablo.domain.Usuario;
import es.unican.polaflix_pablo.domain.Cargo;

public class Factura {
    private final String numeroFactura;
    private final Date fechaFactura;
    private float importeTotal = 0;
    private final Usuario usuario;

    public Factura(Usuario usuario) {
        // this.numeroFactura = generaNumeroFactura();
        numeroFactura = "POLAFLIX_MARZO_AA01";
        // this.fechaFactura = ultimoDiaDeEsteMes();
        fechaFactura = new Date();
        this.usuario = usuario;
    }

    // Getters y Setters
    public String getNumeroFactura() {
        return numeroFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public float getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(float importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
