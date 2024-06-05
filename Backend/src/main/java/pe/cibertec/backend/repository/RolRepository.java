package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByDescripcion(String descripcion);
}
