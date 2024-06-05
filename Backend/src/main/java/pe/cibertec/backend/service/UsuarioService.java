package pe.cibertec.backend.service;

import pe.cibertec.backend.models.Usuario;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> findAll();
    public Usuario findById (int isUsuario);
    public Usuario agregarUsuario (Usuario usuario);
    public Usuario actualizarUsuario (Usuario usuario);
    public void eliminarUsuario (int isUsuario);


}
