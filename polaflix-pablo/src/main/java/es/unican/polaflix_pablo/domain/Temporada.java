package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.service.Views;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Temporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonView({Views.VerSerie.class})
    private final int numeroTemporada;

    @ManyToOne
    @JsonBackReference
    private final Serie serie;

    @JsonView({Views.VerSerie.class})
    @OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL)
    @JsonManagedReference
    private final List<Capitulo> capitulos = new ArrayList<>();

    /**
     * Constructor de la clase Temporada.
     * 
     * @param numeroTemporada numero de la temporada dentro de la serie
     * @param serie           serie a la que pertenece esta temporada
     * @see Serie
     */
    public Temporada(int numeroTemporada, Serie serie) {
        this.numeroTemporada = numeroTemporada;
        this.serie = serie;
    }

    // Constructor vacio para JPA
    public Temporada() {
        this.numeroTemporada = 0;
        this.serie = null;
    }

    /**
     * Anade un nuevo capitulo a la temporada
     * 
     * @param capitulo El capitulo que se va a anadir a la temporada
     */
    public void addCapitulo(Capitulo capitulo) {
        capitulos.add(capitulo);
    }

    /**
     * Obtiene un capitulo especifico de la temporada
     * 
     * @param numero Numero del capitulo que se desea obtener
     * @return El capitulo solicitado, o null si el numero es invalido o no existe
     */
    public Capitulo getCapitulo(int numero) {
        try {
            return capitulos.get(numero - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    // Getters
    public int getNumeroTemporada() {
        return numeroTemporada;
    }

    public Serie getSerie() {
        return serie;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof Temporada) {
            Temporada temporada = (Temporada) o;
            return numeroTemporada == temporada.numeroTemporada && serie.equals(temporada.serie);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroTemporada, serie);
    }
}
