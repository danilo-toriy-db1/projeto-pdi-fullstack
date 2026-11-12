package com.project.my_landing_page.services.security;

import com.project.my_landing_page.http.request.LoginRequest;
import com.project.my_landing_page.http.response.LoginResponse;
import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final String hashFalso;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.hashFalso = passwordEncoder.encode("senhaFalsa");
    }

    public LoginResponse login(LoginRequest req) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsuario(req.usuario());

        String hashSenha = usuarioEncontrado.map(Usuario::getSenha).orElse(hashFalso);
        boolean senhaValida = passwordEncoder.matches(req.senha(), hashSenha);

        if (usuarioEncontrado.isEmpty() || !senhaValida) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        Usuario usuario = usuarioEncontrado.get();
        TokenGerado tokenGerado = tokenService.gerarToken(usuario);

        return new LoginResponse(tokenGerado.token(), "Bearer", tokenGerado.expiration(), usuario.getId(), usuario.getUsuario(), usuario.getRole());
    }
}
