package es.unican.polaflix_pablo.domain;

import java.util.ArrayList;
import java.util.List;

public class Temporada {
    private final int numeroTemporada;
    private final Serie serie;
    private final List<Capitulo> capitulos = new ArrayList<>();

    /**
     * Constructor de la clase Temporada.
     * 
     * @param numeroTemporada numero de la temporada dentro de la serie 
     * @param serie serie a la que pertenece esta temporada
     */
    public Temporada(int numeroTemporada, Serie serie) {
        this.numeroTemporada = numeroTemporada;
        this.serie = serie;
    }

    /**
     * Anade un nuevo capitulo a la temporada
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
}
