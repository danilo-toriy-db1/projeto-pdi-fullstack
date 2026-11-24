package com.project.my_landing_page.services.security;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.models.enums.UsersRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static com.project.my_landing_page.mockData.UsuarioMock.getUsuarioMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "expiracaoMinutos", 60L);
        ReflectionTestUtils.setField(tokenService, "emissor", "my-landing-page");
    }

    @Test
    void deveRetornarTokenGeradoPeloEncoder() {
        configurarEncoderRetornando();

        TokenGerado res = tokenService.gerarToken(getUsuarioMock(Optional.of(UsersRoles.ADMIN)));

        assertEquals("token", res.token());
        assertThat(res.expiration()).isNotNull();
    }

    @Test
    void deveMontarClaimsComDadosDoUsuario() {
        configurarEncoderRetornando();

        tokenService.gerarToken(getUsuarioMock(Optional.of(UsersRoles.ADMIN)));

        JwtClaimsSet claims = capturarParametrosEnviados().getClaims();
        assertEquals("2", claims.getSubject());
        assertEquals("my-landing-page", claims.getClaims().get("iss"));
        assertEquals("ADMIN", claims.getClaimAsString("role"));
        assertEquals("Usuario", claims.getClaimAsString("usuario"));
    }

    @Test
    void deveDefinirExpiracaoDoTokenConformeMinutosConfigurados() {
        configurarEncoderRetornando();

        Instant antes = Instant.now();
        TokenGerado res = tokenService.gerarToken(getUsuarioMock(Optional.of(UsersRoles.ADMIN)));
        Instant depois = Instant.now();

        assertThat(res.expiration()).isBetween(
                antes.plus(60, ChronoUnit.MINUTES),
                depois.plus(60, ChronoUnit.MINUTES)
        );
        JwtClaimsSet claims = capturarParametrosEnviados().getClaims();
        assertEquals(res.expiration(), claims.getExpiresAt());
        assertEquals(res.expiration().minus(60, ChronoUnit.MINUTES), claims.getIssuedAt());
    }

    @Test
    void deveUsarAlgoritmoHS256NoHeader() {
        configurarEncoderRetornando();

        tokenService.gerarToken(getUsuarioMock(Optional.of(UsersRoles.ADMIN)));

        JwsHeader header = capturarParametrosEnviados().getJwsHeader();
        assertEquals(MacAlgorithm.HS256, header.getAlgorithm());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoTiverRole() {
        Usuario usuarioSemRole = new Usuario(1L, "Usuario", "senha", null, null, Set.of());

        Throwable erro = catchThrowable(() -> tokenService.gerarToken(usuarioSemRole));

        assertThat(erro).isInstanceOf(NullPointerException.class);
        verify(jwtEncoder, never()).encode(any());
    }

    private void configurarEncoderRetornando() {
        Jwt jwtFake = Jwt.withTokenValue("token")
                .header("alg", "HS256")
                .claim("sub", "1")
                .build();
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwtFake);
    }

    private JwtEncoderParameters capturarParametrosEnviados() {
        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);
        verify(jwtEncoder).encode(captor.capture());
        return captor.getValue();
    }
}
