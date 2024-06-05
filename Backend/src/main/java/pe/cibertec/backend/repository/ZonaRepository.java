package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.cibertec.backend.models.Zona;

import java.util.List;

public interface ZonaRepository extends JpaRepository<Zona, Integer> {

    @Query("SELECT z FROM Zona z WHERE z.ubicacion.idUbicacion = :idUbicacion")
    List<Zona> findByUbicacionId(int idUbicacion);

    List<Zona> findAllByUbicacion_IdUbicacion(int idUbicacion);

}
