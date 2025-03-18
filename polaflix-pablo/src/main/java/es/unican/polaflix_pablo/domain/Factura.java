package es.unican.polaflix_pablo.domain;

import java.util.Date;

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

    public Cargo addCargo(Capitulo capitulo) {
        Cargo cargo = new Cargo(capitulo.getTemporada().getSerie().getNombre(), capitulo.getTemporada().getNumeroTemporada() + "x" + capitulo.getNumeroCapitulo(), capitulo.getTemporada().getSerie().getCategoria().getImporteCapitulo());
        importeTotal += cargo.getImporte();
        return cargo;
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
