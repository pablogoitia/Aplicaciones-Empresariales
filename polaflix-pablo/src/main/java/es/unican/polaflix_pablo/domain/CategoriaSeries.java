package es.unican.polaflix_pablo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CategoriaSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private final String nombre;
    private double importeCapitulo;

    /**
     * Constructor de la clase CategoriaSeries
     * 
     * @param nombre          El nombre de la categoria
     * @param importeCapitulo El importe por capitulo de la categoria
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof CategoriaSeries) {
            CategoriaSeries that = (CategoriaSeries) o;
            return getNombre().equals(that.nombre);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
