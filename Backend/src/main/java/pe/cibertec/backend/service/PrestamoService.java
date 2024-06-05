package pe.cibertec.backend.service;

import org.springframework.stereotype.Service;
import pe.cibertec.backend.dto.PrestamoDTO;
import pe.cibertec.backend.models.Inversionista;
import pe.cibertec.backend.models.Prestamo;
import pe.cibertec.backend.models.Rol;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {
    List<Prestamo> findAll();
    Prestamo findById(int id);
    Prestamo agregarPrestamo(PrestamoDTO prestamoDTO);
    Prestamo update(int id, int estado);

    List<Prestamo> obtenerPrestamosPorIdPrestatario(int idPrestatario);

    public List<Prestamo> findPrestamos(Integer prestamistaId, String nombre, LocalDate fechaInicio, LocalDate fechaFin);

}
