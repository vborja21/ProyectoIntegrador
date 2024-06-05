package pe.cibertec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.models.Usuario;
import pe.cibertec.backend.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Usuario>> findAll() {
        log.info("Solicitud GET recibida: findAll");
        List<Usuario> usuarios = usuarioService.findAll();
        log.info("Respuesta enviada: {}", usuarios);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/findById/{idUsuario}")
    public ResponseEntity<Usuario> findById(@PathVariable int idUsuario) {
        log.info("Solicitud GET recibida: findById con id {}", idUsuario);
        Usuario usuario = usuarioService.findById(idUsuario);
        log.info("Respuesta enviada: {}", usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/agregarUsuario")
    public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario) {
        log.info("Solicitud POST recibida: agregarUsuario con usuario {}", usuario);
        Usuario createdUsuario = usuarioService.agregarUsuario(usuario);
        log.info("Respuesta enviada: {}", createdUsuario);
        return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/actualizarUsuario")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        log.info("Solicitud PUT recibida: actualizarUsuario con usuario {}", usuario);
        Usuario updatedUsuario = usuarioService.actualizarUsuario(usuario);
        log.info("Respuesta enviada: {}", updatedUsuario);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarUsuario/{idUsuario}")
    public void eliminarUsuario(@PathVariable int idUsuario) {
        log.info("Solicitud DELETE recibida: eliminarUsuario con id {}", idUsuario);
        usuarioService.eliminarUsuario(idUsuario);
        log.info("Usuario con id {} eliminado exitosamente", idUsuario);
    }
}
