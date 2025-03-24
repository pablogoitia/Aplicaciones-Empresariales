package es.unican.polaflix_pablo.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SerieEmpezadaTest {
    private Serie serie;
    private Temporada temporada;
    private Capitulo capitulo1;
    private Capitulo capitulo2;
    SerieEmpezada serieEmpezada;
    
    @BeforeEach
    void setUp() {
        serie = new Serie("Serie1", "Sipnosis", new CategoriaSeries("TestCategoria", 10), null, null);
        temporada = new Temporada(1, serie);
        capitulo1 = new Capitulo(1, "Capitulo1", "Descripcion", temporada);
        capitulo2 = new Capitulo(2, "Capitulo2", "Descripcion", temporada);
        serieEmpezada = new SerieEmpezada(null, serie);
    }

    @Test
    void testAddCapituloVisto() {
        serieEmpezada.addCapituloVisto(capitulo1);
        assertEquals(1, serieEmpezada.getCapitulosVistos().size());

        serieEmpezada.addCapituloVisto(capitulo2);
        assertEquals(2, serieEmpezada.getCapitulosVistos().size());

        // Prueba a agregar un capitulo ya visto
        assertFalse(serieEmpezada.addCapituloVisto(capitulo1));
        assertEquals(2, serieEmpezada.getCapitulosVistos().size());
    }

    @Test
    void testGetCapitulosVistos() {
        serieEmpezada.addCapituloVisto(capitulo1);
        serieEmpezada.addCapituloVisto(capitulo2);
        assertEquals(2, serieEmpezada.getCapitulosVistos().size());

        serieEmpezada.addCapituloVisto(capitulo2);
        assertEquals(2, serieEmpezada.getCapitulosVistos().size());
    }

    @Test
    void testGetSerie() {
        assertEquals(serie, serieEmpezada.getSerie());
        assertNotNull(serieEmpezada.getSerie());
        assertEquals("Serie1", serieEmpezada.getSerie().getNombre());
    }

    @Test
    void testEqualsAndHashCode() {
        Usuario usuario1 = new Usuario("usuario1", "password", "ES1234567890");
        Usuario usuario2 = new Usuario("usuario2", "password", "ES1234567890");
        Serie serie2 = new Serie("Serie2", "Sipnosis2", new CategoriaSeries("TestCategoria2", 20), null, null);

        SerieEmpezada serieEmpezada1 = new SerieEmpezada(usuario1, serie);
        SerieEmpezada serieEmpezada2 = new SerieEmpezada(usuario1, serie);
        SerieEmpezada serieEmpezada3 = new SerieEmpezada(usuario2, serie);
        SerieEmpezada serieEmpezada4 = new SerieEmpezada(usuario1, serie2);

        // Prueba reflexividad
        assertEquals(serieEmpezada1, serieEmpezada1);

        // Prueba simetria
        assertEquals(serieEmpezada1, serieEmpezada2);
        assertEquals(serieEmpezada2, serieEmpezada1);

        // Prueba con diferentes usuarios
        assertNotEquals(serieEmpezada1, serieEmpezada3);
        
        // Prueba con una serie diferente
        assertFalse(serieEmpezada1.equals(serieEmpezada4));

        // Prueba con null y otro tipo de objeto
        assertNotEquals(serieEmpezada1, null);
        assertNotEquals(serieEmpezada1, new Object());

        // Prueba hashCode
        assertEquals(serieEmpezada1.hashCode(), serieEmpezada2.hashCode());
        assertNotEquals(serieEmpezada1.hashCode(), serieEmpezada3.hashCode());
    }
}
