package es.unican.polaflix_pablo.repositories;

import es.unican.polaflix_pablo.domain.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz del repositorio para la entidad Serie.
 * Provee metodos CRUD para gestionar las series en la base de datos.
 */
@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {}