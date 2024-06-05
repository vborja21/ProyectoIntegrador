package pe.cibertec.backend.service;

import pe.cibertec.backend.dto.PrestamistaDTO;
import pe.cibertec.backend.dto.PrestamistaUpdateDTO;
import pe.cibertec.backend.models.Prestamista;
import pe.cibertec.backend.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface PrestamistaService {

     List<Prestamista> findAll();
     List<Prestamista> findAllByJefePrestamistaId(int idJefePrestamista);
     public Prestamista findByNombre(String nombre);
     public Prestamista findById(int idPrestamista);
     public Prestamista findByCorreo(String correo);
     public Optional<Prestamista> findByUsuario(Usuario usuario);
     public Prestamista agregarPrestamista (PrestamistaDTO prestamistaDTO);
     public Prestamista actualizarPrestamista (int id, PrestamistaUpdateDTO updateDTO);
     public void eliminarPrestamista (int idPrestamista);

}
