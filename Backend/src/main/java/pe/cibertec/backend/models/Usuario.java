package pe.cibertec.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.cibertec.backend.validaciones.UserConstraint;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int isUsuario;

    @Column(unique = true)
    private String login;
    private String clave;
    private int estado;

}
