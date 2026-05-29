package org.rogeriodesaf.professor.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.rogeriodesaf.professor.dto.ProfessorRequestDTO;
import org.rogeriodesaf.professor.dto.ProfessorResponseDTO;
import org.rogeriodesaf.professor.entity.Professor;
import org.rogeriodesaf.professor.exception.ProfessorJaCadastradoException;
import org.rogeriodesaf.professor.exception.ProfessorNaoEncontradoException;
import org.rogeriodesaf.professor.mapper.ProfessorMapper;
import org.rogeriodesaf.professor.repository.ProfessorRepository;

import java.util.List;

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
        Professor professorExistente = professorRepository.findByEmail(professorRequestDTO.email());
        if (professorExistente != null) {
            throw new ProfessorJaCadastradoException("Professor ja cadastrado com o email: " + professorRequestDTO.email());
        }

        Professor professor = professorMapper.toEntity(professorRequestDTO);
        professor.ativo = true;

        professorRepository.persist(professor);
        return professorMapper.toResponse(professor);
    }

    @Transactional
    public List<ProfessorResponseDTO> listarProfessores() {
        List<Professor> professores = professorRepository.listAtivos();
        if (professores.isEmpty()) {
            throw new ProfessorNaoEncontradoException("Nenhum professor encontrado");
        }

        return professores.stream()
                .map(professorMapper::toResponse)
                .toList();
    }

    @Transactional
    public List<ProfessorResponseDTO> listarTodosParaAdmin() {
        List<Professor> professores = professorRepository.listAll();
        if (professores.isEmpty()) {
            throw new ProfessorNaoEncontradoException("Nenhum professor encontrado");
        }

        return professores.stream()
                .map(professorMapper::toResponse)
                .toList();
    }

    public ProfessorResponseDTO listarProfessorPorId(Long id) {
        Professor professor = professorRepository.findById(id);
        if (professor == null) {
            throw new ProfessorNaoEncontradoException("Professor nao encontrado com o id: " + id);
        }

        return professorMapper.toResponse(professor);
    }

    @Transactional
    public ProfessorResponseDTO atualizarProfessor(Long id, ProfessorRequestDTO professorRequestDTO) {
        Professor professor = professorRepository.findById(id);
        if (professor == null) {
            throw new ProfessorNaoEncontradoException("Professor nao encontrado com o id: " + id);
        }

        Professor professorExistente = professorRepository.findByEmail(professorRequestDTO.email());
        if (professorExistente != null && !professorExistente.id.equals(professor.id)) {
            throw new ProfessorJaCadastradoException("Professor ja cadastrado com o email: " + professorRequestDTO.email());
        }

        professor.nome = professorRequestDTO.nome();
        professor.email = professorRequestDTO.email();
        professor.especialidade = professorRequestDTO.especialidade();

        professorRepository.persist(professor);
        return professorMapper.toResponse(professor);
    }

    @Transactional
    public void desativarProfessor(Long id) {
        Professor professor = professorRepository.findById(id);
        if (professor == null) {
            throw new ProfessorNaoEncontradoException("Professor nao encontrado com o id: " + id);
        }

        professor.ativo = false;
        professorRepository.persist(professor);
    }

    @Transactional
    public ProfessorResponseDTO ativarProfessor(Long id) {
        Professor professor = professorRepository.findById(id);
        if (professor == null) {
            throw new ProfessorNaoEncontradoException("Professor nao encontrado com o id: " + id);
        }

        professor.ativo = true;
        professorRepository.persist(professor);
        return professorMapper.toResponse(professor);
    }
}
