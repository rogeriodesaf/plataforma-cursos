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

import java.util.List;

@ApplicationScoped
public class AulaService {

    private final AulaMapper aulaMapper;
    private final AulaRepository aulaRepository;
    private final CursoRepository cursoRepository;

    public AulaService(AulaMapper aulaMapper, AulaRepository aulaRepository, CursoRepository cursoRepository) {
        this.aulaMapper = aulaMapper;
        this.aulaRepository = aulaRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public AulaResponseDTO criar (AulaRequestDTO aulaRequestDTO) {
        Curso curso = cursoRepository.findById(aulaRequestDTO.cursoId());
        if (curso == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }

        Aula aula = aulaMapper.toEntity(aulaRequestDTO);

        Long quantidadeAulas = aulaRepository.count("curso.id", curso.id);

       aula.ordem =  quantidadeAulas.intValue() + 1;
       aula.curso = curso;
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
}
