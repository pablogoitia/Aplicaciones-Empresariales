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
        serieEmpezada = new SerieEmpezada(serie);
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
}
