package org.rogeriodesaf.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequestDTO(
        @NotBlank(message = "O token deve ser informado")
        String token,
        @NotBlank(message = "A nova senha deve ser informada")
        @Size(min = 6, max = 8)
        String novaSenha
) {
}
