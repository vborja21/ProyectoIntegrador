package pe.cibertec.backend.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.Usuario;
import pe.cibertec.backend.repository.UsuarioRepository;

import java.util.List;

@Service
@Validated
@Slf4j

public class UsuarioServiceImpl implements UsuarioService {

    @Autowired

    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        log.info("Buscando todos los Usuarios");

        return (List<Usuario>) usuarioRepository.findAll();

    }

    @Override
    public Usuario findById(int isUsuario) {
        log.info("Buscando Usuario por ID: {}", isUsuario);

        return usuarioRepository.findById(isUsuario).orElseThrow(()
                -> new EntityNotFoundException("Usuario no encontrado con el id " + isUsuario));

    }


    @Override
    public Usuario agregarUsuario(@Valid Usuario usuario) {
        log.info("Agregando nuevo Usuario: {}", usuario);

        if (usuarioRepository.existsByLogin(usuario.getLogin())) {
            throw new IllegalStateException("El username ya está en uso");
        }
        return usuarioRepository.save(usuario);

    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        log.info("Editando Usuario: {}", usuario);

        var UsuarioE = usuarioRepository.findById(usuario.getIsUsuario()).get();
        //UsuarioE.setLogin(usuario.getLogin());
        //UsuarioE.setClave(usuario.getClave());
        UsuarioE.setEstado(usuario.getEstado());

        return usuarioRepository.save(UsuarioE);

    }

    @Override
    public void eliminarUsuario(int isUsuario) {
        log.info("Eliminando Usuario: {}", isUsuario);

        var UsuarioE = usuarioRepository.findById(isUsuario).get();

        usuarioRepository.delete(UsuarioE);

    }

}
