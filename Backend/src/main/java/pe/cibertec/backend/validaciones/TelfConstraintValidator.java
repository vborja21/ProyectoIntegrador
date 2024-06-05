package pe.cibertec.backend.validaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelfConstraintValidator implements ConstraintValidator<TelfConstraint, Integer> {
    @Override
    public void initialize(TelfConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer celular, ConstraintValidatorContext context) {
        if(celular == null){
            return false;
        }

        // Solo permite 9 digitos para el numero de telefono
        String celularStr = Integer.toString(celular);
        return celularStr.matches("^\\d{9}$");
    }
}
