package pe.cibertec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.dto.JefePrestamistaDTO;
import pe.cibertec.backend.dto.JefePrestamistaUpdateDTO;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.JefePrestamista;
import lombok.extern.slf4j.Slf4j;
import pe.cibertec.backend.service.JefePrestamistaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/jefePrestamista")
@CrossOrigin(origins = "http://localhost:4200")
public class JefePrestamistaController {

    @Autowired
    private JefePrestamistaService jefePrestamistaService;

    @GetMapping("/findByAll")
    @PreAuthorize("hasAuthority('Inversionista')")
    public ResponseEntity<List<JefePrestamista>> findAll() {
        log.info("Solicitud GET recibida: findAll");
        List<JefePrestamista> jefesPrestamista = jefePrestamistaService.findAll();
        log.info("Respuesta enviada: {}", jefesPrestamista);
        return new ResponseEntity<>(jefesPrestamista, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<JefePrestamista> findById(@PathVariable int id) {
        log.info("Solicitud GET recibida: findById con id {}", id);
        JefePrestamista jefePrestamista = jefePrestamistaService.findById(id);
        log.info("Respuesta enviada: {}", jefePrestamista);
        return new ResponseEntity<>(jefePrestamista, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<JefePrestamista> findByName(@PathVariable String name) {
        log.info("Solicitud GET recibida: findByName con name {}", name);
        JefePrestamista jefePrestamista = jefePrestamistaService.findByName(name);
        log.info("Respuesta enviada: {}", jefePrestamista);
        return new ResponseEntity<>(jefePrestamista, HttpStatus.OK);
    }

    @PostMapping("/jefePrestamistas")
    public ResponseEntity<?> createJefePrestamista(@RequestBody JefePrestamistaDTO jefePrestamistaDTO) {
        log.info("Solicitud POST recibida: createJefePrestamista con jefePrestamistaDTO {}", jefePrestamistaDTO);
        try {
            JefePrestamista createdJefePrestamista = jefePrestamistaService.add(jefePrestamistaDTO);
            log.info("Respuesta enviada: {}", createdJefePrestamista);
            return new ResponseEntity<>(createdJefePrestamista, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear el Jefe Prestamista: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/jefePrestamistas/{id}")
    public ResponseEntity<?> updateJefePrestamista(@PathVariable int id, @RequestBody JefePrestamistaUpdateDTO updateDTO) {
        log.info("Solicitud PUT recibida: updateJefePrestamista con id {} y jefePrestamistaDTO {}", id, updateDTO);
        try {
            JefePrestamista updatedJefePrestamista = jefePrestamistaService.update(id, updateDTO);
            log.info("Respuesta enviada: {}", updatedJefePrestamista);
            return new ResponseEntity<>(updatedJefePrestamista, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error("Entidad no encontrada al actualizar el Jefe Prestamista con id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al actualizar el Jefe Prestamista: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el Jefe Prestamista: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        log.info("Solicitud DELETE recibida: delete con id {}", id);
        jefePrestamistaService.delete(id);
        log.info("Jefe Prestamista con id {} eliminado exitosamente", id);
    }
}
