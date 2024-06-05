package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.Permiso;
import pe.cibertec.backend.models.Usuario;

import java.util.Set;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    Set<Permiso> findByUsuario(Usuario usuario);
}
