package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.Monto;

import java.util.Optional;

public interface MontoRepository extends JpaRepository<Monto, Integer> {

    Optional<Monto> findById(int idMonto);
}
