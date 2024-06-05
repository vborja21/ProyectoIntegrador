package pe.cibertec.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.backend.models.Ubicacion;
import pe.cibertec.backend.repository.UbicacionRepository;

import java.util.List;

@Service
@Slf4j

public class UbicacionServiceImpl implements UbicacionService{

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Override
    public List<Ubicacion> findAll() {

        log.info("Buscando todas las Ubicaciones");
        return ubicacionRepository.findAll();
    }
}
