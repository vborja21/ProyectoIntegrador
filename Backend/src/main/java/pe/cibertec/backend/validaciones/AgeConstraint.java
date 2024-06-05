package pe.cibertec.backend.validaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeConstraintValidator.class)

public @interface AgeConstraint {

    String message() default "La persona debe tener al menos 18 años de edad.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
