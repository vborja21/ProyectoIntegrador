package pe.cibertec.backend.validaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserConstraintValidator.class)

public @interface UserConstraint {

    String message() default "El nombre de usuario ya está en uso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
