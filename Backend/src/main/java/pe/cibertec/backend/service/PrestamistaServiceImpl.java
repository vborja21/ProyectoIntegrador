package pe.cibertec.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.cibertec.backend.dto.PrestamistaDTO;
import pe.cibertec.backend.dto.PrestamistaUpdateDTO;
import pe.cibertec.backend.exception.CustomDuplicateKeyException;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.*;
import pe.cibertec.backend.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class PrestamistaServiceImpl implements PrestamistaService {

    @Autowired
    private PrestamistaRepository prestamistaRepository;

    @Autowired
    private JefePrestamistaRepository jefePrestamistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Prestamista> findAll() {
        log.info("Buscando todos los prestamistas");

        return (List<Prestamista>) prestamistaRepository.findAll();

    }

    @Override
    public List<Prestamista> findAllByJefePrestamistaId(int idJefePrestamista){
        return prestamistaRepository.findAllByJefePrestamistaId(idJefePrestamista);
    }

    @Override
    public Prestamista findByNombre(String nombre) {
        log.info("Buscando prestamista por nombre: {}", nombre);

        return prestamistaRepository.findByNombre(nombre).orElseThrow(()
        -> new EntityNotFoundException("Prestamista no encontrado con el nombre " + nombre.toString()));

    }

    @Override
    public Prestamista findById(int idPrestamista) {
        log.info("Buscando prestamista por ID: {}", idPrestamista);

        return prestamistaRepository.findById(idPrestamista).orElseThrow(()
                -> new EntityNotFoundException("Prestamista no encontrado con el id " + idPrestamista));

    }

    @Override
    public Prestamista findByCorreo(String correo) {
        log.info("Buscando prestamista por correo: {}", correo);

        return prestamistaRepository.findByCorreo(correo).orElseThrow(()
                -> new EntityNotFoundException("Prestamista no encontrado con el correo " + correo.toString()));

    }

    @Override
    @Transactional
    public Optional<Prestamista> findByUsuario(Usuario usuario) {
        return prestamistaRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional
    public Prestamista agregarPrestamista(PrestamistaDTO prestamistaDTO) throws CustomDuplicateKeyException {
        if(usuarioRepository.findByLogin(prestamistaDTO.getLogin()).isPresent()){
            throw new CustomDuplicateKeyException("El login '" + prestamistaDTO.getLogin() + "' ya está en uso.");
        }
        if(prestamistaRepository.findByDocumento(prestamistaDTO.getDocumento()).isPresent()){
            throw new CustomDuplicateKeyException("El documento '" + prestamistaDTO.getDocumento() + "' ya está registrado.");
        }
        if(prestamistaRepository.findByCorreo(prestamistaDTO.getCorreo()).isPresent()){
            throw new CustomDuplicateKeyException("El correo '" + prestamistaDTO.getCorreo() + "' ya está registrado.");
        }

        log.info("Agregando nuevo prestamista: {}", prestamistaDTO);

        Usuario usuario = new Usuario();
        usuario.setLogin(prestamistaDTO.getLogin());
        usuario.setClave(passwordEncoder.encode(prestamistaDTO.getPassword()));
        usuario.setEstado(1); // Estado activo
        usuario = usuarioRepository.save(usuario);

        Prestamista prestamista = new Prestamista();
        prestamista.setNombre(prestamistaDTO.getNombre());
        prestamista.setApellido(prestamistaDTO.getApellido());
        prestamista.setFechaNacimiento(prestamistaDTO.getFechaNacimiento());
        prestamista.setFechaCreacion(prestamistaDTO.getFechaCreacion());
        prestamista.setCelular(prestamistaDTO.getCelular());
        prestamista.setDocumento(prestamistaDTO.getDocumento());
        prestamista.setCorreo(prestamistaDTO.getCorreo());
        prestamista.setUsuario(usuario);


        if (prestamistaDTO.getIdZona() != null) {
            Zona zona = zonaRepository.findById(prestamistaDTO.getIdZona())
                    .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
            prestamista.setIdZona(zona);
        }

        // Asociar el JefePrestamista si es especificado
        if (prestamistaDTO.getIdJefePrestamista() > 0) { // Asumiendo que idJefePrestamista no será negativo
            JefePrestamista jefePrestamista = jefePrestamistaRepository.findById(prestamistaDTO.getIdJefePrestamista())
                    .orElseThrow(() -> new RuntimeException("Jefe Prestamista no encontrado"));
            prestamista.setJefePrestamista(jefePrestamista);
        }

        prestamista = prestamistaRepository.save(prestamista);

        Rol rol = rolRepository.findByDescripcion(prestamistaDTO.getRolDescripcion())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Permiso permiso = new Permiso();
        permiso.setUsuario(usuario);
        permiso.setRol(rol);
        permisoRepository.save(permiso);

        return prestamista;

    }

    @Override
    public Prestamista actualizarPrestamista(int id, PrestamistaUpdateDTO updateDTO) {
        // Capturar prestamista
        Prestamista prestamista = prestamistaRepository.findById(id).orElseThrow(() -> new RuntimeException("Prestamista no encontrado con el id " + id));
        log.info("Editando Prestamista: {}", prestamista);


        // Actualizar campos
        prestamista.setNombre(updateDTO.getNombre());
        prestamista.setApellido(updateDTO.getApellido());
        prestamista.setFechaNacimiento(updateDTO.getFechaNacimiento());
        prestamista.setDocumento(updateDTO.getDocumento());
        prestamista.setCelular(updateDTO.getCelular());
        prestamista.setCorreo(updateDTO.getCorreo());

        // guardar cambios
        return prestamistaRepository.save(prestamista);

    }

    @Override
    public void eliminarPrestamista(int idPrestamista) {
        log.info("Eliminando prestamista por ID: {}", idPrestamista);

        var prestamistaE = prestamistaRepository.findById(idPrestamista).get();

        prestamistaRepository.delete(prestamistaE);


    }
}
