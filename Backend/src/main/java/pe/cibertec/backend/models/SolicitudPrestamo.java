package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class SolicitudPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitudPrestamo;

    // Atributos adicionales
    private double montoSolicitado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date fechaSolicitud;
    private String estado; // Estado de la solicitud (pendiente, aprobado, rechazado, etc.)

    @ManyToOne
    @JoinColumn(name = "idPrestatario")
    private Prestatario prestatario;

    @ManyToOne
    @JoinColumn(name = "idPrestamista")
    private Prestamista prestamista;

}
