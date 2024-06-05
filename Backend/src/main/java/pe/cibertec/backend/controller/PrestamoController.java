package pe.cibertec.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.dto.PrestamoDTO;
import pe.cibertec.backend.dto.PrestatarioUpdateDTO;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.Prestamo;
import pe.cibertec.backend.models.Prestatario;
import pe.cibertec.backend.service.PrestamoService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/prestamos")
@CrossOrigin(origins = "http://localhost:4200")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Prestamo>> findAll() {
        log.info("Solicitud GET recibida: findAll");
        List<Prestamo> prestamo = prestamoService.findAll();
        log.info("Respuesta enviada: {}", prestamo);
         return new ResponseEntity<>(prestamo, HttpStatus.OK);
    }

    @GetMapping("/findByIdPrestamo/{idPrestamo}")
    public ResponseEntity<Prestamo> findByIdPrestamo(@PathVariable int idPrestamo) {
        return new ResponseEntity<>(prestamoService.findById(idPrestamo), HttpStatus.OK);
    }


    @PostMapping("/crearPrestamo")
    public ResponseEntity<?> createPrestamo(@RequestBody PrestamoDTO prestamoDTO) {
        log.info("Solicitud POST recibida: createPrestamo con PrestamoDTO {}", prestamoDTO);
        try {
            Prestamo createdPrestamo = prestamoService.agregarPrestamo(prestamoDTO);
            log.info("Respuesta enviada: {}", createdPrestamo);
            return new ResponseEntity<>(createdPrestamo, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear el Prestamo: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/prestamo/{id}/{estado}")
    public ResponseEntity<?> updatePrestamo(@PathVariable int id, @PathVariable int estado) {
        try{
            Prestamo updatePrestamo = prestamoService.update(id, estado);
            return new ResponseEntity<>(updatePrestamo, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el Prestamo: " + e.getMessage());
        }
    }

    @GetMapping("/findAll/{idPrestatario}")
    public ResponseEntity<List<Prestamo>> obtenerPrestamosPorIdPrestatario(@PathVariable int idPrestatario) {
        List<Prestamo> prestamos = prestamoService.obtenerPrestamosPorIdPrestatario(idPrestatario);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @GetMapping("/findPrestamosPorFiltro/{prestamistaId}")
    public ResponseEntity<List<Prestamo>> getPrestamos(@PathVariable Integer prestamistaId,
                                                       @RequestParam(required = false) String nombre,
                                                       @RequestParam(required = false) String fechaInicio,
                                                       @RequestParam(required = false) String fechaFin) {
        LocalDate start = fechaInicio != null ? LocalDate.parse(fechaInicio) : null;
        LocalDate end = fechaFin != null ? LocalDate.parse(fechaFin) : null;
        List<Prestamo> prestamos = prestamoService.findPrestamos(prestamistaId, nombre, start, end);
        return ResponseEntity.ok(prestamos);
    }
}
