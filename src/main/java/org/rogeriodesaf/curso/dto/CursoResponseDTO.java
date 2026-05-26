package org.rogeriodesaf.curso.dto;

public record CursoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        Boolean ativo
) {
}
