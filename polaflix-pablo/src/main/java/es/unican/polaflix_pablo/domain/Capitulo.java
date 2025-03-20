package es.unican.polaflix_pablo.domain;

public class Capitulo {
    private final int numeroCapitulo;
    private String titulo;
    private String descripcion;
    private final Temporada temporada;

    /**
     * Constructor de la clase Capitulo.
     * 
     * @param numeroCapitulo numero del capitulo en la temporada
     * @param titulo         titulo del capitulo
     * @param descripcion    descripcion del capitulo
     * @param temporada      temporada a la que pertenece el capitulo
     */
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
