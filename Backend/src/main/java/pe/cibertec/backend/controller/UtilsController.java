package pe.cibertec.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.backend.models.Monto;
import pe.cibertec.backend.models.Ubicacion;
import pe.cibertec.backend.models.Zona;
import pe.cibertec.backend.service.MontoService;
import pe.cibertec.backend.service.PrestamistaService;
import pe.cibertec.backend.service.UbicacionService;
import pe.cibertec.backend.service.ZonaService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/utils")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilsController {

    @Autowired
    private UbicacionService ubicacionService;

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private PrestamistaService prestamistaService;

    @Autowired
    private MontoService montoService;

    @GetMapping("/ubicaciones")
    public ResponseEntity<List<Ubicacion>> findAllUbicaciones() {
        log.info("Solicitud GET recibida: findAllUbicaciones");
        List<Ubicacion> ubicaciones = ubicacionService.findAll();
        log.info("Respuesta enviada: {}", ubicaciones);
        return new ResponseEntity<>(ubicaciones, HttpStatus.OK);
    }

    @GetMapping("/zonas")
    public ResponseEntity<List<Zona>> findAllZonas() {
        log.info("Solicitud GET recibida: findAllZonas");
        List<Zona> zonas = zonaService.listarZonas();
        log.info("Respuesta enviada: {}", zonas);
        return new ResponseEntity<>(zonas, HttpStatus.OK);
    }

    @GetMapping("/zonas/{idJefePrestamista}")
    public ResponseEntity<List<Zona>> getZonasByJefePrestamista(@PathVariable int idJefePrestamista) {
        List<Zona> zonas = zonaService.findZonasByJefePrestamistaId(idJefePrestamista);
        return ResponseEntity.ok(zonas);
    }

    @GetMapping("/montos")
    public ResponseEntity<?> findAllMontos() {
        List<Monto> montos = montoService.findAll();
        return new ResponseEntity<>(montos, HttpStatus.OK);
    }

    @GetMapping("/montos/{idMonto}")
    public ResponseEntity<?> findMontoById(@PathVariable int idMonto) {
        Monto monto = montoService.findById(idMonto);
        return new ResponseEntity<>(monto, HttpStatus.OK);
    }

}
