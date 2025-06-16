package es.unican.polaflix_pablo.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{nombreUsuario}/capitulos-vistos/{idSerie}")
    public ResponseEntity<List<Long>> getCapitulosVistosUsuarioSerie(@PathVariable String nombreUsuario, @PathVariable Long idSerie, @RequestParam(required = false) Integer numTemporada) {
        List<Capitulo> capitulos = usuarioService.getCapitulosVistosUsuarioSerie(nombreUsuario, idSerie);

        if (capitulos == null) {
            return ResponseEntity.notFound().build();
        }

        if (numTemporada != null) {
            capitulos.removeIf(c -> c.getTemporada().getNumeroTemporada() != numTemporada);
        }

        List<Long> numerosCapitulos = capitulos.stream()
            .map(cv -> cv.getId())
            .collect(Collectors.toList());

        return ResponseEntity.ok(numerosCapitulos);
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

    @PutMapping("/{nombreUsuario}/ver-capitulo")
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
