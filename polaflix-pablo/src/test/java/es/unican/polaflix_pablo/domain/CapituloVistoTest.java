package es.unican.polaflix_pablo.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CapituloVistoTest {

    @Test
    void testConstructorAndGetter() {
        Capitulo capitulo = new Capitulo(1, "Capitulo", "Descripcion", null);
        CapituloVisto capituloVisto = new CapituloVisto(null, capitulo);
        
        assertEquals(capitulo, capituloVisto.getCapitulo());
    }

    @Test 
    void testEquals() {
        Capitulo cap1 = new Capitulo(1, "Capitulo", "Descripcion", null);
        Capitulo cap2 = new Capitulo(2, "Otro capitulo", "Descripcion", null);
        SerieEmpezada serieEmpezada = new SerieEmpezada(null, null);

        CapituloVisto capVisto1 = new CapituloVisto(serieEmpezada, cap1);
        CapituloVisto capVisto2 = new CapituloVisto(serieEmpezada, cap1);
        CapituloVisto capVisto3 = new CapituloVisto(serieEmpezada, cap2);

        assertEquals(capVisto1, capVisto2);
        assertNotEquals(capVisto1, capVisto3);
    }

    @Test
    void testEqualsAndHashCode() {
        Serie serie = new Serie("Serie", "Sinopsis", null, "", "");
        Temporada temp = new Temporada(1, serie);
        Capitulo cap1 = new Capitulo(1, "Capitulo", "Descripcion", temp);
        Capitulo cap2 = new Capitulo(2, "Otro capitulo", "Descripcion", temp);
        SerieEmpezada serieEmpezada = new SerieEmpezada(null, null);
        
        CapituloVisto capVisto1 = new CapituloVisto(serieEmpezada, cap1);
        CapituloVisto capVisto2 = new CapituloVisto(serieEmpezada, cap1);
        CapituloVisto capVisto3 = new CapituloVisto(serieEmpezada, cap2);
        
        // Test equals reflexivo
        assertTrue(capVisto1.equals(capVisto1));
        
        // Test equals simetrico
        assertTrue(capVisto1.equals(capVisto2));
        assertTrue(capVisto2.equals(capVisto1));
        
        // Test equals transitivo
        assertFalse(capVisto1.equals(capVisto3));
        assertFalse(capVisto2.equals(capVisto3));
        
        // Test equals con null y otro tipo
        assertFalse(capVisto1.equals(null));
        assertFalse(capVisto1.equals(new Object()));
        
        // Test hashCode
        assertEquals(capVisto1.hashCode(), capVisto2.hashCode());
        assertNotEquals(capVisto1.hashCode(), capVisto3.hashCode());
    }
}
