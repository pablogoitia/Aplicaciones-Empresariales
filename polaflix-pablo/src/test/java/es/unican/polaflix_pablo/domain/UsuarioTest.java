package es.unican.polaflix_pablo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        // Prueba cuando la serie no esta en pendientes ni empezadas
        assertFalse(usuario.verCapitulo(capitulo1));

        // Prueba a ver capitulos cuando la serie esta en pendientes
        usuario.addSeriePendiente(serie1);
        assertTrue(usuario.verCapitulo(capitulo1));

        // Verificar que la serie se movio a empezadas
        assertTrue(usuario.getSeriesPendientes().isEmpty());
        assertFalse(usuario.getSeriesEmpezadas().isEmpty());

        // Prueba a ver el mismo capitulo otra vez
        assertTrue(usuario.verCapitulo(capitulo1));

        // Verificar que solo hay un cargo por el primer visionado
        assertEquals(1, usuario.getFacturas().getLast().getCargos().size());

        // Prueba a ver otro capitulo de la misma serie
        assertTrue(usuario.verCapitulo(capitulo2));
        assertEquals(2, usuario.getFacturas().getLast().getCargos().size());

        // Verificar importe total correcto
        assertEquals(categoria.getImporteCapitulo() * 2,
                usuario.getFacturas().getLast().getImporteTotal());
    }

    @Test
    void testGetFacturaMesActual() {
        // Prueba cuando facturas no tiene nada
        assertNull(usuario.getFacturaMesActual());

        // Anade una factura para el mes actual viendo un capitulo
        usuario.addSeriePendiente(serie1);
        usuario.verCapitulo(capitulo1);
        Factura currentMonthFactura = usuario.getFacturaMesActual();
        assertNotNull(currentMonthFactura);
        assertEquals(Calendar.getInstance().get(Calendar.MONTH) + 1, currentMonthFactura.getMes());

        // Crea una Calendar para el mes anterior
        Calendar prevMonth = Calendar.getInstance();
        prevMonth.add(Calendar.MONTH, -1);

        // Crea un mock de Factura para simular que trabajamos con el mes anterior
        Factura oldFactura = mock(Factura.class);
        when(oldFactura.getFechaFactura()).thenReturn(prevMonth.getTime());
        when(oldFactura.getMes()).thenReturn(prevMonth.get(Calendar.MONTH) + 1);
        usuario.getFacturas().clear(); // Limpia las facturas actuales
        usuario.getFacturas().add(oldFactura);

        // Prueba que la factura del ultimo mes facturado no es devuelta
        assertNull(usuario.getFacturaMesActual());
    }
    
    @Test
    void testGetSeriePendiente() {
        // Prueba cuando la lista de series está vacia
        assertNull(usuario.getSeriePendiente(serie1));
        
        // Prueba cuando la serie esta en la lista
        usuario.addSeriePendiente(serie1);
        Serie encontrada = usuario.getSeriePendiente(serie1);
        assertNotNull(encontrada);
        assertEquals(serie1.getNombre(), encontrada.getNombre());
        
        // Prueba con una serie diferente que no esta en la lista
        Serie serie2 = new Serie("Serie 2", "Sipnosis", categoria, "Creadores", "Actores");
        assertNull(usuario.getSeriePendiente(serie2));
        
        // Prueba con una serie con el mismo nombre pero diferente objeto
        Serie serieMismoNombre = new Serie("Serie 1", "Sinopsis diferente", categoria, "Otros creadores", "Otros actores"); 
        encontrada = usuario.getSeriePendiente(serieMismoNombre);
        assertNotNull(encontrada);
        assertEquals(serie1.getNombre(), encontrada.getNombre());
    }

    @Test
    void testGetSerieEmpezada() {
        // Prueba cuando la lista de series empezadas esta vacia
        assertNull(usuario.getSerieEmpezada(serie1));
        
        // Prueba cuando la serie esta en la lista de empezadas
        usuario.addSeriePendiente(serie1);
        usuario.verCapitulo(capitulo1); // Esto mueve la serie a empezadas
        SerieEmpezada encontrada = usuario.getSerieEmpezada(serie1);
        assertNotNull(encontrada);
        assertEquals(serie1.getNombre(), encontrada.getSerie().getNombre());
        
        // Prueba con una serie diferente que no esta en la lista
        Serie serie2 = new Serie("Serie 2", "Sipnosis", categoria, "Creadores", "Actores");
        assertNull(usuario.getSerieEmpezada(serie2));
        
        // Prueba con una serie con el mismo nombre pero diferente objeto
        Serie serieMismoNombre = new Serie("Serie 1", "Sinopsis diferente", categoria, "Otros creadores", "Otros actores");
        encontrada = usuario.getSerieEmpezada(serieMismoNombre);
        assertNotNull(encontrada);
        assertEquals(serie1.getNombre(), encontrada.getSerie().getNombre());
    }

    @Test
    void testEquals() {
        // Prueba equals con el mismo objeto
        assertTrue(usuario.equals(usuario));
        
        // Prueba equals con null
        assertFalse(usuario.equals(null));
        
        // Prueba equals con diferente tipo
        assertFalse(usuario.equals(new Object()));
        
        // Prueba equals con diferente usuario pero mismo nombre de usuario
        Usuario usuarioMismoNombre = new Usuario("usuario1", "diferente", "ES0000000000");
        assertTrue(usuario.equals(usuarioMismoNombre));
        
        // Prueba equals con nombre de usuario diferente
        Usuario usuarioDiferente = new Usuario("usuario2", "password", "ES1234567890");
        assertFalse(usuario.equals(usuarioDiferente));
    }
    
    @Test
    void testHashCode() {
        // Verifica que el hashCode es el mismo que el del nombre de usuario
        assertEquals(usuario.getNombreUsuario().hashCode(), usuario.hashCode());
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
        assertNotNull(usuario.getSeriesTerminadas());
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