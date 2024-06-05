package pe.cibertec.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.cibertec.backend.dto.JefePrestamistaDTO;
import pe.cibertec.backend.dto.PrestatarioDTO;
import pe.cibertec.backend.dto.PrestatarioUpdateDTO;
import pe.cibertec.backend.exception.CustomDuplicateKeyException;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.*;
import pe.cibertec.backend.repository.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PrestatarioServiceImpl implements PrestatarioService {

    @Autowired
    private PrestatarioRepository prestatarioRepository;

    @Autowired
    private PrestamistaRepository prestamistaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Prestatario> findAll() {
        log.info("Buscando todos los prestatarios");

        return prestatarioRepository.findAll();
    }

    @Override
    public List<Prestatario> findAllByPrestamistaId(int idPrestamista){
        return prestatarioRepository.findAllByPrestamistaId(idPrestamista);
    }

    @Override
    public Prestatario findById(int id) {
        log.info("Buscando Prestatario por ID: {}", id);

        return prestatarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Optional<Prestatario> findByUsuario(Usuario usuario){
        return prestatarioRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional
    public Prestatario add(PrestatarioDTO prestatarioDTO) throws CustomDuplicateKeyException {

        if (usuarioRepository.findByLogin(prestatarioDTO.getLogin()).isPresent()) {
            throw new CustomDuplicateKeyException("El login '" + prestatarioDTO.getLogin() + "' ya está en uso.");
        }
        if (prestatarioRepository.findByDocumento(prestatarioDTO.getDocumento()).isPresent()) {
            throw new CustomDuplicateKeyException("El documento '" + prestatarioDTO.getDocumento() + "' ya está registrado.");
        }
        if (prestatarioRepository.findByCorreo(prestatarioDTO.getCorreo()).isPresent()) {
            throw new CustomDuplicateKeyException("El correo '" + prestatarioDTO.getCorreo() + "' ya está registrado.");
        }

        log.info("Buscando Prestatario: {}", prestatarioDTO);

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setLogin(prestatarioDTO.getLogin());
        usuario.setClave(passwordEncoder.encode(prestatarioDTO.getPassword()));
        usuario.setEstado(1); // Estado activo
        usuario = usuarioRepository.save(usuario);

        // Crear Prestatario y asignar Usuario

        Prestatario prestatario = new Prestatario();
        prestatario.setNombre(prestatarioDTO.getNombre());
        prestatario.setApellido(prestatarioDTO.getApellido());
        prestatario.setFechaNacimiento(prestatarioDTO.getFechaNacimiento());
        prestatario.setFechaCreacion(prestatarioDTO.getFechaCreacion());
        prestatario.setCorreo(prestatarioDTO.getCorreo());
        prestatario.setDocumento(prestatarioDTO.getDocumento());
        prestatario.setCelular(prestatarioDTO.getCelular());
        prestatario.setUsuario(usuario);

        if(prestatarioDTO.getIdPrestamista() > 0) {
            Prestamista prestamista = prestamistaRepository.findById(prestatarioDTO.getIdPrestamista())
                    .orElseThrow(() -> new EntityNotFoundException("Prestamista no encontrado con el id " + prestatarioDTO.getIdPrestamista()));
            prestatario.setPrestamista(prestamista);
        }

        prestatario = prestatarioRepository.save(prestatario);

        // Asignar permiso
        Rol rol = rolRepository.findByDescripcion(prestatarioDTO.getRolDescripcion())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Permiso permiso = new Permiso();
        permiso.setUsuario(usuario);
        permiso.setRol(rol);
        permisoRepository.save(permiso);

        return prestatario;
    }


    @Override
    public Prestatario update(int id, PrestatarioUpdateDTO updateDTO) {
        // Capturar Prestatario
        Prestatario prestatario = prestatarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Prestatario no encontrado con el id " + id));
        log.info("Editando Prestatario: {}", prestatario);

        // Actualizar campos
        prestatario.setNombre(updateDTO.getNombre());
        prestatario.setApellido(updateDTO.getApellido());
        prestatario.setFechaNacimiento(updateDTO.getFechaNacimiento());
        prestatario.setCelular(updateDTO.getCelular());
        prestatario.setDocumento(updateDTO.getDocumento());
        prestatario.setCorreo(updateDTO.getCorreo());

        // guardar cambios
        return prestatarioRepository.save(prestatario);

    }

    @Override
    public void delete(int idPrestatario) {
        log.info("Eliminando Prestatario: {}", idPrestatario);

        var prestatarioF = prestatarioRepository.findById(idPrestatario).get();

        prestatarioRepository.delete(prestatarioF);


    }


}
