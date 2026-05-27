package org.rogeriodesaf.professor.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.professor.dto.ProfessorRequestDTO;
import org.rogeriodesaf.professor.dto.ProfessorResponseDTO;
import org.rogeriodesaf.professor.exception.ProfessorJaCadastradoException;
import org.rogeriodesaf.professor.exception.ProfessorNaoEncontradoException;
import org.rogeriodesaf.professor.mapper.ProfessorMapper;
import org.rogeriodesaf.professor.repository.ProfessorRepository;

@ApplicationScoped
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
    }

    @Transactional
    public ProfessorResponseDTO criarProfessor(ProfessorRequestDTO professorRequestDTO) {
        var professorExistente = professorRepository.findByEmail(professorRequestDTO.email());
        if (professorExistente != null){
            throw new ProfessorJaCadastradoException("Professor já cadastrado com o email: " + professorRequestDTO.email());
        }

        var professor = professorMapper.toEntity(professorRequestDTO);
        professor.ativo = true;

        professorRepository.persist(professor);
        return professorMapper.toResponse(professor);
    }
}
