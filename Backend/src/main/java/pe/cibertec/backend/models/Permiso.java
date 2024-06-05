package pe.cibertec.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermiso;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private Rol rol;

}
