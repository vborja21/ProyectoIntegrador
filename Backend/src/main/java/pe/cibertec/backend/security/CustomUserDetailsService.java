package pe.cibertec.backend.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.cibertec.backend.models.*;
import pe.cibertec.backend.repository.*;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermisoRepository permisoRepository; // Necesitas implementar este repositorio

    @Autowired
    private JefePrestamistaRepository jefePrestamistaRepository;

    @Autowired
    private PrestamistaRepository prestamistaRepository;

    @Autowired
    private PrestatarioRepository prestatarioRepository;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el login: " + username));

        // Aquí necesitas recuperar los permisos del usuario a través del PermisoRepository
        Set<Permiso> permisos = permisoRepository.findByUsuario(usuario);
        Set<GrantedAuthority> authorities = permisos.stream()
                .map(permiso -> new SimpleGrantedAuthority(permiso.getRol().getDescripcion()))
                .collect(Collectors.toSet());

        // Aquí necesitas recuperar los ID de las entidades asociadas
        Integer entityId = null;
        JefePrestamista jefe = jefePrestamistaRepository.findByUsuario(usuario).orElse(null);
        if (jefe != null) {
            entityId = jefe.getIdJefePrestamista();
        } else {
            Prestamista prestamista = prestamistaRepository.findByUsuario(usuario).orElse(null);
            if (prestamista != null) {
                entityId = prestamista.getIdPrestamista();
            } else {
                Prestatario prestatario = prestatarioRepository.findByUsuario(usuario).orElse(null);
                if(prestatario != null){
                    entityId = prestatario.getIdPrestatario();
                }

            }
            // Agregar otras condiciones para diferentes tipos de usuarios si es necesario
        }

        // Loguear las autoridades para debug
        System.out.println("Autoridades cargadas: " + authorities);

        return new ExtendedUserDetails(usuario.getLogin(), usuario.getClave(), authorities, entityId);
    }

    private Collection<? extends GrantedAuthority> mapearPermisosARoles(Set<Permiso> permisos) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Permiso permiso : permisos) {
            authorities.add(new SimpleGrantedAuthority(permiso.getRol().getDescripcion()));
        }
        return authorities;
    }
}
