package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUbicacion;
    private String nombre;
    private String direccion;
    public Ubicacion(){}
    public Ubicacion(String nombre, String direccion){
        this.nombre = nombre;
        this.direccion = direccion;
    }

    @OneToMany(mappedBy = "idZona")
    @JsonManagedReference
    private List<Zona> zonas;
}

