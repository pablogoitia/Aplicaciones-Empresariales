package es.unican.polaflix_pablo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import es.unican.polaflix_pablo.domain.*;
import es.unican.polaflix_pablo.repositories.*;

@Component
public class AppFeeder implements CommandLineRunner {
	
	@Autowired
	protected UsuarioRepository ur;
	@Autowired
	protected SerieRepository sr;
	@Autowired
	protected CategoriaSeriesRepository csr;

	
	@Override
	public void run(String... args) throws Exception {
		feedUsuarios();
		feedCategoriasSeries();
		feedSeries();
		
		System.out.println("Application feeded");
	}

	private void feedUsuarios() {
		Usuario u1 = new Usuario("pablogoitia", "123456", "ES7921000813610123456789");
		Usuario u2 = new Usuario("johndoe","789123", "ES6000491500051234567892");
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
		Serie s1 = new Serie("Breaking Bad", "Un profesor de quimica se convierte en fabricante de metanfetamina", 
				csr.findById(1L).get(), "Vince Gilligan", "Bryan Cranston, Aaron Paul");
		Serie s2 = new Serie("Game of Thrones", "La lucha por el trono de hierro en los siete reinos de Westeros", 
				csr.findById(2L).get(), "David Benioff, D.B. Weiss", "Emilia Clarke, Kit Harington");
		Serie s3 = new Serie("Stranger Things", "Un grupo de amigos se enfrenta a fuerzas sobrenaturales en su pueblo", 
				csr.findById(3L).get(), "The Duffer Brothers", "Winona Ryder, David Harbour");

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
		Capitulo s1t1c1 = new Capitulo(1, "Pilot", "El piloto de la serie", s1t1);
		Capitulo s1t2c1 = new Capitulo(1, "A Lush Life", "El primer capitulo de la segunda temporada", s1t2);
		s1t1.addCapitulo(s1t1c1);
		s1t2.addCapitulo(s1t2c1);
		Capitulo s2t1c1 = new Capitulo(1, "Winter Is Coming", "El piloto de la serie", s2t1);
		Capitulo s2t2c1 = new Capitulo(1, "The Kingsroad", "El primer capitulo de la segunda temporada", s2t2);
		s2t1.addCapitulo(s2t1c1);
		s2t2.addCapitulo(s2t2c1);
		Capitulo s3t1c1 = new Capitulo(1, "Chapter One: The Vanishing of Will Byers", "El piloto de la serie", s3t1);
		Capitulo s3t2c1 = new Capitulo(1, "Chapter Two: The Weirdo on Maple Street", "El primer capitulo de la segunda temporada", s3t2);
		s3t1.addCapitulo(s3t1c1);
		s3t2.addCapitulo(s3t2c1);

		// Guarda todo
		sr.save(s1);
		sr.save(s2);
		sr.save(s3);
	}
}
