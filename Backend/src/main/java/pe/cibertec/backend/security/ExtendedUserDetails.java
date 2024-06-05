package pe.cibertec.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUserDetails extends User {
    private final Integer entityId;

    public ExtendedUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer entityId) {
        super(username, password, authorities);
        this.entityId = entityId;
    }

    public Integer getEntityId() {
        return entityId;
    }
}
