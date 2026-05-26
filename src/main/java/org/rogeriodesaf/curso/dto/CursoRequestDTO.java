package org.rogeriodesaf.curso.dto;

import jakarta.validation.constraints.NotBlank;

public record CursoRequestDTO(

        @NotBlank(message = "Informe o curso")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao
) {
}
