package org.rogeriodesaf.matricula.dto;

import java.time.LocalDateTime;

public record MatriculaResponseDTO(
        Long id,
        Long usuarioId,
        String nomeUsuario,
        Long cursoId,
        String tituloCurso,
        boolean ativa,
        LocalDateTime dataMatricula
) {
}
