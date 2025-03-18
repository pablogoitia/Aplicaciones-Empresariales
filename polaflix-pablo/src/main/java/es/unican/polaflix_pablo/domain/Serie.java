package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

import es.unican.polaflix_pablo.domain.CategoriaSeries;
import es.unican.polaflix_pablo.domain.Temporada;

public class Serie {
    private final String nombre;
    private String sinopsis;
    private CategoriaSeries categoria;
    private String creadores;
    private String actores;
    private List<Temporada> temporadas = new ArrayList<>();

    public Serie(String nombre, String sinopsis, CategoriaSeries categoria, String creadores, String actores) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.categoria = categoria;
        this.creadores = creadores;
        this.actores = actores;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public CategoriaSeries getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaSeries categoria) {
        this.categoria = categoria;
    }

    public String getCreadores() {
        return creadores;
    }

    public void setCreadores(String creadores) {
        this.creadores = creadores;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }
}
