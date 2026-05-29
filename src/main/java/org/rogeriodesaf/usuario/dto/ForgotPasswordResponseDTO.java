package org.rogeriodesaf.usuario.dto;

public record ForgotPasswordResponseDTO(
        String mensagem,
        String token,
        String linkRedefinicao
) {
}
