package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends DefaultServiceImpl<Usuario> {

    private PasswordEncoder passwordEncoder;

    private UsuarioRepository usuarioRepository;

    protected UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = repository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return super.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario){
        Usuario existingUsuario = usuarioRepository.findById(id).orElse(null);
        if(existingUsuario == null){
            return null;
        }

        existingUsuario.setUsuario(usuario.getUsuario());
        existingUsuario.setRole(usuario.getRole());
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            existingUsuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(existingUsuario);
    }

}
