package es.unican.polaflix_pablo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Calendar;

public class FacturaTest {
    private Usuario usuario;
    private Factura factura;
    private Serie serie;
    private Temporada temporada;
    private Capitulo capitulo1;
    private Capitulo capitulo2;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario("testUser", "password", "ES1234567890");
        factura = new Factura(usuario);
        serie = new Serie("Serie1", "Sinopsis", new CategoriaSeries("TestCategoria", 10), null, null);
        temporada = new Temporada(1, serie);
        capitulo1 = new Capitulo(1, "Capitulo1", "Descripcion", temporada);
        capitulo2 = new Capitulo(2, "Capitulo2", "Descripcion", temporada);
    }

    @Test
    void testAddCargo() {
        Cargo cargo = factura.addCargo(capitulo1);
        
        assertNotNull(cargo);
        assertEquals(10, cargo.getImporte());
        assertEquals(1, factura.getCargos().size());
        assertEquals(10, factura.getImporteTotal());
    }

    @Test
    void testEqualsAndHashCode() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        // Crear dos facturas para el mismo usuario
        Factura factura1 = new Factura(usuario);
        
        // Probar reflexividad
        assertTrue(factura.equals(factura));
        assertEquals(factura.hashCode(), factura.hashCode());
        
        // Probar simetria con diferentes objetos
        // Se espera que sean iguales porque el numero de factura es el mismo, al no contemplar
        // el caso de que se puedan crear dos facturas para el mismo usuario en el mismo mes.
        assertTrue(factura.equals(factura1));
        assertTrue(factura1.equals(factura));
        
        // Probar con null
        assertFalse(factura.equals(null));
        
        // Probar con objeto de diferente tipo
        assertFalse(factura.equals(new Object()));
        
        // Comprobar que facturas con mismo numero son iguales
        String nF = factura.getNumeroFactura();
        Factura facturaIgual = new Factura(usuario);

        // Accedemos al campo privado "nombre"
        Field field = Factura.class.getDeclaredField("numeroFactura");

        field.setAccessible(true); // Permitimos acceso al campo privado
        field.set(facturaIgual, nF);

        assertTrue(factura.equals(facturaIgual));
        assertEquals(factura.hashCode(), facturaIgual.hashCode());
    }

    @Test
    void testGetCargos() {
        factura.addCargo(capitulo1);
        factura.addCargo(capitulo2);
        
        List<Cargo> cargos = factura.getCargos();
        assertEquals(2, cargos.size());
    }

    @Test
    void testGetFechaFactura() {
        assertNotNull(factura.getFechaFactura());
    }

    @Test
    void testGetImporteTotal() {
        factura.addCargo(capitulo1);
        factura.addCargo(capitulo2);
        
        assertEquals(2, factura.getCargos().size());
        assertEquals(capitulo1.getTemporada().getSerie().getCategoria().getImporteCapitulo() * 2, factura.getImporteTotal());
    }

    @Test
    void testGetMes() {
        Calendar cal = Calendar.getInstance();
        int mesFactura = factura.getMes();
        int mesActual = cal.get(Calendar.MONTH) + 1;

        assertTrue(mesFactura >= 1 && mesFactura <= 12);
        assertEquals(mesActual, mesFactura);
    }

    @Test
    void testGetNumeroFactura() {
        assertNotNull(factura.getNumeroFactura());
        String expectedPrefix = "POLAFLIX_" + factura.getMes() + "_";
        assertTrue(factura.getNumeroFactura().startsWith(expectedPrefix));
    }

    @Test
    void testGetUsuario() {
        assertEquals(usuario, factura.getUsuario());
    }

    @Test
    void testSetImporteTotal() {
        factura.setImporteTotal(50);
        assertEquals(50, factura.getImporteTotal());
    }
}