package pe.cibertec.backend.validaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.cibertec.backend.repository.UsuarioRepository;
import pe.cibertec.backend.service.UsuarioService;

@Component
public class UserConstraintValidator implements ConstraintValidator<UserConstraint, String> {

    @Autowired

    private UsuarioRepository usuarioRepository;

    public UserConstraintValidator() {

    }

    @Override
    public void initialize(UserConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username , ConstraintValidatorContext context) {

      return username != null && !usuarioRepository.existsByLogin(username);

    }
 }