package org.rogeriodesaf.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.rogeriodesaf.usuario.enums.Perfil;

public record UsuarioRequestDTO (

        @NotBlank(message = "O nome deve ser preenchido")
        String nome,
        @NotBlank(message = "O email deve ser informado")
        @Email(message = "Email inválido")
        String email,
        @NotBlank
        @Size(min=6, max=8)
        String senha

) {
}
