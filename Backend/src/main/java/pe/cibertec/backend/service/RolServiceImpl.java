package pe.cibertec.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.Rol;
import pe.cibertec.backend.repository.RolRepository;

import java.util.List;

@Service
@Slf4j

public class RolServiceImpl implements RolService {

    @Autowired

    private RolRepository rolRepository;

    @Override
    public List<Rol> findAll() {
        log.info("Buscando todos los roles");

        return (List<Rol>) rolRepository.findAll();

    }

    @Override
    public Rol findByDescripcion(String descripcion) {
        log.info("Buscando Rol por Descripcion: {}", descripcion);

        return rolRepository.findByDescripcion(descripcion).orElseThrow(()
        -> new EntityNotFoundException("Rol no encontrado con la descripcion " + descripcion.toString()));

    }

    @Override
    public Rol findById(int idRol) {
        log.info("Buscando Rol por ID: {}", idRol);

        return rolRepository.findById(idRol).orElseThrow(()
        -> new EntityNotFoundException("Rol no encontrado con el Id " + idRol));

    }

    @Override
    public Rol agregarRol(Rol rol) {
        log.info("Agregando nuevo Rol: {}", rol);

        return  rolRepository.save(rol);

    }

    @Override
    public Rol actualizarRol(Rol rol) {
        log.info("Editando Rol: {}", rol);

        var RolE = rolRepository.findById(rol.getIdRol()).get();
        RolE.setDescripcion(rol.getDescripcion());

        return rolRepository.save(RolE);

    }

    @Override
    public void eliminarRol(int idRol) {
        log.info("Eliminando Rol: {}", idRol);

        var RolE = rolRepository.findById(idRol).get();

        rolRepository.delete(RolE);


    }
}
