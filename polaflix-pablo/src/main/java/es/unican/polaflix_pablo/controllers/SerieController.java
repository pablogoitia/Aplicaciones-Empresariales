package es.unican.polaflix_pablo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.domain.Serie;
import es.unican.polaflix_pablo.domain.Temporada;
import es.unican.polaflix_pablo.service.SerieService;
import es.unican.polaflix_pablo.service.Views;

@RestController
@RequestMapping("/series")
@CrossOrigin(origins = {"http://localhost:4200", "http://0.0.0.0:4200"})
public class SerieController {

    @Autowired
    private SerieService serieService;
    
    @GetMapping
    @JsonView({Views.Serie.class})
    public ResponseEntity<List<Serie>> getSeries(@RequestParam(required = false) String inicial, @RequestParam(required = false) String nombre) {
        List<Serie> s = null;

        if (inicial != null && nombre != null) {
            return ResponseEntity.badRequest().build();
        }

        if (inicial == null && nombre == null) {
            s = serieService.getAllSeries();
        } else if (inicial != null) {
            s = serieService.getSeriesByInitial(inicial);
        } else {
            s = serieService.getSeriesByName(nombre);
        }

		return ResponseEntity.ok(s);
    }

    @GetMapping("/{id}")
    @JsonView({Views.Serie.class})
    public ResponseEntity<Serie> getSerie(@PathVariable Long id) {
        ResponseEntity<Serie> result;
        Serie s = serieService.getSerieById(id);

        if (s != null) {
            result = ResponseEntity.ok(s);
        } else {
            result = ResponseEntity.notFound().build();
        }

		return result;
    }

    @GetMapping("/{id}/temporadas")
    @JsonView({Views.VerSerie.class})
    public ResponseEntity<Serie> getTemporadasSerie(@PathVariable Long id) {
        ResponseEntity<Serie> result;
        Serie t = serieService.getAllTemporadas(id);

        if (t != null) {
            result = ResponseEntity.ok(t);
        } else {
            result = ResponseEntity.notFound().build();
        }

        return result;
    }
    
    @GetMapping("/{id}/temporadas/{numTemporada}")
    @JsonView({Views.VerSerie.class})
    public ResponseEntity<Temporada> getTemporada(@PathVariable Long id, @PathVariable int numTemporada) {
        ResponseEntity<Temporada> result;
        Temporada t = serieService.getTemporadaById(id, numTemporada);

        if (t == null) {
            result = ResponseEntity.notFound().build();
        } else {
            result = ResponseEntity.ok(t);
        }

		return result;
    }
}
