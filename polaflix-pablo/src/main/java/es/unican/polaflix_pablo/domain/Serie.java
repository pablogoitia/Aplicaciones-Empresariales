package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.service.Views;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.InfoSerie.class, Views.VerSerie.class, Views.Usuario.class})
    private Long id;
    
    @JsonView({Views.InfoSerie.class, Views.VerSerie.class, Views.Usuario.class})
    private final String nombre;
    
    @JsonView({Views.InfoSerie.class})
    @Column(length = 2000)
    private String sinopsis;

    @JsonView({Views.InfoSerie.class})
    private List<String> creadores = new ArrayList<>();

    @JsonView({Views.InfoSerie.class})
    private List<String> actores = new ArrayList<>();

    @ManyToOne
    @JsonView({Views.VerSerie.class})
    private CategoriaSeries categoria;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    @JsonView({Views.VerSerie.class})
    @JsonManagedReference
    private final List<Temporada> temporadas = new ArrayList<>();

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
    public Serie(String nombre, String sinopsis, CategoriaSeries categoria, List<String> creadores, List<String> actores) {
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

    public List<String> getCreadores() {
        return creadores;
    }

    public void setCreadores(List<String> creadores) {
        this.creadores = creadores;
    }

    public List<String> getActores() {
        return actores;
    }

    public void setActores(List<String> actores) {
        this.actores = actores;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof Serie) {
            Serie serie = (Serie) o;
            return nombre.equals(serie.nombre) && creadores.equals(serie.creadores);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(nombre, creadores);
    }
}
