package es.unican.polaflix_pablo.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class SerieEmpezada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Usuario usuario;

    @OneToOne
    private final Serie serie;

    @OneToMany(mappedBy = "serieEmpezada", cascade = CascadeType.ALL)
    private final List<CapituloVisto> capitulosVistos = new LinkedList<>();

    /**
     * Constructor de la clase SerieEmpezada.
     * 
     * @param usuario El usuario que ha empezado a ver la serie
     * @param serie   La serie que el usuario ha comenzado a ver
     * @see Usuario
     * @see Serie
     */
    public SerieEmpezada(Usuario usuario, Serie serie) {
        this.usuario = usuario;
        this.serie = serie;
    }

    // Constructor vacio para JPA
    public SerieEmpezada() {
        this.usuario = null;
        this.serie = null;
    }

    /**
     * Anade un capitulo a la lista de capitulos vistos.
     * 
     * @param capitulo El capitulo que se desea marcar como visto
     * @return true si el capitulo se anadio correctamente a la lista de vistos,
     *         false si el capitulo ya fue visto anteriormente
     */
    public boolean addCapituloVisto(Capitulo capitulo) {
        CapituloVisto capituloVisto = new CapituloVisto(this, capitulo);

        // Si el capitulo no esta en la lista de capitulos vistos, lo anade
        if (!capitulosVistos.contains(capituloVisto)) {
            capitulosVistos.add(capituloVisto);
            return true;
        }
        return false;
    }

    /**
     * Comprueba si es el ultimo capitulo de la serie.
     * @return true si es el ultimo capitulo, false en caso contrario
     */
    public boolean esUltimoCapitulo(Capitulo capitulo) {
        Serie s = capitulo.getTemporada().getSerie();
        Capitulo ultimoCapitulo = s.getTemporadas().getLast().getCapitulos().getLast();

        return capitulo.equals(ultimoCapitulo);
    }

    // Getters
    public Serie getSerie() {
        return serie;
    }

    public List<CapituloVisto> getCapitulosVistos() {
        return capitulosVistos;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o instanceof SerieEmpezada) {
            SerieEmpezada serieEmpezada = (SerieEmpezada) o;
            return usuario.equals(serieEmpezada.usuario) && serie.equals(serieEmpezada.serie);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, serie);
    }
}
