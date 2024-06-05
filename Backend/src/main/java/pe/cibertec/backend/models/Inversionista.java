package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.cibertec.backend.validaciones.DniConstraint;
import pe.cibertec.backend.validaciones.TelfConstraint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Inversionista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInversionista;
    private String nombre;
    private String apellido;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    private String correo;
    @NotNull(message = "El documento es obligatorio")
    @DniConstraint
    private int documento;
    @NotNull(message = "El celular es obligatorio")
    @TelfConstraint
    private int celular;
    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @OneToMany(mappedBy = "inversionista")
    private List<JefePrestamista> jefesPrestamistas;
    @PrePersist
    protected void onCreate() {
        fechaCreacion = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fechaCreacion = formatter.parse(formatter.format(fechaCreacion));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
