package es.unican.polaflix_pablo.domain;

public class CategoriaSeries {
    private final String nombre;
    private float importeCapitulo;

    public CategoriaSeries(String nombre, float importeCapitulo) {
        this.nombre = nombre;
        this.importeCapitulo = importeCapitulo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public float getImporteCapitulo() {
        return importeCapitulo;
    }

    public void setImporteCapitulo(float importeCapitulo) {
        this.importeCapitulo = importeCapitulo;
    }
}
