package es.unican.polaflix_pablo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.domain.Capitulo;
import es.unican.polaflix_pablo.domain.CapituloVisto;
import es.unican.polaflix_pablo.domain.Factura;
import es.unican.polaflix_pablo.domain.Serie;
import es.unican.polaflix_pablo.domain.SerieEmpezada;
import es.unican.polaflix_pablo.domain.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @JsonView({Views.Usuario.class})
    public ResponseEntity<?> getUsuarios() {
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/{nombreUsuario}")
    @JsonView({Views.Usuario.class})
    public ResponseEntity<Usuario> getUsuario(@PathVariable String nombreUsuario) {
        ResponseEntity<Usuario> result;
        Usuario u = usuarioService.getUsuario(nombreUsuario);

        if (u != null) {
            result = ResponseEntity.ok(u);
        } else {
            result = ResponseEntity.notFound().build();
        }

        return result;
    }

    @GetMapping("/{nombreUsuario}/series-pendientes")
    @JsonView({Views.ListaSeries.class})
    public ResponseEntity<Set<Serie>> getSeriesPendientesUsuario(@PathVariable String nombreUsuario) {
        ResponseEntity<Set<Serie>> result;
        Set<Serie> series = usuarioService.getSeriesPendientesUsuario(nombreUsuario);

        if (series != null) {
            result = ResponseEntity.ok(series);
        } else {
            result = ResponseEntity.badRequest().build();
        }

        return result;
    }

    @GetMapping("/{nombreUsuario}/series-empezadas")
    @JsonView({Views.ListaSeries.class})
    public ResponseEntity<Set<SerieEmpezada>> getSeriesEmpezadasUsuario(@PathVariable String nombreUsuario) {
        ResponseEntity<Set<SerieEmpezada>> result;
        Set<SerieEmpezada> series = usuarioService.getSeriesEmpezadasUsuario(nombreUsuario);

        if (series != null) {
            result = ResponseEntity.ok(series);
        } else {
            result = ResponseEntity.badRequest().build();
        }

        return result;
    }

    @GetMapping("/{nombreUsuario}/series-terminadas")
    @JsonView({Views.ListaSeries.class})
    public ResponseEntity<Set<SerieEmpezada>> getSeriesTerminadasUsuario(@PathVariable String nombreUsuario) {
        ResponseEntity<Set<SerieEmpezada>> result;
        Set<SerieEmpezada> series = usuarioService.getSeriesTerminadasUsuario(nombreUsuario);

        if (series != null) {
            result = ResponseEntity.ok(series);
        } else {
            result = ResponseEntity.badRequest().build();
        }

        return result;
    }

    @GetMapping("/{nombreUsuario}/capitulos-vistos/{idSerie}")
    @JsonView({Views.CapitulosVistos.class})
    public ResponseEntity<List<CapituloVisto>> getCapitulosVistosUsuarioSerie(@PathVariable String nombreUsuario, @PathVariable Long idSerie, @RequestParam(required = false) Integer numTemporada) {
        List<CapituloVisto> capitulos = usuarioService.getCapitulosVistosUsuarioSerie(nombreUsuario, idSerie);

        if (capitulos == null) {
            return ResponseEntity.notFound().build();
        }

        if (numTemporada != null) {
            capitulos.removeIf(c -> c.getCapitulo().getTemporada().getNumeroTemporada() != numTemporada);
        }

        return ResponseEntity.ok(capitulos);
    }

    @GetMapping("/{nombreUsuario}/capitulos-vistos/{idSerie}/ultima-temporada")
    @JsonView({Views.NumTemporada.class})
    public ResponseEntity<Integer> getUltimaTemporadaVista(@PathVariable String nombreUsuario, @PathVariable Long idSerie) {
        Integer ultimaTemporada = usuarioService.getUltimaTemporadaVista(nombreUsuario, idSerie);

        if (ultimaTemporada == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(ultimaTemporada);
    }

    @GetMapping("/{nombreUsuario}/ver-capitulo")
    @JsonView({Views.CapitulosVistos.class})
    public ResponseEntity<Capitulo> visualizarCapitulo(
        @PathVariable String nombreUsuario,
        @RequestParam Long idSerie,
        @RequestParam Integer numTemporada,
        @RequestParam Integer numCapitulo) {
        
        Capitulo c = usuarioService.verCapitulo(nombreUsuario, idSerie, numTemporada, numCapitulo);

        if (c == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(c);
    }

    @PutMapping("/{nombreUsuario}/nueva-pendiente/{idSerie}")
    @JsonView({Views.IdSerie.class})
    public ResponseEntity<Serie> nuevaPendiente(@PathVariable String nombreUsuario, @PathVariable Long idSerie) {
        ResponseEntity<Serie> result;
        Serie s = usuarioService.anhadeSeriePendiente(nombreUsuario, idSerie);

        if (s != null) {
            result = ResponseEntity.ok(s);
        } else {
            result = ResponseEntity.badRequest().build();
        }

        return result;
    }

    @GetMapping("/{nombreUsuario}/facturas")
    @JsonView({Views.Factura.class})
    public ResponseEntity<List<Factura>> getFactura(@PathVariable String nombreUsuario, @RequestParam(required = false) Integer mes, @RequestParam(required = false) Integer anio) {
        ResponseEntity<List<Factura>> result;
        Usuario u = usuarioService.getUsuario(nombreUsuario);
        List<Factura> facturas = null;

        if (u == null) {
            return ResponseEntity.notFound().build();
        } else if ((mes == null) != (anio == null)) {
            return ResponseEntity.badRequest().build();
        }
        
        if (mes != null && anio != null) {
            facturas = usuarioService.getFacturaConFiltro(nombreUsuario, mes.intValue(), anio.intValue());
        } else {
            facturas = usuarioService.getAllFacturas(nombreUsuario);
        }

        if (facturas != null) {
            result = ResponseEntity.ok(facturas);
        } else {
            result = ResponseEntity.notFound().build();
        }

        return result;
    }
}
