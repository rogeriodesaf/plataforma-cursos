package org.rogeriodesaf.aula.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.rogeriodesaf.aula.dto.AulaRequestDTO;
import org.rogeriodesaf.aula.dto.AulaResponseDTO;
import org.rogeriodesaf.aula.entity.Aula;
import org.rogeriodesaf.aula.exception.AulaAnteriorNaoConcluida;
import org.rogeriodesaf.aula.exception.AulaJaConcluidaException;
import org.rogeriodesaf.aula.exception.AulaJaExisteException;
import org.rogeriodesaf.aula.exception.AulaNaoEncontradaException;
import org.rogeriodesaf.aula.mapper.AulaMapper;
import org.rogeriodesaf.aula.repository.AulaRepository;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.curso.exception.CursoNaoEncontradoException;
import org.rogeriodesaf.curso.repository.CursoRepository;
import org.rogeriodesaf.matricula.entity.Matricula;
import org.rogeriodesaf.matricula.repository.MatriculaRepository;
import org.rogeriodesaf.professor.entity.Professor;
import org.rogeriodesaf.professor.exception.ProfessorNaoEncontradoException;
import org.rogeriodesaf.professor.repository.ProfessorRepository;
import org.rogeriodesaf.progresso.entity.Progresso;
import org.rogeriodesaf.progresso.exception.AlunoNaoMatriculadoException;
import org.rogeriodesaf.progresso.repository.ProgressoRepository;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.exception.UsuarioNaoEncontradoException;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

import java.util.List;

@ApplicationScoped
public class AulaService {

    private final AulaMapper aulaMapper;
    private final AulaRepository aulaRepository;
    private final CursoRepository cursoRepository;
    private final ProfessorRepository professorRepository;
    private final JsonWebToken jsonWebToken;
    private final UsuarioRepository usuarioRepository;
    private final MatriculaRepository matriculaRepository;
    private final ProgressoRepository progressoRepository;

    public AulaService(AulaMapper aulaMapper, AulaRepository aulaRepository, CursoRepository cursoRepository, ProfessorRepository professorRepository, JsonWebToken jsonWebToken, UsuarioRepository usuarioRepository, MatriculaRepository matriculaRepository, ProgressoRepository progressoRepository) {
        this.aulaMapper = aulaMapper;
        this.aulaRepository = aulaRepository;
        this.cursoRepository = cursoRepository;
        this.professorRepository = professorRepository;
        this.jsonWebToken = jsonWebToken;
        this.usuarioRepository = usuarioRepository;
        this.matriculaRepository = matriculaRepository;
        this.progressoRepository = progressoRepository;
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

    public List<AulaResponseDTO> listarAulas (Long id){
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

    @Transactional
    public void concluirAula (Long aulaId) {
        String email = jsonWebToken.getSubject();

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        Aula aula = aulaRepository.findById(aulaId);
        if (aula == null) {
            throw new AulaNaoEncontradaException("Aula não encontrada");
        }
        Matricula matricula = matriculaRepository.buscarPorUsuarioECurso(aulaId, usuario.id);
        if (matricula == null) {
            throw new AlunoNaoMatriculadoException("Aluno não matriculado neste curso");
        }

        Progresso progressoExistente = progressoRepository.buscaPorUsuarioEAula(usuario.id, aulaId);
        if (progressoExistente != null) {
            throw new AulaJaConcluidaException("Aula já concluída");
        }

        Aula aulaAnterior = aulaRepository.buscarAulaPorCursoEOrdem(aula.curso.id, aula.ordem - 1);
        Progresso progressoAnterior = progressoRepository.buscaPorUsuarioEAula(usuario.id, aulaAnterior.id);
        if (progressoAnterior == null) {
            throw new AulaAnteriorNaoConcluida("Conclua aula anterior antes de avançar para esta aula");
        }

        Progresso progresso = new Progresso();
        progresso.usuario = usuario;
        progresso.aula = aula;
        progresso.dataConclusao = java.time.LocalDateTime.now();

        progressoRepository.persist(progresso);
    }
}
