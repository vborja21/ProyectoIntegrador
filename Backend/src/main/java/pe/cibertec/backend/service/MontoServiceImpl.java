package pe.cibertec.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.backend.models.Monto;
import pe.cibertec.backend.repository.MontoRepository;

import java.util.List;

@Service
public class MontoServiceImpl implements MontoService{

    @Autowired
    private MontoRepository montoRepository;

    @Override
    public List<Monto> findAll() {
        return montoRepository.findAll();
    }

    @Override
    public Monto findById(int id) {
        return montoRepository.findById(id).orElse(null);
    }
}
