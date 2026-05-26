package org.rogeriodesaf.progresso.dto;

public record ProgressoResponseDTO(
    Long cursoId,
    Long totalAuLas,
    Long aulasConcluidas,
    Double percentual
) {
}
