package es.unican.polaflix_pablo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz del repositorio para la entidad CategoriaSeries.
 * Provee metodos CRUD para gestionar las categorias de series en la base de datos.
 */
@Repository
public interface CategoriaSeriesRepository extends JpaRepository<CategoriaSeriesRepository, Long> {}