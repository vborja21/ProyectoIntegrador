package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.Cuota;

public interface CuotaRepository extends JpaRepository<Cuota, Integer> {
}
