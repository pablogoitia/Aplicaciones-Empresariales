package es.unican.polaflix_pablo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.unican.polaflix_pablo.domain.Capitulo;
import es.unican.polaflix_pablo.domain.Factura;
import es.unican.polaflix_pablo.domain.Serie;
import es.unican.polaflix_pablo.domain.SerieEmpezada;
import es.unican.polaflix_pablo.domain.Temporada;
import es.unican.polaflix_pablo.domain.Usuario;
import es.unican.polaflix_pablo.repositories.SerieRepository;
import es.unican.polaflix_pablo.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private SerieRepository sr;

    @Transactional(readOnly = true)
    public Usuario getUsuario(String nombreUsuario) {
        return ur.findByNombreUsuario(nombreUsuario);
    }

    @Transactional(readOnly = true)
    public List<Capitulo> getCapitulosVistosUsuarioSerie(String nombreUsuario, Long idSerie) {
        Usuario usuario = ur.findByNombreUsuario(nombreUsuario);
        Serie serie = sr.findById(idSerie).orElse(null);
        List<Capitulo> capitulos = List.of();
        SerieEmpezada serieEmpezada = null;

        if (usuario == null || serie == null) {
            return null;
        }

        serieEmpezada = usuario.getSerieEmpezada(serie);

        if (serieEmpezada == null) {
            serieEmpezada = usuario.getSerieTerminada(serie);
        }

        if (serieEmpezada != null) {
            capitulos = serieEmpezada.getCapitulosVistos();
        }

        return capitulos;
    }

    @Transactional(readOnly = true)
    public Integer getUltimaTemporadaVista(String nombreUsuario, Long idSerie) {
        Usuario usuario = ur.findByNombreUsuario(nombreUsuario);
        Serie serie = sr.findById(idSerie).orElse(null);
        SerieEmpezada serieEmpezada = null;
        Integer temporada = 1;
        int temp;

        if (usuario == null || serie == null) {
            return null;
        }

        serieEmpezada = usuario.getSerieEmpezada(serie);

        if (serieEmpezada == null) {
            serieEmpezada = usuario.getSerieTerminada(serie);
        }

        if (serieEmpezada != null) {
            temporada = 1;
            for (Capitulo c : serieEmpezada.getCapitulosVistos()) {
                temp = c.getTemporada().getNumeroTemporada();
                if (temp > temporada) {
                    temporada = temp;
                }
            }
        }

        return temporada;
    }

    @Transactional
    public Capitulo verCapitulo(String nombreUsuario, Long idSerie, int numTemporada, int numCapitulo) {
        Usuario u = null;
        Serie s = null;
        Temporada t = null;
        Capitulo c = null;
        boolean visto = false;

        if ((u = ur.findByNombreUsuario(nombreUsuario)) == null ||
            (s = sr.findById(idSerie).orElse(null)) == null ||
            (t = s.getTemporada(numTemporada)) == null ||
            (c = t.getCapitulo(numCapitulo)) == null) {
            return null;
        }

        visto = u.verCapitulo(c);
        ur.save(u);

        return (visto) ? c : null;
    }

    @Transactional
    public Serie anhadeSeriePendiente(String nombreUsuario, Long idSerie) {
        Usuario u = ur.findByNombreUsuario(nombreUsuario);
        Serie s = sr.findById(idSerie).orElse(null);
        boolean res = false;

        if (u == null || s == null) {
            return null;
        }

        res = u.addSeriePendiente(s);

        if (res) {
            ur.save(u);
        }
        
        return s;
    }

    @Transactional(readOnly = true)
    public List<Factura> getAllFacturas(String nombreUsuario) {
        Usuario u = ur.findByNombreUsuario(nombreUsuario);
        List<Factura> facturas = null;

        if (u == null) {
            return null;
        }

        facturas = u.getFacturas();

        return facturas;
    }

    @Transactional(readOnly = true)
    public List<Factura> getFacturaConFiltro(String nombreUsuario, int mes, int anio) {
        Usuario u = ur.findByNombreUsuario(nombreUsuario);
        List<Factura> facturas = null;

        if (u == null) {
            return null;
        }

        facturas = u.getFacturas();

        facturas.removeIf(f -> f.getMes() != mes || f.getAnio() != anio);

        return facturas;
    }
}
