package org.rogeriodesaf.aula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AulaRequestDTO (

        @NotBlank(message = "titulo é obrigatório")
        String titulo,

        @NotBlank(message = "descricao é obrigatória")
        String descricao,

        @NotBlank(message = "A URL do vídeo é obrigatória")
        String urlVideo,

        @Positive(message = "A duração deve ser maior do que zero")
        @NotNull(message = "A duração é obrigatória")
        Integer duracaoMinutos,

        Long cursoId
) {
}
