package org.rogeriodesaf.usuario.dto;

import org.rogeriodesaf.usuario.enums.Perfil;

public record UsuarioResponseDTO (
        Long id,
        String nome,
        String email,
        Perfil perfil
){
}
