package pe.cibertec.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestamo;

    @ManyToOne
    @JoinColumn(name = "idPrestatario")
    private Prestatario prestatario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "idMonto", nullable = true)
    private Monto monto;
    private double pagoDiario;


    private int estadoPrestamo;

    @PrePersist
    protected void onCreate() {

        // Verificar que fechaInicio ha sido establecida

        if (fechaInicio == null) {
            throw new IllegalStateException("Fecha de inicio debe ser establecida manualmente.");
        }

        if (monto != null && monto.getDuracion() > 0) {
            fechaFin = fechaInicio.plusDays(monto.getDuracion());
            Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());


            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaInicioDate);
            cal.add(Calendar.DAY_OF_MONTH, monto.getDuracion());
            fechaFinDate = cal.getTime();
            fechaFin = fechaFinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        int diasLaborales = contarDiasLaborales(fechaInicio, fechaFin);
        if (diasLaborales > 0) {
            double calculo = monto.getMonto() / diasLaborales;
            pagoDiario = Math.round(calculo * 100.0) / 100.0; // Redondea a dos decimales
        }

    }

    //Validacion para que no cuenten los dias sabados y domingos para el prestamo

    private int contarDiasLaborales(LocalDate start, LocalDate end) {
        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());


        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.add(Calendar.DAY_OF_MONTH, 1);

        int workDays = 0;

        while (startCal.before(endCal)) {
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                    startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                workDays++;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return workDays;
    }

}
