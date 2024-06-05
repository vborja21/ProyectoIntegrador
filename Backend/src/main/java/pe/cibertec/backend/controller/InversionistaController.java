package pe.cibertec.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.models.Inversionista;
import pe.cibertec.backend.service.InversionistaService;

import java.util.List;

@RestController
@RequestMapping("api/inversionista")
@CrossOrigin(origins = "http://localhost:4200")
public class InversionistaController {

    private static final Logger logger = LoggerFactory.getLogger(InversionistaController.class);

    @Autowired
    private InversionistaService inversionistaService;

    @GetMapping("/todos")
    public ResponseEntity<List<Inversionista>> findAll() {
        logger.info("Solicitud GET recibida: findAll");
        List<Inversionista> inversionistas = inversionistaService.findAll();
        logger.info("Respuesta enviada: {}", inversionistas);
        return new ResponseEntity<>(inversionistas, HttpStatus.OK);
    }

    @GetMapping("/buscarId/{idInversionista}")
    public ResponseEntity<Inversionista> findIdInv(@PathVariable int idInversionista) {
        logger.info("Solicitud GET recibida: findIdInv con idInversionista {}", idInversionista);
        Inversionista inversionista = inversionistaService.findByCode(idInversionista);
        logger.info("Respuesta enviada: {}", inversionista);
        return new ResponseEntity<>(inversionista, HttpStatus.OK);
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<Inversionista> findByName(@PathVariable String nombre) {
        logger.info("Solicitud GET recibida: findByName con nombre {}", nombre);
        Inversionista inversionista = inversionistaService.findByName(nombre);
        logger.info("Respuesta enviada: {}", inversionista);
        return new ResponseEntity<>(inversionista, HttpStatus.OK);
    }

    @GetMapping("/buscarCorreo/{correo}")
    public ResponseEntity<Inversionista> findByEmail(@PathVariable String correo) {
        logger.info("Solicitud GET recibida: findByEmail con correo {}", correo);
        Inversionista inversionista = inversionistaService.findByCorreo(correo);
        logger.info("Respuesta enviada: {}", inversionista);
        return new ResponseEntity<>(inversionista, HttpStatus.OK);
    }

    @PostMapping("/newInversionista")
    public ResponseEntity<Inversionista> addInversionista(@RequestBody Inversionista inversionista) {
        logger.info("Solicitud POST recibida: addInversionista con inversionista {}", inversionista);
        Inversionista addedInversionista = inversionistaService.addInversionista(inversionista);
        logger.info("Respuesta enviada: {}", addedInversionista);
        return new ResponseEntity<>(addedInversionista, HttpStatus.CREATED);
    }

    @PutMapping("/editInversionista")
    public ResponseEntity<Inversionista> editInversionista(@RequestBody Inversionista inversionista) {
        logger.info("Solicitud PUT recibida: editInversionista con inversionista {}", inversionista);
        Inversionista editedInversionista = inversionistaService.editInversionista(inversionista);
        logger.info("Respuesta enviada: {}", editedInversionista);
        return new ResponseEntity<>(editedInversionista, HttpStatus.OK);
    }

    @DeleteMapping("/delInversionista/{idInversionista}")
    public void deleteInversionista(@PathVariable int idInversionista) {
        logger.info("Solicitud DELETE recibida: deleteInversionista con idInversionista {}", idInversionista);
        inversionistaService.deleteInversionista(idInversionista);
        logger.info("Inversionista con id {} eliminado exitosamente", idInversionista);
    }
}
