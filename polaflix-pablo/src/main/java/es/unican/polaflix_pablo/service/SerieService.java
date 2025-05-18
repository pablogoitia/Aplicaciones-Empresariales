package es.unican.polaflix_pablo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.unican.polaflix_pablo.domain.Serie;
import es.unican.polaflix_pablo.domain.Temporada;
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
     * Devuelve la primera serie que coincide total o parcialmente con el nombre dado.
     * @return List<Serie> - Lista de series que coinciden con el nombre dado.
     */
    @Transactional(readOnly = true)
    public List<Serie> getSeriesByName(String name) {
        return sr.findByNombreStartingWithIgnoreCase(name);
    }

    /**
     * Devuelve la serie con el id dado.
     * @return Serie - Serie con el id dado. Si no existe, devuelve null.
     */
    @Transactional(readOnly = true)
    public Serie getSerieById(Long id) {
        return sr.findById(id).orElse(null);
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

    /**
     * Devuelve la lista de temporadas de una serie.
     * 
     * @param idSerie - Id de la serie.
     * @return List<Temporada> - Lista de temporadas de la serie. Si la serie no existe, devuelve null.
     */
    @Transactional(readOnly = true)
    public List<Temporada> getAllTemporadas(Long idSerie) {
        Serie serie = sr.findById(idSerie).orElse(null);

        if (serie == null) {
            return null;
        }
        
        return serie.getTemporadas();
    }

    /**
     * Devuelve una temporada de una serie.
     * @param idSerie - Id de la serie.
     * @param idTemporada - Numero de la temporada.
     * @return Temporada - Temporada con los identificadores dados. Si no existe, devuelve null.
     */
    @Transactional(readOnly = true)
    public Temporada getTemporadaById(Long idSerie, int idTemporada) {
        Serie serie = sr.findById(idSerie).orElse(null);

        if (serie == null) {
            return null;
        }
        
        return serie.getTemporada(idTemporada);
    }
}
