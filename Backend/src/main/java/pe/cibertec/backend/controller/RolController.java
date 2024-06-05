package pe.cibertec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.models.Rol;
import pe.cibertec.backend.service.RolService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rol")
@CrossOrigin(origins = "http://localhost:4200")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Rol>> findAll() {
        log.info("Solicitud GET recibida: findAll");
        List<Rol> roles = rolService.findAll();
        log.info("Respuesta enviada: {}", roles);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/findById/{idRol}")
    public ResponseEntity<Rol> findById(@PathVariable int idRol) {
        log.info("Solicitud GET recibida: findById, idRol={}", idRol);
        Rol rol = rolService.findById(idRol);
        log.info("Respuesta enviada: {}", rol);
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @GetMapping("/findByDescripcion/{descripcion}")
    public ResponseEntity<Rol> findByDescripcion(@PathVariable String descripcion) {
        log.info("Solicitud GET recibida: findByDescripcion, descripcion={}", descripcion);
        Rol rol = rolService.findByDescripcion(descripcion);
        log.info("Respuesta enviada: {}", rol);
        return new ResponseEntity<>(rol, HttpStatus.OK);
    }

    @PostMapping("/agregarRol")
    public ResponseEntity<Rol> agregarRol(@RequestBody Rol rol) {
        log.info("Solicitud POST recibida: agregarRol, rol={}", rol);
        Rol nuevoRol = rolService.agregarRol(rol);
        log.info("Respuesta enviada: {}", nuevoRol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @PutMapping("/actualizarRol")
    public ResponseEntity<Rol> actualizarRol(@RequestBody Rol rol) {
        log.info("Solicitud PUT recibida: actualizarRol, rol={}", rol);
        Rol rolActualizado = rolService.actualizarRol(rol);
        log.info("Respuesta enviada: {}", rolActualizado);
        return new ResponseEntity<>(rolActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarRol/{idRol}")
    public ResponseEntity<String> eliminarRol(@PathVariable int idRol) {
        log.info("Solicitud DELETE recibida: eliminarRol, idRol={}", idRol);
        rolService.eliminarRol(idRol);
        log.info("Respuesta enviada: Rol eliminado correctamente");
        return new ResponseEntity<>("Rol eliminado correctamente", HttpStatus.OK);
    }
}
