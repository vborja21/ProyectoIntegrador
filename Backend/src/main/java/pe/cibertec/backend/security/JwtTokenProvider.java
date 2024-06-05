package pe.cibertec.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:7200000}")
    private long validityInMilliseconds = 7200000; // 1h

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString("asdlkasdksadjklsazxczxcx1z321a5d1a320as2d0a".getBytes());
    }

    public String createToken(Authentication authentication) {
        ExtendedUserDetails userDetails = (ExtendedUserDetails) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("auth", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        // Incluir idEntidad en el token
        if (userDetails.getEntityId() != null) {
            claims.put("idEntidad", userDetails.getEntityId());
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // Decodificar la clave secreta codificada en Base64 a una clave de tipo Key
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                // Asegúrate de que se utilice un método de firma compatible sin DatatypeConverter
                .signWith(key, SignatureAlgorithm.HS256) // Esta línea ha sido modificada
                .compact();
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            // Decodificar la clave secreta codificada en Base64 a una clave de tipo Key
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            Key key = Keys.hmacShaKeyFor(keyBytes);

            System.out.println("Using secret key for validation: " + secretKey);
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            Date expiration = claims.getBody().getExpiration();
            System.out.println("Token expiration time: " + expiration);
            return !expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token validation error: " + e.getMessage());
            throw new JwtAuthenticationException("Expired or invalid JWT token", e);
        }
    }



    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    @Autowired
    private CustomUserDetailsService userDetailsService;
}
