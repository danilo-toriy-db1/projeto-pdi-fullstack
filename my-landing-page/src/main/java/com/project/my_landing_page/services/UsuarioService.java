package com.project.my_landing_page.services;

import com.project.my_landing_page.models.Usuario;
import com.project.my_landing_page.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario saveUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAllUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findUsuarioById(Long id){
        return usuarioRepository.findById(id);
    }

    public void deleteByIdUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario updateUsuario(Long id, Usuario usuario){
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
