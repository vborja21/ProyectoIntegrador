package pe.cibertec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.backend.models.Usuario;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByLogin(String login);
    boolean existsByLogin(String login);
}
