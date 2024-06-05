package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRecomendacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date fechaRecomendacion;

    @ManyToOne
    @JoinColumn(name = "idPrestatario", insertable = false, updatable = false)
    private Prestatario prestatarioRecomendador; // Relación con el prestatario que recomienda

    @ManyToOne
    @JoinColumn(name = "idPrestatario", insertable = false, updatable = false)
    private Prestatario prestatarioRecomendado; // Relación con el prestatario recomendado

}
