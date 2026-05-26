package org.rogeriodesaf.matricula.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.rogeriodesaf.curso.exception.CursoNaoEncontradoException;
import org.rogeriodesaf.curso.repository.CursoRepository;
import org.rogeriodesaf.matricula.dto.MatriculaResponseDTO;
import org.rogeriodesaf.matricula.entity.Matricula;
import org.rogeriodesaf.matricula.mapper.MatriculaMapper;
import org.rogeriodesaf.matricula.repository.MatriculaRepository;
import org.rogeriodesaf.seguranca.JwtService;
import org.rogeriodesaf.usuario.exception.UsuarioNaoEncontradoException;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;

@ApplicationScoped
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final JsonWebToken jsonWebToken;
    private final MatriculaMapper matriculaMapper;

    public MatriculaService(MatriculaRepository matriculaRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository, JsonWebToken jsonWebToken, MatriculaMapper matriculaMapper) {
        this.matriculaRepository = matriculaRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.jsonWebToken = jsonWebToken;

        this.matriculaMapper = matriculaMapper;
    }

    @Transactional
    public MatriculaResponseDTO matricularUsuarioEmCurso(Long cursoId) {
        String email = jsonWebToken.getSubject();
        var usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        var curso = cursoRepository.findById(cursoId);
        if (curso == null) {
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }

        var matriculaExistente =
                matriculaRepository.buscarPorUsuarioECurso(usuario.id, cursoId);
        if (matriculaExistente != null) {
            throw new RuntimeException("Usuário já matriculado neste curso");
        }

        var matricula = new Matricula();
        matricula.usuario = usuario;
        matricula.curso = curso;
        matricula.ativa = true;
        matricula.dataMatricula = LocalDateTime.now();

        matriculaRepository.persist(matricula);
        return matriculaMapper.toResponse(matricula);
    }
}
