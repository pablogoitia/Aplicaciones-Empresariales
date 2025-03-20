package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

public class Serie {
    private final String nombre;
    private String sinopsis;
    private CategoriaSeries categoria;
    private String creadores;
    private String actores;
    private List<Temporada> temporadas = new ArrayList<>();

    /**
     * Constructor de la clase Serie
     * 
     * @param nombre Nombre de la serie
     * @param sinopsis Resumen de la trama de la serie
     * @param categoria Categoria a la que pertenece la serie
     * @param creadores Nombres de los creadores de la serie
     * @param actores Nombres de los actores principales de la serie
     */
    public Serie(String nombre, String sinopsis, CategoriaSeries categoria, String creadores, String actores) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.categoria = categoria;
        this.creadores = creadores;
        this.actores = actores;
    }

    /**
     * Anade una nueva temporada a la serie.
     * La temporada se anade en la posicion correspondiente a su numero de temporada.
     * 
     * @param temporada la temporada a anadir a la serie
     * @see Temporada
     */
    public void addTemporada(Temporada temporada) {
        temporadas.add(temporada);
    }

    /**
     * Obtiene una temporada de la serie por su numero
     * @param numero El numero de la temporada a obtener
     * @return La temporada correspondiente al numero especificado, o null si no existe
     */
    public Temporada getTemporada(int numero) {
        try {
            return temporadas.get(numero - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
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
