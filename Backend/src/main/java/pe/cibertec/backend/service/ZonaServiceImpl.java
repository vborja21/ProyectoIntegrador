package pe.cibertec.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.backend.models.JefePrestamista;
import pe.cibertec.backend.models.Zona;
import pe.cibertec.backend.repository.JefePrestamistaRepository;
import pe.cibertec.backend.repository.ZonaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ZonaServiceImpl implements ZonaService {

    private static final Logger log = LoggerFactory.getLogger(ZonaServiceImpl.class);

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private JefePrestamistaRepository jefePrestamistaRepository;

    @Override
    public List<Zona> listarZonas() {
        log.info("Buscando todas las Zonas");
        return zonaRepository.findAll();
    }

    @Override
    public List<Zona> findZonasByJefePrestamistaId(int idJefePrestamista){
        Optional<JefePrestamista> jefe = jefePrestamistaRepository.findById(idJefePrestamista);
        return jefe.map(j -> zonaRepository.findAllByUbicacion_IdUbicacion(j.getUbicacion().getIdUbicacion()))
                .orElseThrow(() -> new RuntimeException("Jefe Prestamista no encontrado o sin ubicación asignada"));

    }
}
