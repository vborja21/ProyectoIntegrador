package pe.cibertec.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.cibertec.backend.models.Prestamo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    Prestamo findById(int id);
    List<Prestamo> findByPrestatario_IdPrestatario(int idPrestatario);

    @Query("SELECT p FROM Prestamo p WHERE p.prestatario.prestamista.idPrestamista = :prestamistaId AND " +
            "(:nombre IS NULL OR LOWER(p.prestatario.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:fechaInicio IS NULL OR p.fechaInicio >= :fechaInicio) AND (:fechaFin IS NULL OR p.fechaFin <= :fechaFin)")
    List<Prestamo> findByPrestamistaAndNombreAndFechaInicioFin(@Param("prestamistaId") int prestamistaId,
                                                               @Param("nombre") String nombre,
                                                               @Param("fechaInicio") LocalDate fechaInicio,
                                                               @Param("fechaFin") LocalDate fechaFin);

}