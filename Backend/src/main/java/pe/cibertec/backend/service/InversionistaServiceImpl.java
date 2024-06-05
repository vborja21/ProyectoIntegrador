package pe.cibertec.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.Inversionista;
import pe.cibertec.backend.models.Usuario;
import pe.cibertec.backend.repository.InversionistaRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InversionistaServiceImpl implements InversionistaService{

    @Autowired
    private InversionistaRepository inversionistaRepository;
    @Override
    public List<Inversionista> findAll() {
        log.info("Buscando todos los inversionistas");
        return (List<Inversionista>) inversionistaRepository.findAll();

    }

    @Override
    public Inversionista findByCode(int idInversionista) {
        log.info("Buscando inversionista por código: {}", idInversionista);
        return inversionistaRepository.findById(idInversionista).orElseThrow(()
                -> new EntityNotFoundException("Id del Inversionista no encontrado" + idInversionista));

    }

    @Override
    public Inversionista findByName(String nombre) {
        log.info("Buscando inversionista por nombre: {}", nombre);

        return inversionistaRepository.findByNombre(nombre).orElseThrow(()
                -> new EntityNotFoundException("Nombre de inversionista no encontrado" + nombre.toString()));

    }

    @Override
    public Inversionista findByCorreo(String correo) {
        log.info("Buscando inversionista por correo: {}", correo);

        return inversionistaRepository.findByCorreo(correo).orElseThrow(()
                -> new EntityNotFoundException("Correo de inversionista no encontrado" + correo.toString()));

    }

    @Override
    @Transactional
    public Optional<Inversionista> findByUsuario(Usuario usuario){
        return inversionistaRepository.findByUsuario(usuario);
    }

    @Override
    public Inversionista addInversionista(Inversionista inversionista) {
        log.info("Agregando nuevo inversionista: {}", inversionista);

        return inversionistaRepository.save(inversionista);

    }

    @Override
    public Inversionista editInversionista(Inversionista inversionista) {
        log.info("Editando inversionista: {}", inversionista);

        var editInver = inversionistaRepository.findById(inversionista.getIdInversionista()).get();
        editInver.setNombre(inversionista.getNombre());
        editInver.setApellido(inversionista.getApellido());
        editInver.setFechaNacimiento(inversionista.getFechaNacimiento());
        editInver.setCelular(inversionista.getCelular());
        editInver.setDocumento(inversionista.getDocumento());
        editInver.setCorreo(inversionista.getCorreo());

        return inversionistaRepository.save(editInver);

    }

    @Override
    public void deleteInversionista(int idInversionista) {
        log.info("Eliminando inversionista con id: {}", idInversionista);

        var delInver = inversionistaRepository.findById(idInversionista).get();
        inversionistaRepository.delete(delInver);

    }

}
