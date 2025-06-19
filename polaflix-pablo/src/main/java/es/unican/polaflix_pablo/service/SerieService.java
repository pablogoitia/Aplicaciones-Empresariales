package es.unican.polaflix_pablo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.unican.polaflix_pablo.domain.Serie;
import es.unican.polaflix_pablo.repositories.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository sr;

    /**
     * Devuelve la lista de todas las series.
     * @return List<Serie> - Lista de series.
     */
    @Transactional(readOnly = true)
    public List<Serie> getAllSeries() {
        return sr.findAll();
    }
    
    /**
     * Devuelve las series que coinciden total o parcialmente con el nombre dado.
     * @return List<Serie> - Lista de series con el nombre dado.
     */
    @Transactional(readOnly = true)
    public List<Serie> getSeriesByName(String name) {
        return sr.findByNombreContainingIgnoreCase(name);
    }

    /**
     * Devuelve la serie con el id dado.
     * @return Serie - Serie con el id dado. Si no existe, devuelve null.
     */
    @Transactional(readOnly = true)
    public Serie getSerieById(Long idSerie) {
        return sr.findById(idSerie).orElse(null);
    }

    /**
     * Devuelve una lista de series cuyo nombre empieza por la inicial dada.
     * @param inicial - Inicial del nombre de la serie.
     * @return List<Serie> - Lista de series.
     */
    @Transactional(readOnly = true)
    public List<Serie> getSeriesByInitial(String inicial) {
        String i = "" + inicial.toUpperCase().charAt(0);
        return sr.findByNombreStartingWithIgnoreCase(i);
    }
}
