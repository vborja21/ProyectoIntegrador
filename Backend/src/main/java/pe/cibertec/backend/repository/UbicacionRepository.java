package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.Ubicacion;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
    Ubicacion findByNombre(String nombre);
}
