package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.cibertec.backend.models.Prestamista;
import pe.cibertec.backend.models.Usuario;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PrestamistaRepository extends JpaRepository<Prestamista, Integer> {

    Optional<Prestamista> findByCorreo (String correo);

    Optional<Prestamista> findByDocumento(int documento);

    Optional<Prestamista> findByNombre (String nombre);

    Optional<Prestamista> findByUsuario(Usuario usuario);

    @Query("SELECT p FROM Prestamista p WHERE p.jefePrestamista.idJefePrestamista = :idJefePrestamista")
    List<Prestamista> findAllByJefePrestamistaId(int idJefePrestamista);

}
