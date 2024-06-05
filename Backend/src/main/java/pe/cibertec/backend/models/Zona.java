package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter

public class Zona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idZona;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    @JsonIgnoreProperties("zonas")
    private Ubicacion ubicacion;

    
}


