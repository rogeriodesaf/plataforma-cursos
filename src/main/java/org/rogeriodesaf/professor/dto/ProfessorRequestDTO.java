package org.rogeriodesaf.professor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfessorRequestDTO (

        @NotBlank
        String nome,

        @Email
        String email,

        String especialidade
){
}
