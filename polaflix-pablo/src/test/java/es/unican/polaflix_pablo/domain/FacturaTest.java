package es.unican.polaflix_pablo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
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
        serie = new Serie("Serie1", "Sipnosis", new CategoriaSeries("TestCategoria", 10), null, null);
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