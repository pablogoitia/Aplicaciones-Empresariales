package es.unican.polaflix_pablo.domain;

import es.unican.polaflix_pablo.domain.Temporada;

public class Capitulo {
    private final int numeroCapitulo;
    private String titulo;
    private String descripcion;
    private final Temporada temporada;

    public Capitulo(int numeroCapitulo, String titulo, String descripcion, Temporada temporada) {
        this.numeroCapitulo = numeroCapitulo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.temporada = temporada;
    }

    // Getters y Setters
    public int getNumeroCapitulo() {
        return numeroCapitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Temporada getTemporada() {
        return temporada;
    }
}
