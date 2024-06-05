package pe.cibertec.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.cibertec.backend.dto.JefePrestamistaDTO;
import pe.cibertec.backend.dto.JefePrestamistaUpdateDTO;
import pe.cibertec.backend.exception.CustomDuplicateKeyException;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.*;
import pe.cibertec.backend.repository.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class JefePrestamistaServiceImpl implements JefePrestamistaService{

    @Autowired
    private JefePrestamistaRepository jefePrestamistaRepository;

    // Para el POST
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PermisoRepository permisoRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    UbicacionRepository ubicacionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<JefePrestamista> findAll() {
        log.info("Buscando todos los jefe prestamistas");

        return (List<JefePrestamista>) jefePrestamistaRepository.findAll();
    }

    @Override
    public JefePrestamista findById(int id) {
        log.info("Buscando Jefe prestamista por ID: {}", id);
        return jefePrestamistaRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("No se encontro a un Jefe prestamista con el siguiente ID: " + id));
    }

    @Override
    public JefePrestamista findByName(String name) {
        log.info("Buscando Jefe prestamista por nombre: {}", name);

        return jefePrestamistaRepository.findByNombre(name).orElseThrow(()
                -> new EntityNotFoundException("No se encontro a un Jefe prestamista con el siguiente nombre: " + name.toString()));
    }

    @Override
    @Transactional
    public Optional<JefePrestamista> findByUsuario(Usuario usuario) {
        return jefePrestamistaRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional
    public JefePrestamista add(JefePrestamistaDTO jefePrestamistaDTO) throws CustomDuplicateKeyException {
            if(usuarioRepository.findByLogin(jefePrestamistaDTO.getLogin()).isPresent()){
                throw new CustomDuplicateKeyException("El login '" + jefePrestamistaDTO.getLogin() + "' ya está en uso.");
            }
            if(jefePrestamistaRepository.findByDocumento(jefePrestamistaDTO.getDocumento()).isPresent()){
                throw new CustomDuplicateKeyException("El documento '" + jefePrestamistaDTO.getDocumento() + "' ya está registrado.");
            }
            if(jefePrestamistaRepository.findByCorreo(jefePrestamistaDTO.getCorreo()).isPresent()){
                throw new CustomDuplicateKeyException("El correo '" + jefePrestamistaDTO.getCorreo() + "' ya está registrado.");
            }

            log.info("Agregando nuevo Jefe prestamista: {}", jefePrestamistaDTO);
            // Crear usuario
            Usuario usuario = new Usuario();
            usuario.setLogin(jefePrestamistaDTO.getLogin());
            usuario.setClave(passwordEncoder.encode(jefePrestamistaDTO.getPassword()));
            usuario.setEstado(1);  // Estado activo
            usuario = usuarioRepository.save(usuario);

            // Crear JefePrestamista y asignar Usuario
            JefePrestamista jefePrestamista = new JefePrestamista();
            jefePrestamista.setNombre(jefePrestamistaDTO.getNombre());
            jefePrestamista.setApellido(jefePrestamistaDTO.getApellido());
            jefePrestamista.setFechaNacimiento(jefePrestamistaDTO.getFechaNacimiento());
            jefePrestamista.setFechaCreacion(jefePrestamistaDTO.getFechaCreacion());
            jefePrestamista.setDocumento(jefePrestamistaDTO.getDocumento());
            jefePrestamista.setCelular(jefePrestamistaDTO.getCelular());
            jefePrestamista.setCorreo(jefePrestamistaDTO.getCorreo());

            // Buscar y asignar Ubicacion
            Ubicacion ubicacion = ubicacionRepository.findById(jefePrestamistaDTO.getIdUbicacion())
                    .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));
            if(jefePrestamistaRepository.findByUbicacion(ubicacion).isPresent()) {
                throw new CustomDuplicateKeyException("Ya existe un Jefe Prestamista en la ubicación '" + ubicacion.getNombre() + "'.");
            }
            jefePrestamista.setUbicacion(ubicacion);

            jefePrestamista.setUsuario(usuario);
            jefePrestamista = jefePrestamistaRepository.save(jefePrestamista);

            // Asignar permiso
            Rol rol = rolRepository.findByDescripcion(jefePrestamistaDTO.getRolDescripcion())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            Permiso permiso = new Permiso();
            permiso.setUsuario(usuario);
            permiso.setRol(rol);
            permisoRepository.save(permiso);

            return jefePrestamista;

    }
    //@Override
    //public JefePrestamista add(JefePrestamista jefePrestamista) {
    //    return jefePrestamistaRepository.save(jefePrestamista);
    //}

    @Override
    @Transactional
    public JefePrestamista update(int id, JefePrestamistaUpdateDTO updateDTO)  {


        JefePrestamista jefe = jefePrestamistaRepository.findById(id).orElseThrow(() -> new RuntimeException("Jefe Prestamista no encontrado"));
        log.info("Editando Jefe Prestamista: {}", updateDTO);

        // Actualizando campos
        jefe.setNombre(updateDTO.getNombre());
        jefe.setApellido(updateDTO.getApellido());
        jefe.setFechaNacimiento(updateDTO.getFechaNacimiento());
        jefe.setCorreo(updateDTO.getCorreo());
        jefe.setDocumento(updateDTO.getDocumento());
        jefe.setCelular(updateDTO.getCelular());

        // Guardar los cambios
        return jefePrestamistaRepository.save(jefe);
    }

    @Override
    public void delete(int id) {
        log.info("Eliminando jefe prestamista con id: {}", id);

        var jefe = jefePrestamistaRepository.findById(id).get();
        jefePrestamistaRepository.delete(jefe);
    }
}
