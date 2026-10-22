package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.repositories.repositories.UsuarioRepository;
import com.project.my_landing_page.services.DefaultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioService extends DefaultServiceImpl<Usuario> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    protected UsuarioService(UsuarioRepository repository) {
        super(repository);
    }

    @Override
    public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario){
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
