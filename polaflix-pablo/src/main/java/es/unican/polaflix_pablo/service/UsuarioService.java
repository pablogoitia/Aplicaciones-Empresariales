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

    public List<Long> getCapitulosVistos(String nombreUsuario, Long idSerie) {
        List<Capitulo> capitulos = getCapitulosVistosBase(nombreUsuario, idSerie);

        if (capitulos == null) {
            return null;
        }

        return getIDsCapitulos(capitulos);
    }

    public List<Long> getCapitulosVistosPorTemporada(String nombreUsuario, Long idSerie, Integer numTemporada) {
        List<Capitulo> capitulos = getCapitulosVistosBase(nombreUsuario, idSerie);

        if (capitulos == null) {
            return null;
        }

        capitulos = capitulos.stream()
                .filter(c -> c.getTemporada().getNumeroTemporada() == numTemporada)
                .toList();
                
        return getIDsCapitulos(capitulos);
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

    public List<Factura> getAllFacturas(String nombreUsuario) {
        return getFacturasBase(nombreUsuario);
    }

    public List<Factura> getFacturaConFiltro(String nombreUsuario, int mes, int anio) {
        List<Factura> facturas = getFacturasBase(nombreUsuario);

        if (facturas != null) {
            facturas.removeIf(f -> f.getMes() != mes || f.getAnio() != anio);
        }

        return facturas;
    }

    @Transactional(readOnly = true)
    private List<Capitulo> getCapitulosVistosBase(String nombreUsuario, Long idSerie) {
        Usuario usuario = ur.findByNombreUsuario(nombreUsuario);
        Serie serie = sr.findById(idSerie).orElse(null);
        SerieEmpezada serieEmpezada;

        if (usuario == null || serie == null) {
            return null;
        }

        serieEmpezada = usuario.getSerieEmpezada(serie);
        if (serieEmpezada == null) {
            serieEmpezada = usuario.getSerieTerminada(serie);
        }

        return (serieEmpezada != null) ? serieEmpezada.getCapitulosVistos() : null;
    }

    private List<Long> getIDsCapitulos(List<Capitulo> capitulos) {
        return capitulos.stream()
                .map(Capitulo::getId)
                .toList();
    }

    @Transactional(readOnly = true)
    private List<Factura> getFacturasBase(String nombreUsuario) {
        Usuario u = ur.findByNombreUsuario(nombreUsuario);

        if (u == null) {
            return null;
        }

        return u.getFacturas();
    }
}
