package es.unican.polaflix_pablo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import es.unican.polaflix_pablo.domain.Capitulo;
import es.unican.polaflix_pablo.domain.CategoriaSeries;
import es.unican.polaflix_pablo.domain.Serie;
import es.unican.polaflix_pablo.domain.SerieEmpezada;
import es.unican.polaflix_pablo.domain.Temporada;
import es.unican.polaflix_pablo.domain.Usuario;
import es.unican.polaflix_pablo.repositories.CategoriaSeriesRepository;
import es.unican.polaflix_pablo.repositories.SerieRepository;
import es.unican.polaflix_pablo.repositories.UsuarioRepository;

@Component
public class AppFeeder implements CommandLineRunner {

	@Autowired
	protected UsuarioRepository ur;
	@Autowired
	protected SerieRepository sr;
	@Autowired
	protected CategoriaSeriesRepository csr;

	private Usuario u1, u2;
	private Serie s1, s2, s3;

	@Override
	public void run(String... args) throws Exception {
		feedUsuarios();
		feedCategoriasSeries();
		feedSeries();
		feedListasSeriesUsuario();
		// testSimple();

		System.out.println("Application feeded");
	}

	private void feedUsuarios() {
		u1 = new Usuario("pablogoitia", "123456", "ES7921000813610123456789");
		u2 = new Usuario("johndoe", "789123", "ES6000491500051234567892");
		ur.save(u1);
		ur.save(u2);
	}

	private void feedCategoriasSeries() {
		CategoriaSeries catSer1 = new CategoriaSeries("Estándar", 0.50);
		CategoriaSeries catSer2 = new CategoriaSeries("Silver", 0.75);
		CategoriaSeries catSer3 = new CategoriaSeries("Gold", 1.50);

		csr.save(catSer1);
		csr.save(catSer2);
		csr.save(catSer3);
	}

	private void feedSeries() {
		s1 = new Serie("La casa de papel", "Un grupo de criminales lleva a cabo el mayor atraco a la Real Casa de la Moneda de España.",
				csr.findById(1L).get(), "Álex Pina", "Úrsula Corberó, Álvaro Morte...");
		s2 = new Serie("Merlí", "Un profesor de filosofía enseña a sus alumnos a pensar por sí mismos.",
				csr.findById(2L).get(), "Héctor Lozano", "Francesc Orella, Carlos Cuevas...");
		s3 = new Serie("Reina Roja", "Antonia es “Reina Roja”, la pieza fundamental de una organización policial secreta que resuelve crímenes atroces.",
				csr.findById(3L).get(), "Koldo Serra; Julián de Tavira", "Vicky Luengo, Hovik Keuchkerian...");

		// Añadir temporadas a las series
		Temporada s1t1 = new Temporada(1, s1);
		Temporada s1t2 = new Temporada(2, s1);
		s1.addTemporada(s1t1);
		s1.addTemporada(s1t2);
		Temporada s2t1 = new Temporada(1, s2);
		Temporada s2t2 = new Temporada(2, s2);
		s2.addTemporada(s2t1);
		s2.addTemporada(s2t2);
		Temporada s3t1 = new Temporada(1, s3);
		Temporada s3t2 = new Temporada(2, s3);
		s3.addTemporada(s3t1);
		s3.addTemporada(s3t2);

		// Añadir un capitulo a cada temporada
		Capitulo s1t1c1 = new Capitulo(1, "Efectuar lo acordado", "El Profesor recluta a una joven experta en atracos y a otros siete delincuentes para llevar a cabo un gran asalto a la Fábrica Nacional de Moneda y Timbre.", s1t1);
		Capitulo s1t2c1 = new Capitulo(1, "Hemos vuelto", "Desconsolada por el secuestro de Río, Tokio recurre al Profesor. Después de tramar un nuevo y osado plan para rescatarlo, sólo queda reunir al resto de la banda.", s1t2);
		s1t1.addCapitulo(s1t1c1);
		s1t2.addCapitulo(s1t2c1);
		Capitulo s2t1c1 = new Capitulo(1, "Els peripatètics", "El piloto de la serie", s2t1);
		Capitulo s2t2c1 = new Capitulo(1, "Plató", "El primer capitulo de la segunda temporada", s2t2);
		s2t1.addCapitulo(s2t1c1);
		s2t2.addCapitulo(s2t2c1);
		Capitulo s3t1c1 = new Capitulo(1, "Un salto", "El piloto de la serie", s3t1);
		Capitulo s3t2c1 = new Capitulo(1, "Un tatuaje", "El primer capitulo de la segunda temporada", s3t2);
		s3t1.addCapitulo(s3t1c1);
		s3t2.addCapitulo(s3t2c1);

		// Guarda todo
		sr.save(s1);
		sr.save(s2);
		sr.save(s3);
	}

	private void feedListasSeriesUsuario() {
		/**
		 * De las tres series del sistema:
		 * - La primera estara en la lista de terminadas.
		 * - La segunda estara en la lista de empezadas.
		 * - La tercera estara en la lista de pendientes.
		 */

		// Todas las series estan pendientes
		u1.addSeriePendiente(s1);
		u1.addSeriePendiente(s2);
		u1.addSeriePendiente(s3);

		// Empezamos a ver la segunda serie
		u1.verCapitulo(s2.getTemporada(1).getCapitulo(1));

		// Terminamos la primera serie
		u1.verCapitulo(s1.getTemporada(2).getCapitulo(1));

		// Guarda todo
		ur.save(u1);
	}

	private void testSimple() {
		// Test simple para comprobar que la aplicacion funciona
		System.out.println("Usuario 1: " + u1.getNombreUsuario());
		System.out.println("Series pendientes: ");
		for (Serie s : u1.getSeriesPendientes()) {
			System.out.println("- " + s.getNombre());
		}
		System.out.println("Series empezadas: ");
		for (SerieEmpezada se : u1.getSeriesEmpezadas()) {
			System.out.println("- " + se.getSerie().getNombre());
		}
		System.out.println("Series terminadas: ");
		for (SerieEmpezada st : u1.getSeriesTerminadas()) {
			System.out.println("- " + st.getSerie().getNombre());
		}
		System.out.println();

		// Muestra la lista de usuarios
		System.out.println("Lista de usuarios:");
		for (Usuario u : ur.findAll()) {
			System.out.println("- " + u.getNombreUsuario());
		}
		System.out.println();

		// Eliminar el primer usuario
		ur.deleteById(1L);
		System.out.println("Usuario 1 eliminado");

		// Muestra la lista de usuarios restantes
		System.out.println("Usuarios restantes:");
		for (Usuario u : ur.findAll()) {
			System.out.println("- " + u.getNombreUsuario());
		}
		System.out.println();
	}
}
