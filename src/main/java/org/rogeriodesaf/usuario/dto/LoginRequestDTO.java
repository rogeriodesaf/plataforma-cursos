package org.rogeriodesaf.usuario.dto;

public record LoginRequestDTO(
        String email,
        String senha
) {
}
