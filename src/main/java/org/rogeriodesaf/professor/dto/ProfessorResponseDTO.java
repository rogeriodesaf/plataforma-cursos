package org.rogeriodesaf.professor.dto;

public record ProfessorResponseDTO(
        Long id,
        String nome,
        String email,
        String especialidade,
        boolean ativo
) {
}
