package es.unican.polaflix_pablo.domain;

public class CategoriaSeries {
    private final String nombre;
    private double importeCapitulo;

    public CategoriaSeries(String nombre, double importeCapitulo) {
        this.nombre = nombre;
        this.importeCapitulo = importeCapitulo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public double getImporteCapitulo() {
        return importeCapitulo;
    }

    public void setImporteCapitulo(double importeCapitulo) {
        this.importeCapitulo = importeCapitulo;
    }
}
