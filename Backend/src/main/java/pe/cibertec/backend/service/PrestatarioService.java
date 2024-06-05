package pe.cibertec.backend.service;

import pe.cibertec.backend.dto.JefePrestamistaDTO;
import pe.cibertec.backend.dto.PrestatarioDTO;
import pe.cibertec.backend.dto.PrestatarioUpdateDTO;
import pe.cibertec.backend.models.JefePrestamista;
import pe.cibertec.backend.models.Prestatario;
import pe.cibertec.backend.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface PrestatarioService {

    List<Prestatario> findAll();
    List<Prestatario> findAllByPrestamistaId(int idPrestamista);
    Prestatario findById(int id);
    Optional<Prestatario> findByUsuario(Usuario usuario);
    Prestatario add(PrestatarioDTO prestatarioDTO);
    Prestatario update(int id, PrestatarioUpdateDTO updateDTO);
    public void delete (int idPrestatario);

}
