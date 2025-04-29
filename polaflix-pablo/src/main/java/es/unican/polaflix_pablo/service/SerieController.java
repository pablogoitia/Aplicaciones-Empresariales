package es.unican.polaflix_pablo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.polaflix_pablo.domain.Serie;

@RestController
@RequestMapping("/series")
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
}
