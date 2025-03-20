package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class Factura {
    private static int contadorFacturas = 0;
    private final String numeroFactura;
    private final Date fechaFactura;
    private float importeTotal = 0;
    private final Usuario usuario;
    private final List<Cargo> cargos = new ArrayList<>();

    public Factura(Usuario usuario) {
        fechaFactura = ultimoDiaMesActual();
        numeroFactura = "POLAFLIX_" + getMes() + "_" + contadorFacturas;
        contadorFacturas++;
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
    
    private Date ultimoDiaMesActual() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
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

    public int getMes() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFactura);
        return cal.get(Calendar.MONTH) + 1;
    }
}
