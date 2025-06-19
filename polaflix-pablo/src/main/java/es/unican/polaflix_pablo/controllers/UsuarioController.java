package es.unican.polaflix_pablo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.domain.Capitulo;
import es.unican.polaflix_pablo.domain.Factura;
import es.unican.polaflix_pablo.domain.Serie;
import es.unican.polaflix_pablo.domain.Usuario;
import es.unican.polaflix_pablo.service.UsuarioService;
import es.unican.polaflix_pablo.service.Views;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:4200", "http://0.0.0.0:4200"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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

    @GetMapping("/{nombreUsuario}/capitulos-vistos")
    public ResponseEntity<List<Long>> getCapitulosVistos(@PathVariable String nombreUsuario, @RequestParam Long idSerie, @RequestParam(required = false) Integer numTemporada) {
        List<Long> capitulos;

        if (numTemporada == null) {
            capitulos = usuarioService.getCapitulosVistos(nombreUsuario, idSerie);
        } else {
            capitulos = usuarioService.getCapitulosVistosPorTemporada(nombreUsuario, idSerie, numTemporada);
        }

        if (capitulos == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(capitulos);
    }

    @PutMapping("/{nombreUsuario}/capitulos-vistos")
    public ResponseEntity<Capitulo> visualizarCapitulo(
        @PathVariable String nombreUsuario,
        @RequestParam Long idSerie,
        @RequestParam Integer numTemporada,
        @RequestParam Integer numCapitulo) {
        
        Capitulo c;

        c = usuarioService.verCapitulo(nombreUsuario, idSerie, numTemporada, numCapitulo);

        if (c == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{nombreUsuario}/series-pendientes/{idSerie}")
    public ResponseEntity<Serie> nuevaPendiente(@PathVariable String nombreUsuario, @PathVariable Long idSerie) {
        Serie s = usuarioService.anhadeSeriePendiente(nombreUsuario, idSerie);

        if (s == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{nombreUsuario}/facturas")
    @JsonView({Views.Factura.class})
    public ResponseEntity<List<Factura>> getFactura(@PathVariable String nombreUsuario, @RequestParam(required = false) Integer mes, @RequestParam(required = false) Integer anio) {
        ResponseEntity<List<Factura>> result;
        List<Factura> facturas = null;

        if ((mes == null) != (anio == null)) {
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
