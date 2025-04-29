package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.service.Views;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Serie {
    @JsonView({Views.Serie.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonView({Views.Serie.class})
    @Column(unique = true)
    private final String nombre;
    
    @JsonView({Views.Serie.class})
    private String sinopsis;

    @JsonView({Views.Serie.class})
    private String creadores;

    @JsonView({Views.Serie.class})
    private String actores;

    @JsonView({Views.Serie.class})
    @OneToOne
    private CategoriaSeries categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Temporada> temporadas = new ArrayList<>();

    /**
     * Constructor de la clase Serie
     * 
     * @param nombre    Nombre de la serie
     * @param sinopsis  Resumen de la trama de la serie
     * @param categoria Categoria a la que pertenece la serie
     * @param creadores Nombres de los creadores de la serie
     * @param actores   Nombres de los actores principales de la serie
     * @see CategoriaSeries
     */
    public Serie(String nombre, String sinopsis, CategoriaSeries categoria, String creadores, String actores) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.categoria = categoria;
        this.creadores = creadores;
        this.actores = actores;
    }

    // Constructor vacio para JPA
    public Serie() {
        this.nombre = null;
        this.sinopsis = null;
        this.categoria = null;
        this.creadores = null;
        this.actores = null;
    }

    /**
     * Anade una nueva temporada a la serie.
     * La temporada se anade en la posicion correspondiente a su numero de
     * temporada.
     * 
     * @param temporada la temporada a anadir a la serie
     * @see Temporada
     */
    public void addTemporada(Temporada temporada) {
        temporadas.add(temporada);
    }

    /**
     * Obtiene una temporada de la serie por su numero
     * 
     * @param numero El numero de la temporada a obtener
     * @return La temporada correspondiente al numero especificado, o null si no
     *         existe
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof Serie) {
            Serie serie = (Serie) o;
            return nombre.equals(serie.nombre);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
