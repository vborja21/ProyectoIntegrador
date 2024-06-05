package pe.cibertec.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JefePrestamistaUpdateDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String correo;
    private int documento;
    private int celular;
}
