package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.JefePrestamista;
import pe.cibertec.backend.models.Ubicacion;
import pe.cibertec.backend.models.Usuario;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface JefePrestamistaRepository extends JpaRepository<JefePrestamista,Integer> {
    Optional<JefePrestamista>  findByNombre(String name);
    Optional<JefePrestamista>  findByUsuario(Usuario usuario);

    Optional<JefePrestamista> findByUbicacion(Ubicacion ubicacion);
    Optional<JefePrestamista> findByDocumento(int documento);
    Optional<JefePrestamista> findByCorreo(String correo);
}
