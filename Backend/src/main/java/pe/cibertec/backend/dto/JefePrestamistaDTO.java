package pe.cibertec.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class JefePrestamistaDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Date fechaCreacion;
    private int documento;
    private int celular;
    private String correo;
    private int idUbicacion;
    private String login;
    private String password;
    private String rolDescripcion;

}
