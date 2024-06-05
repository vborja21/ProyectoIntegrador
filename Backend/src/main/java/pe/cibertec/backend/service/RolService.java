package pe.cibertec.backend.service;

import pe.cibertec.backend.models.Rol;

import java.util.List;

public interface RolService {

    List<Rol> findAll();
    public Rol findByDescripcion(String descripcion);
    public Rol findById (int idRol);
    public Rol agregarRol (Rol rol);
    public Rol actualizarRol (Rol rol);
    public void eliminarRol (int idRol);

}
