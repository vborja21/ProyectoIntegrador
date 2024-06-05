package pe.cibertec.backend.service;

import pe.cibertec.backend.dto.JefePrestamistaDTO;
import pe.cibertec.backend.dto.JefePrestamistaUpdateDTO;
import pe.cibertec.backend.models.JefePrestamista;
import pe.cibertec.backend.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface JefePrestamistaService {
    public List<JefePrestamista> findAll();
    public JefePrestamista findById(int id);
    public JefePrestamista findByName(String name);
    Optional<JefePrestamista> findByUsuario(Usuario usuario);
    public JefePrestamista add(JefePrestamistaDTO jefePrestamistaDTO);
    //public JefePrestamista add(JefePrestamista jefePrestamista);
    public JefePrestamista update(int id, JefePrestamistaUpdateDTO UpdateDTO);
    public void delete (int id);

}
