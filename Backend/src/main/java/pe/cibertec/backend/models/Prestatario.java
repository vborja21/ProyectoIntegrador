package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.cibertec.backend.validaciones.AgeConstraint;
import pe.cibertec.backend.validaciones.DniConstraint;
import pe.cibertec.backend.validaciones.TelfConstraint;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Prestatario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestatario;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de nacimiento es obligatorio")
    @AgeConstraint
    private LocalDate fechaNacimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @NotBlank(message = "El correo es obligatorio")
    @Column(unique = true)
    private String correo;
    @Column(unique = true)
    @NotNull(message = "El documento es obligatorio")
    @DniConstraint
    private int documento;
    @NotNull(message = "El celular es obligatorio")
    @TelfConstraint
    private int celular;
    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "idPrestamista", nullable = true)
    @JsonBackReference
    private Prestamista prestamista;

    //Validacion para que la fechaCreacion se cree automatico

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

