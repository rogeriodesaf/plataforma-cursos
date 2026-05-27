package org.rogeriodesaf.aula.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

public record AulaResponseDTO (
        Long id,
        String titulo,
        String descricao,
        String urlVideo,
        Integer ordem,
        Integer duracaoMinutos,
        Boolean ativa,
        Long cursoId,
        String nomeCurso,
        Long professorId,
        String nomeProfessor
) {
}
