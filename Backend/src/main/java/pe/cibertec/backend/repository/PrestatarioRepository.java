package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.cibertec.backend.models.Prestatario;
import pe.cibertec.backend.models.Usuario;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PrestatarioRepository extends JpaRepository<Prestatario, Integer> {

    Optional<Prestatario> findByUsuario(Usuario usuario);

    Optional<Prestatario> findByDocumento(int documento);

    Optional<Prestatario> findByCorreo(String correo);

    @Query("SELECT p FROM Prestatario p WHERE p.prestamista.idPrestamista = :idPrestamista")
    List<Prestatario> findAllByPrestamistaId(int idPrestamista);

}
