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
import es.unican.polaflix_pablo.service.SerieService;
import es.unican.polaflix_pablo.service.Views;

@RestController
@RequestMapping("/series")
@CrossOrigin(origins = {"http://localhost:4200", "http://0.0.0.0:4200"})
public class SerieController {

    @Autowired
    private SerieService serieService;
    
    @GetMapping
    @JsonView({Views.InfoSerie.class})
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

    @GetMapping("/{idSerie}")
    @JsonView({Views.VerSerie.class})
    public ResponseEntity<Serie> getSerie(@PathVariable Long idSerie) {
        ResponseEntity<Serie> result;
        Serie s = serieService.getSerieById(idSerie);

        if (s != null) {
            result = ResponseEntity.ok(s);
        } else {
            result = ResponseEntity.notFound().build();
        }

		return result;
    }
}
