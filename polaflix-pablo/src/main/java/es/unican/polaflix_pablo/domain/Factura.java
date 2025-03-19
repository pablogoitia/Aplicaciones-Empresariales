package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {
    private final String numeroFactura;
    private final Date fechaFactura;
    private float importeTotal = 0;
    private final Usuario usuario;
    private final List<Cargo> cargos = new ArrayList<>();

    public Factura(Usuario usuario) {
        // TODO: this.numeroFactura = generaNumeroFactura();
        numeroFactura = "POLAFLIX_MARZO_AA01";
        // TODO: this.fechaFactura = ultimoDiaDeEsteMes();
        fechaFactura = new Date();
        this.usuario = usuario;
    }

    public Cargo addCargo(Capitulo capitulo) {
        Cargo cargo = new Cargo(capitulo.getTemporada().getSerie().getNombre(),
                capitulo.getTemporada().getNumeroTemporada() + "x" + capitulo.getNumeroCapitulo(),
                capitulo.getTemporada().getSerie().getCategoria().getImporteCapitulo());
        importeTotal += cargo.getImporte();
        cargos.add(cargo);
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

    public List<Cargo> getCargos() {
        return cargos;
    }
}
