package es.unican.polaflix_pablo.repositories;

import es.unican.polaflix_pablo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz del repositorio para la entidad Usuario.
 * Provee metodos CRUD para gestionar los usuarios en la base de datos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuario(String nombreUsuario);
}