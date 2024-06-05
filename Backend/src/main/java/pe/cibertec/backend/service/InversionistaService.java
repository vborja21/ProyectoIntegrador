package pe.cibertec.backend.service;

import org.springframework.stereotype.Service;
import pe.cibertec.backend.models.Inversionista;
import pe.cibertec.backend.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface InversionistaService {

    public List<Inversionista> findAll();
    public Inversionista findByCode(int idInversionista);
    public Inversionista findByName(String nombre);;
    public Inversionista findByCorreo(String correo);
    Optional<Inversionista> findByUsuario(Usuario usuario);
    public Inversionista addInversionista(Inversionista inversionista);
    public Inversionista editInversionista(Inversionista inversionista);
    public void deleteInversionista(int idInversionista);
}
