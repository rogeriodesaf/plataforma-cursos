package org.rogeriodesaf.usuario.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.usuario.dto.UsuarioRequestDTO;
import org.rogeriodesaf.usuario.dto.UsuarioResponseDTO;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.enums.Perfil;

@ApplicationScoped
public class UsuarioMapper {

    public Usuario toEntity (UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = new Usuario();
        usuario.nome = usuarioRequestDTO.nome();
        usuario.email = usuarioRequestDTO.email();

        return usuario;
    }

    public UsuarioResponseDTO toResponse(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.perfil
        );
    }
}

