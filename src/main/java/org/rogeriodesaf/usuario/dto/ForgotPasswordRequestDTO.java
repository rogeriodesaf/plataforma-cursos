package org.rogeriodesaf.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequestDTO(
        @NotBlank(message = "O email deve ser informado")
        @Email(message = "Email invalido")
        String email
) {
}
