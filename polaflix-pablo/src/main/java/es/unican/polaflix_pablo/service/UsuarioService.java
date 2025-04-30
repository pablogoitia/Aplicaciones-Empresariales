package es.unican.polaflix_pablo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.unican.polaflix_pablo.domain.Capitulo;
import es.unican.polaflix_pablo.domain.CapituloVisto;
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

    public Usuario getUsuario(String nombreUsuario) {
        return ur.findByNombreUsuario(nombreUsuario);
    }

    public List<CapituloVisto> getCapitulosVistosUsuarioSerie(String nombreUsuario, Long idSerie) {
        Usuario usuario = ur.findByNombreUsuario(nombreUsuario);
        Serie serie = sr.findById(idSerie).orElse(null);
        List<CapituloVisto> capitulos = List.of();
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
            for (CapituloVisto c : serieEmpezada.getCapitulosVistos()) {
                temp = c.getCapitulo().getTemporada().getNumeroTemporada();
                if (temp > temporada) {
                    temporada = temp;
                }
            }
        }

        return temporada;
    }

    public Capitulo verCapitulo(String nombreUsuario, Long idSerie, int numTemporada, int numCapitulo) {
        Usuario u = ur.findByNombreUsuario(nombreUsuario);
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

    public List<Factura> getAllFacturas(String nombreUsuario) {
        Usuario u = ur.findByNombreUsuario(nombreUsuario);
        List<Factura> facturas = null;

        if (u == null) {
            return null;
        }

        facturas = u.getFacturas();

        return facturas;
    }

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
