package es.unican.polaflix_pablo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private Usuario usuario;
    private Serie serie1;
    private Temporada temporada1;
    private Capitulo capitulo1;
    private Capitulo capitulo2;
    private CategoriaSeries categoria;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("usuario1", "password", "ES1234567890");
        categoria = new CategoriaSeries("Categoria 1", 10);
        serie1 = new Serie("Serie 1", "Sipnosis", categoria, "Creadores", "Actores");
        temporada1 = new Temporada(1, serie1);
        capitulo1 = new Capitulo(1, "Capitulo 1", "Descripcion", temporada1);
        capitulo2 = new Capitulo(2, "Capitulo 2", "Descripcion", temporada1);

        serie1.addTemporada(temporada1);
        temporada1.addCapitulo(capitulo1);
        temporada1.addCapitulo(capitulo2);
    }

    @Test
    void testAddSeriePendiente() {
        assertTrue(usuario.addSeriePendiente(serie1));
        assertFalse(usuario.addSeriePendiente(serie1));
    }

    @Test
    void testVerCapitulo() {
        // Comprueba que los capitulos se facturan correctamente
        usuario.addSeriePendiente(serie1);
        List<Factura> facturas = usuario.getFacturas();
        assertTrue(facturas.isEmpty());
        assertTrue(usuario.verCapitulo(capitulo1));
        assertTrue(usuario.verCapitulo(capitulo2));
        facturas = usuario.getFacturas();
        assertFalse(facturas.isEmpty());
        Factura ultimaFactura = facturas.getLast();
        assertEquals(2, ultimaFactura.getCargos().size());
        assertEquals(categoria.getImporteCapitulo() * 2, ultimaFactura.getImporteTotal());
        assertEquals(capitulo2.getTemporada().getSerie().getNombre(), ultimaFactura.getCargos().getLast().getNombreSerie());

        // Comprueba que un capitulo visto no se vuelve a facturar
        assertTrue(usuario.verCapitulo(capitulo2));
        assertEquals(2, ultimaFactura.getCargos().size());
        assertEquals(categoria.getImporteCapitulo() * 2, ultimaFactura.getImporteTotal());
    }

    @Test
    void testGetContrasena() {
        assertEquals("password", usuario.getContrasena());
    }

    @Test
    void testGetFacturas() {
        List<Factura> facturas = usuario.getFacturas();
        assertNotNull(facturas);
    }

    @Test
    void testGetIban() {
        assertEquals("ES1234567890", usuario.getIban());
    }

    @Test
    void testGetNombreUsuario() {
        assertEquals("usuario1", usuario.getNombreUsuario());
    }

    @Test
    void testGetSerieEmpezada() {
        usuario.addSeriePendiente(serie1);
        usuario.verCapitulo(capitulo1);
        assertNotNull(usuario.getSerieEmpezada(serie1));
    }

    @Test
    void testGetSeriePendiente() {
        usuario.addSeriePendiente(serie1);
        assertNotNull(usuario.getSeriePendiente(serie1));
    }

    @Test
    void testGetSeriesEmpezadas() {
        usuario.addSeriePendiente(serie1);
        usuario.verCapitulo(capitulo1);
        assertFalse(usuario.getSeriesEmpezadas().isEmpty());
    }

    @Test
    void testGetSeriesPendientes() {
        usuario.addSeriePendiente(serie1);
        assertFalse(usuario.getSeriesPendientes().isEmpty());
    }

    @Test
    void testGetSeriesTerminadas() {
        List<Serie> seriesTerminadas = usuario.getSeriesTerminadas();
        assertNotNull(seriesTerminadas);
    }

    @Test
    void testIsTieneCuotaFija() {
        assertFalse(usuario.isTieneCuotaFija());
    }

    @Test
    void testSetContrasena() {
        usuario.setContrasena("newpassword");
        assertEquals("newpassword", usuario.getContrasena());
    }

    @Test
    void testSetIban() {
        usuario.setIban("ES0987654321");
        assertEquals("ES0987654321", usuario.getIban());
    }

    @Test
    void testSetNombreUsuario() {
        usuario.setNombreUsuario("newuser");
        assertEquals("newuser", usuario.getNombreUsuario());
    }

    @Test
    void testSetTieneCuotaFija() {
        usuario.setTieneCuotaFija(true);
        assertTrue(usuario.isTieneCuotaFija());
    }
}
