package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.Inversionista;
import pe.cibertec.backend.models.Usuario;

import java.util.Optional;

public interface InversionistaRepository extends JpaRepository<Inversionista, Integer> {
    Optional<Inversionista> findByNombre(String nombre);

    Optional<Inversionista> findByCorreo(String correo);

    Optional<Inversionista> findByUsuario(Usuario usuario);
}
