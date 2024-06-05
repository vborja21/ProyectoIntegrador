package pe.cibertec.backend.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.dto.PrestatarioDTO;
import pe.cibertec.backend.dto.PrestatarioUpdateDTO;
import pe.cibertec.backend.exception.EntityNotFoundException;
import pe.cibertec.backend.models.Prestatario;
import pe.cibertec.backend.service.PrestatarioService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/prestatario")
@CrossOrigin(origins = "http://localhost:4200")
public class PrestatarioController {

    @Autowired
    private PrestatarioService prestatarioService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Prestatario>> findAll() {
        log.info("Solicitud GET recibida: findAll");
        List<Prestatario> prestatarios = prestatarioService.findAll();
        log.info("Respuesta enviada: {}", prestatarios);
        return new ResponseEntity<>(prestatarios, HttpStatus.OK);
    }

    @GetMapping("/findAllByPrestamistaId/{idPrestamista}")
    public ResponseEntity<List<Prestatario>> findAllByPrestamistaId(@PathVariable int idPrestamista){
        return new ResponseEntity<>(prestatarioService.findAllByPrestamistaId(idPrestamista), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Prestatario> findById(@PathVariable int id){
        log.info("Solicitud GET recibida: findById con id {}", id);
        Prestatario prestatario = prestatarioService.findById(id);
        log.info("Respuesta enviada: {}", prestatario);
        return new ResponseEntity<>(prestatario, HttpStatus.OK);
    }

    @PostMapping("/prestatario")
    public ResponseEntity<?> createPrestatario(@RequestBody PrestatarioDTO prestatarioDTO) {
        log.info("Solicitud POST recibida: createPrestatario con prestatarioDTO {}", prestatarioDTO);
        try {
            Prestatario createdPrestatario = prestatarioService.add(prestatarioDTO);
            log.info("Respuesta enviada: {}", createdPrestatario);
            return new ResponseEntity<>(createdPrestatario, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear el Prestatario: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/prestatario/{id}")
    public ResponseEntity<?> updatePrestatario(@PathVariable int id, @RequestBody PrestatarioUpdateDTO updateDTO){
        try{
            Prestatario updatePrestatario = prestatarioService.update(id, updateDTO);
            return new ResponseEntity<>(updatePrestatario, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el Prestatario: " + e.getMessage());
        }
    }
}
