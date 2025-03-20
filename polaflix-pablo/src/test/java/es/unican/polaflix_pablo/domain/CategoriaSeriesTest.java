package es.unican.polaflix_pablo.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CategoriaSeriesTest {

    @Test
    public void testConstructorAndGetters() {
        String nombre = "Gold";
        float importe = 1.5f;
        
        CategoriaSeries categoria = new CategoriaSeries(nombre, importe);
        
        assertEquals(nombre, categoria.getNombre());
        assertEquals(importe, categoria.getImporteCapitulo());
    }

    @Test 
    public void testSetImporteCapitulo() {
        CategoriaSeries categoria = new CategoriaSeries("Basic", 1.99f);
        float nuevoImporte = 2.99f;
        
        categoria.setImporteCapitulo(nuevoImporte);
        
        assertEquals(nuevoImporte, categoria.getImporteCapitulo(), 0.001);
    }
}
