package pe.cibertec.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class PrestamistaDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Date fechaCreacion;
    private String correo;
    private int documento;
    private int celular;
    private String login;
    private String password;
    private String rolDescripcion;
    private Integer idZona;
    private int idJefePrestamista;
}
