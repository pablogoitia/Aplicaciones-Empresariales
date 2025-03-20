package es.unican.polaflix_pablo.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CapituloVistoTest {

    @Test
    void testConstructorAndGetter() {
        Capitulo capitulo = new Capitulo(1, "Capitulo", "Descripcion", null);
        CapituloVisto capituloVisto = new CapituloVisto(capitulo);
        
        assertEquals(capitulo, capituloVisto.getCapitulo());
    }

    @Test 
    void testEquals() {
        Capitulo cap1 = new Capitulo(1, "Capitulo", "Descripcion", null);
        Capitulo cap2 = new Capitulo(2, "Otro capitulo", "Descripcion", null);

        CapituloVisto capVisto1 = new CapituloVisto(cap1);
        CapituloVisto capVisto2 = new CapituloVisto(cap1);
        CapituloVisto capVisto3 = new CapituloVisto(cap2);

        assertEquals(capVisto1, capVisto2);
        assertNotEquals(capVisto1, capVisto3);
    }
}
