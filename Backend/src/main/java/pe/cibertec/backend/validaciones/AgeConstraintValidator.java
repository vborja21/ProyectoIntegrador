package pe.cibertec.backend.validaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeConstraintValidator implements ConstraintValidator<AgeConstraint, LocalDate> {
    @Override
    public void initialize(AgeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        if (fechaNacimiento == null) {
            return false;
        }

        int age = LocalDate.now().minusYears(18).compareTo(fechaNacimiento);

        return age >= 0;
    }
}