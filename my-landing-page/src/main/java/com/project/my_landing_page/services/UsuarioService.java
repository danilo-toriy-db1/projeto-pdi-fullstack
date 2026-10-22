package com.project.my_landing_page.services;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends DefaultServiceImpl<Usuario> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    protected UsuarioService(UsuarioRepository repository) {
        super(repository);
    }

    @Override
    public Usuario update(Long id, Usuario usuario){
        Usuario existingUsuario = usuarioRepository.findById(id).orElse(null);
        if(existingUsuario == null){
            return null;
        }

        existingUsuario.setUsuario(usuario.getUsuario());
        existingUsuario.setSenha(usuario.getSenha());
        existingUsuario.setRole(usuario.getRole());

        return usuarioRepository.save(existingUsuario);
    }

}
