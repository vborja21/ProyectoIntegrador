package pe.cibertec.backend.validaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelfConstraintValidator.class)
public @interface TelfConstraint {

    // Mensaje que se mostrara si no se cumple la validacion
    String message() default "El numero de telefono debe ser de 9 digitos y";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
