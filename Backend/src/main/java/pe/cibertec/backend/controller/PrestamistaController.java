package pe.cibertec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.dto.PrestamistaDTO;
import pe.cibertec.backend.dto.PrestamistaUpdateDTO;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.Prestamista;
import pe.cibertec.backend.service.PrestamistaService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/prestamista")
@CrossOrigin(origins = "http://localhost:4200")
public class PrestamistaController {

    @Autowired
    private PrestamistaService prestamistaService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Prestamista>> findAll() {
        log.info("Solicitud GET recibida: findAll");
        List<Prestamista> prestamistas = prestamistaService.findAll();
        log.info("Respuesta enviada: {}", prestamistas);
        return new ResponseEntity<>(prestamistas, HttpStatus.OK);
    }

    @GetMapping("/findAllByJefePrestamistaId/{idJefePrestamista}")
    public ResponseEntity<List<Prestamista>> findAllByJefePrestamistaId(@PathVariable int idJefePrestamista) {
        return new ResponseEntity<>(prestamistaService.findAllByJefePrestamistaId(idJefePrestamista), HttpStatus.OK);
    }

    @GetMapping("/findById/{idPrestamista}")
    public ResponseEntity<Prestamista> findById(@PathVariable int idPrestamista) {
        return new ResponseEntity<>(prestamistaService.findById(idPrestamista), HttpStatus.OK);
    }

    @GetMapping("/findByNombre/{nombre}")
    public ResponseEntity<Prestamista> findByNombre(@PathVariable String nombre) {
        log.info("Solicitud GET recibida: findByNombre con nombre {}", nombre);
        Prestamista prestamista = prestamistaService.findByNombre(nombre);
        log.info("Respuesta enviada: {}", prestamista);
        return new ResponseEntity<>(prestamista, HttpStatus.OK);
    }

    @GetMapping("/findByCorreo/{correo}")
    public ResponseEntity<Prestamista> findByCorreo(@PathVariable String correo) {
        log.info("Solicitud GET recibida: findByCorreo con correo {}", correo);
        Prestamista prestamista = prestamistaService.findByCorreo(correo);
        log.info("Respuesta enviada: {}", prestamista);
        return new ResponseEntity<>(prestamista, HttpStatus.OK);
    }

    @PostMapping("/prestamista")
    public ResponseEntity<?> createPrestamista(@RequestBody PrestamistaDTO prestamistaDTO) {
        log.info("Solicitud POST recibida: createPrestamista con prestamistaDTO {}", prestamistaDTO);
        try {
            Prestamista createdPrestamista = prestamistaService.agregarPrestamista(prestamistaDTO);
            log.info("Respuesta enviada: {}", createdPrestamista);
            return new ResponseEntity<>(createdPrestamista, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear el Prestamista: {}", e.getMessage());
            throw e;
        }
    }


    @PutMapping("/actualizarPrestamista/{id}")
    public ResponseEntity<?> actualizarPrestamista (@PathVariable int id,@RequestBody PrestamistaUpdateDTO updateDTO) {
        try {
            Prestamista updatePrestamista = prestamistaService.actualizarPrestamista(id, updateDTO);
            return new ResponseEntity<>(updatePrestamista, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el Jefe Prestamista: " + e.getMessage());
        }

    }

    @DeleteMapping("/eliminarPrestamista/{idPrestamista}")
    public void eliminarPrestamista(@PathVariable int idPrestamista) {
        log.info("Solicitud DELETE recibida: eliminarPrestamista con id {}", idPrestamista);
        prestamistaService.eliminarPrestamista(idPrestamista);
        log.info("Prestamista con id {} eliminado exitosamente", idPrestamista);
    }
}
