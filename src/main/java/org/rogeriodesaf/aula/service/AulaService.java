package org.rogeriodesaf.aula.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;
import org.rogeriodesaf.aula.dto.AulaRequestDTO;
import org.rogeriodesaf.aula.dto.AulaResponseDTO;
import org.rogeriodesaf.aula.entity.Aula;
import org.rogeriodesaf.aula.exception.AulaJaExisteException;
import org.rogeriodesaf.aula.mapper.AulaMapper;
import org.rogeriodesaf.aula.repository.AulaRepository;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.curso.exception.CursoNaoEncontradoException;
import org.rogeriodesaf.curso.repository.CursoRepository;
import org.rogeriodesaf.professor.entity.Professor;
import org.rogeriodesaf.professor.exception.ProfessorNaoEncontradoException;
import org.rogeriodesaf.professor.repository.ProfessorRepository;

import java.util.List;

@ApplicationScoped
public class AulaService {

    private final AulaMapper aulaMapper;
    private final AulaRepository aulaRepository;
    private final CursoRepository cursoRepository;
    private final ProfessorRepository professorRepository;

    public AulaService(AulaMapper aulaMapper, AulaRepository aulaRepository, CursoRepository cursoRepository, ProfessorRepository professorRepository) {
        this.aulaMapper = aulaMapper;
        this.aulaRepository = aulaRepository;
        this.cursoRepository = cursoRepository;
        this.professorRepository = professorRepository;
    }

    @Transactional
    public AulaResponseDTO criar (AulaRequestDTO aulaRequestDTO) {
        Curso curso = cursoRepository.findById(aulaRequestDTO.cursoId());
        if (curso == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }

        Professor professor = professorRepository.findById(aulaRequestDTO.professorId());
        if (professor == null){
            throw new ProfessorNaoEncontradoException("Professor não encontrado");
        }

        Aula aula = aulaMapper.toEntity(aulaRequestDTO);

        Long quantidadeAulas = aulaRepository.count("curso.id", curso.id);

       aula.ordem =  quantidadeAulas.intValue() + 1;
       aula.curso = curso;
       aula.professor = professor;
       aula.ativa = true;

        aulaRepository.persist(aula);

        return aulaMapper.toResponse(aula);
    }

    public List<AulaResponseDTO> listarAulas (@PathParam("id") Long id){
        List<Aula> aulas = aulaRepository.findByCurso(id);
         return aulas.stream()
                 .map(aulaMapper::toResponse)
                 .toList();
    }

    public List<AulaResponseDTO> listarAulasPorProfessor ( Long professorId){
        List<Aula> aulas = aulaRepository.listarProfessor(professorId);
        return aulas.stream()
                .map(aulaMapper::toResponse)
                .toList();
    }
}
