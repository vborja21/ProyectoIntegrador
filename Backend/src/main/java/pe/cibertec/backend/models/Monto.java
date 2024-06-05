package pe.cibertec.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Monto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMonto;
    private double monto;
    private int duracion;
    public Monto() {}
    public Monto(double monto, int duracion) {
        this.monto = monto;
        this.duracion = duracion;
    }
}
