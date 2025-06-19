package es.unican.polaflix_pablo.domain;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.service.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Capitulo implements Comparable<Capitulo> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonView({Views.VerSerie.class})
    private final int numeroCapitulo;

    @JsonView({Views.VerSerie.class})
    private String titulo;

    @JsonView({Views.VerSerie.class})
    private String descripcion;

    @ManyToOne
    private final Temporada temporada;

    /**
     * Constructor de la clase Capitulo.
     * 
     * @param numeroCapitulo numero del capitulo en la temporada
     * @param titulo         titulo del capitulo
     * @param descripcion    descripcion del capitulo
     * @param temporada      temporada a la que pertenece el capitulo
     * @see Temporada
     */
    public Capitulo(int numeroCapitulo, String titulo, String descripcion, Temporada temporada) {
        this.numeroCapitulo = numeroCapitulo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.temporada = temporada;
    }

    // Constructor vacio para JPA
    public Capitulo() {
        this.numeroCapitulo = 0;
        this.titulo = null;
        this.descripcion = null;
        this.temporada = null;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof Capitulo) {
            Capitulo that = (Capitulo) o;
            return getNumeroCapitulo() == that.numeroCapitulo && getTemporada().equals(that.temporada);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCapitulo, temporada);
    }

    @Override
    public int compareTo(Capitulo o) {
        int temporadaCmp = Integer.compare(
            this.getTemporada().getNumeroTemporada(), 
            o.getTemporada().getNumeroTemporada()
        );
        if (temporadaCmp != 0) {
            return temporadaCmp;
        }
        return Integer.compare(
            this.getNumeroCapitulo(), 
            o.getNumeroCapitulo()
        );
    }
}
