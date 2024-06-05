package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCuota;
    private int idPrestamo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;

}
