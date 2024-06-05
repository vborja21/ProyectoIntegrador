package pe.cibertec.backend.validaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DniConstraintValidator.class)
public @interface DniConstraint {

    // Mensaje que se mostrara si no se cumple la validacion
    String message() default "El DNI debe ser de 8 digitos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
