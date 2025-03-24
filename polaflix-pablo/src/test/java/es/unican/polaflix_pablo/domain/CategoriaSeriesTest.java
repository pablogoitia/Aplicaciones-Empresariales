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

    @Test
    public void testEqualsAndHashCode() {
        // Casos de prueba para equals
        CategoriaSeries categoria1 = new CategoriaSeries("Basic", 1.99);
        CategoriaSeries categoria2 = new CategoriaSeries("Basic", 2.99);
        CategoriaSeries categoria3 = new CategoriaSeries("Premium", 1.99);
        
        // Prueba reflexividad
        assertTrue(categoria1.equals(categoria1));
        
        // Prueba simetria
        assertTrue(categoria1.equals(categoria2));
        assertTrue(categoria2.equals(categoria1));
        
        // Prueba con diferentes nombres
        assertFalse(categoria1.equals(categoria3));
        
        // Prueba con null
        assertFalse(categoria1.equals(null));
        
        // Prueba con objeto de otra clase
        assertFalse(categoria1.equals(new Object()));
        
        // Casos de prueba para hashCode
        assertEquals(categoria1.hashCode(), categoria2.hashCode());
        assertNotEquals(categoria1.hashCode(), categoria3.hashCode());
    }
}
