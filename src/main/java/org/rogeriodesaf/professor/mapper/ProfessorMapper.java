package org.rogeriodesaf.professor.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.professor.dto.ProfessorRequestDTO;
import org.rogeriodesaf.professor.dto.ProfessorResponseDTO;
import org.rogeriodesaf.professor.entity.Professor;

@ApplicationScoped
public class ProfessorMapper {

    public Professor toEntity(ProfessorRequestDTO professorRequestDTO) {
        Professor professor = new Professor();
        professor.nome = professorRequestDTO.nome();
        professor.email = professorRequestDTO.email();
        professor.especialidade = professorRequestDTO.especialidade();
        return professor;
    }

    public ProfessorResponseDTO toResponse(Professor professor) {
        return new ProfessorResponseDTO(
                professor.id,
                professor.nome,
                professor.email,
                professor.especialidade,
                professor.ativo
        );
    }
}
