package pe.cibertec.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import pe.cibertec.backend.validaciones.UserConstraint;

@Getter
@Setter
public class UserRegistrationDto {

    @JsonProperty("username")
    @UserConstraint(message = "El username ya está en uso")
    @NotBlank(message = "El username no puede estar vacío")
    private String username;
    private String password;
    private String rol; // Nuevo campo para el rol

}
