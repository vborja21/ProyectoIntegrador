package pe.cibertec.backend.dto;
import lombok.Getter;
import lombok.Setter;
import pe.cibertec.backend.models.Monto;
import pe.cibertec.backend.models.Prestamista;
import pe.cibertec.backend.models.Prestatario;
import pe.cibertec.backend.models.Zona;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class PrestamoDTO {

    private int idPrestamo;
    private int idPrestatario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int idMonto;
    private double pagoDiario;
    private int estadoPrestamo;

}
