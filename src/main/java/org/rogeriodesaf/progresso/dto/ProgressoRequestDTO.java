package org.rogeriodesaf.progresso.dto;

public record ProgressoRequestDTO (
    String id,
    String matricula,
    String tipoProgresso,
    String descricao,
    String status
) {
}
