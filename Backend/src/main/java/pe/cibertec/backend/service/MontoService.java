package pe.cibertec.backend.service;

import pe.cibertec.backend.models.Monto;

import java.util.List;

public interface MontoService {
    List<Monto> findAll();
    Monto findById(int id);
}
