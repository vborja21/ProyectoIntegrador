package pe.cibertec.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.cibertec.backend.dto.PrestamoDTO;
import pe.cibertec.backend.exception.CustomDuplicateKeyException;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.*;
import pe.cibertec.backend.repository.*;
import pe.cibertec.backend.service.PrestamoService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private PrestatarioRepository prestatarioRepository;

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private MontoRepository montoRepository;


    @Override
    public List<Prestamo> findAll() {

        return (List<Prestamo>) prestamoRepository.findAll();

    }

    @Override
    public List<Prestamo> findPrestamos(Integer prestamistaId, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        return prestamoRepository.findByPrestamistaAndNombreAndFechaInicioFin(prestamistaId, nombre, fechaInicio, fechaFin);
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorIdPrestatario(int idPrestatario) {
        return prestamoRepository.findByPrestatario_IdPrestatario(idPrestatario);
    }

    @Override
    public Prestamo findById(int id) {
        return prestamoRepository.findById(id);
    }

    @Override
    public Prestamo agregarPrestamo(PrestamoDTO prestamoDTO) {


        Prestatario prestatario = prestatarioRepository.findById(prestamoDTO.getIdPrestatario()).orElseThrow(() -> new RuntimeException("Prestatario no encontrado con ID :"
                + prestamoDTO.getIdPrestatario()));

        Monto monto = montoRepository.findById(prestamoDTO.getIdMonto())
                .orElseThrow(() -> new RuntimeException("Monto no encontrado con ID: " + prestamoDTO.getIdMonto()));

        Prestamo prestamo = new Prestamo();

        prestamo.setPrestatario(prestatario);
        prestamo.setMonto(monto);
        prestamo.setFechaInicio(prestamoDTO.getFechaInicio());
        prestamo.setFechaFin(prestamoDTO.getFechaFin());
        prestamo.setPagoDiario(prestamoDTO.getPagoDiario());
        prestamo.setEstadoPrestamo(prestamoDTO.getEstadoPrestamo());

        return prestamoRepository.save(prestamo);

    }

    @Override
    public Prestamo update(int id, int estado) {
        Prestamo prestamo = prestamoRepository.findById(id);
        prestamo.setEstadoPrestamo(estado);

        if (estado == 2) {
            // Calcular días laborales entre fechaInicio y fechaFin
            int diasLaborales = contarDiasLaborales(prestamo.getFechaInicio(), prestamo.getFechaFin());

            // Crear y guardar registros en la tabla Cuotas
            LocalDate fechaCuota = prestamo.getFechaInicio();
            LocalDate fechaFinCuotas = prestamo.getFechaFin().plusDays(1); // Se suma 1 para incluir la fecha de fin
            while (fechaCuota.isBefore(fechaFinCuotas)) {
                if (fechaCuota.getDayOfWeek() != DayOfWeek.SATURDAY && fechaCuota.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    Cuota cuota = new Cuota();
                    cuota.setIdPrestamo(id);
                    cuota.setFechaPago(Date.from(fechaCuota.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    cuotaRepository.save(cuota);
                }
                fechaCuota = fechaCuota.plusDays(1);
            }
        }

        return prestamoRepository.save(prestamo);
    }

    private int contarDiasLaborales(LocalDate start, LocalDate end) {
        int workDays = 0;
        LocalDate date = start;
        while (date.isBefore(end) || date.isEqual(end)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workDays++;
            }
            date = date.plusDays(1);
        }
        return workDays;
    }


}