package com.project.my_landing_page.services.security;

import com.project.my_landing_page.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${app.jwt.expiracao-minutos}")
    private long expiracaoMinutos;

    @Value("${app.jwt.emissor}")
    private String emissor;

    public TokenGerado gerarToken(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiration = now.plus(expiracaoMinutos, ChronoUnit.MINUTES);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(emissor)
                .subject(String.valueOf(usuario.getId()))
                .issuedAt(now)
                .expiresAt(expiration)
                .claim("usuario", usuario.getUsuario())
                .claim("role", usuario.getRole().name())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();

        return new TokenGerado(token, expiration);
    }
}
