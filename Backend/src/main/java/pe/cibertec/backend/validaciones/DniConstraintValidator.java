package pe.cibertec.backend.validaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DniConstraintValidator implements ConstraintValidator<DniConstraint, Integer> {
    @Override
    public void initialize(DniConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer documento, ConstraintValidatorContext context) {
        if(documento == null){
            return false;
        }

        // Solo permite 8 digitos para el DNI
        String dniStr = Integer.toString(documento);
        return dniStr.matches("^\\d{8}$");


    }
}
