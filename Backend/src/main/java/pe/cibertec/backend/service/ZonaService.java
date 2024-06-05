package pe.cibertec.backend.service;


import pe.cibertec.backend.models.Zona;

import java.util.List;

public interface ZonaService {
    List<Zona> listarZonas();
    List<Zona> findZonasByJefePrestamistaId(int idJefePrestamista);
}
