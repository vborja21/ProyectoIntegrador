package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.util.List;


@Entity
@Getter
@Setter
public class JefePrestamista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idJefePrestamista;
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
    @JoinColumn(name = "idUbicacion")
    private Ubicacion ubicacion;
    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "idInversionista", nullable = true)
    private Inversionista inversionista;
    @OneToMany(mappedBy = "jefePrestamista")
    @JsonManagedReference
    private List<Prestamista> prestamistas;

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

