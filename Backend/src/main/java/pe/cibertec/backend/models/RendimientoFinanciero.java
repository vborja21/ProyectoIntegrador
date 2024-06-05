package pe.cibertec.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RendimientoFinanciero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRendimientoFinanciero;

    // Atributos adicionales
    private double ROI; // Retorno de la inversión
    private double tasaInteresPromedio; // Tasa de interés promedio
    private double cantidadTotalPrestada; // Cantidad total prestada
    private double cantidadTotalReembolsada; // Cantidad total reembolsada



    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    private Ubicacion ubicacion;
}
