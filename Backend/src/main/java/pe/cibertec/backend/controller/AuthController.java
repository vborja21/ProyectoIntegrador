package pe.cibertec.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import pe.cibertec.backend.dto.UserRegistrationDto;
import pe.cibertec.backend.models.Permiso;
import pe.cibertec.backend.models.Rol;
import pe.cibertec.backend.models.Usuario;
import pe.cibertec.backend.repository.PermisoRepository;
import pe.cibertec.backend.repository.RolRepository;
import pe.cibertec.backend.repository.UsuarioRepository;
import pe.cibertec.backend.security.JwtTokenProvider;
import pe.cibertec.backend.service.UsuarioService;
import pe.cibertec.backend.validaciones.UserConstraintValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        log.info("Solicitud POST recibida: login");
        String username = credentials.get("username");
        String password = credentials.get("password");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.createToken(authentication);

        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);

        log.info("Respuesta enviada: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        log.info("Solicitud POST recibida: registerUser");
        Usuario usuario = new Usuario();
        usuario.setLogin(registrationDto.getUsername());
        usuario.setClave(passwordEncoder.encode(registrationDto.getPassword()));
        usuario.setEstado(1); // Asumiendo que '1' significa activo
        Usuario savedUsuario = usuarioRepository.save(usuario);

        Optional<Rol> rolOptional = rolRepository.findByDescripcion(registrationDto.getRol());
        if (rolOptional.isPresent()) {
            Rol rol = rolOptional.get();
            Permiso permiso = new Permiso();
            permiso.setUsuario(savedUsuario);
            permiso.setRol(rol);
            permisoRepository.save(permiso);
        } else {
            log.error("Rol inválido: {}", registrationDto.getRol());
            return ResponseEntity.badRequest().body("Rol no válido");
        }

        log.info("Usuario registrado exitosamente: {}", savedUsuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}
