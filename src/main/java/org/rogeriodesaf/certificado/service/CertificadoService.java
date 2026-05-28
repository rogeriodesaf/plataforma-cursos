package org.rogeriodesaf.certificado.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.rogeriodesaf.aula.repository.AulaRepository;
import org.rogeriodesaf.certificado.dto.CertificadoResponseDTO;
import org.rogeriodesaf.certificado.entity.Certificado;
import org.rogeriodesaf.certificado.mapper.CertificadoMapper;
import org.rogeriodesaf.certificado.repository.CertificadoRepository;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.curso.exception.CursoNaoConcluidoException;
import org.rogeriodesaf.curso.exception.CursoNaoEncontradoException;
import org.rogeriodesaf.curso.repository.CursoRepository;
import org.rogeriodesaf.matricula.repository.MatriculaRepository;
import org.rogeriodesaf.progresso.exception.AlunoNaoMatriculadoException;
import org.rogeriodesaf.progresso.repository.ProgressoRepository;
import org.rogeriodesaf.usuario.exception.UsuarioNaoEncontradoException;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

import javax.swing.text.html.ObjectView;

@ApplicationScoped
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final JsonWebToken jsonWebToken;
    private final UsuarioRepository usuarioRepository;
    private final CertificadoMapper certificadoMapper;
    private final MatriculaRepository matriculaRepository;
    private final AulaRepository aulaRepository;
    private final ProgressoRepository progressoRepository;
    private final CursoRepository cursoRepository;

    public CertificadoService(CertificadoRepository certificadoRepository, JsonWebToken jsonWebToken, UsuarioRepository usuarioRepository, CertificadoMapper certificadoMapper, MatriculaRepository matriculaRepository, AulaRepository aulaRepository, ProgressoRepository progressoRepository, CursoRepository cursoRepository) {
        this.certificadoRepository = certificadoRepository;
        this.jsonWebToken = jsonWebToken;
        this.usuarioRepository = usuarioRepository;
        this.certificadoMapper = certificadoMapper;
        this.matriculaRepository = matriculaRepository;
        this.aulaRepository = aulaRepository;
        this.progressoRepository = progressoRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public CertificadoResponseDTO obterCertificadoPorCurso(Long cursoId) {
        String email = jsonWebToken.getSubject();
        var usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        Curso curso = cursoRepository.findById(cursoId);
        if (curso == null){
            throw new CursoNaoEncontradoException("Curso não encontrado");
        }

        var matricula = matriculaRepository.buscarPorUsuarioECurso(usuario.id, cursoId);
        if (matricula == null || !matricula.ativa) {
            throw new AlunoNaoMatriculadoException("Usuário não está matriculado ou a matrícula não está ativa");
        }

        Long totalAulas = aulaRepository.count("curso.id", cursoId);


        Long aulasConcluidas = progressoRepository.contarAulasConcluidasPorUsuarioECurso(usuario.id, cursoId);
        if (!totalAulas.equals(aulasConcluidas)) {
            throw new CursoNaoConcluidoException("Usuário não concluiu todas as aulas do curso");
        }

        double percentualConclusao = 0.0;
        if (totalAulas > 0) {
            percentualConclusao = (aulasConcluidas.doubleValue() / totalAulas.doubleValue()) * 100.0;
        }

        if (percentualConclusao < 100.0) {
            throw new CursoNaoConcluidoException("Usuário não concluiu todas as aulas do curso");
        }

        var certificadoExistente = certificadoRepository.buscarPorUsuarioECurso(usuario.id, cursoId);
        if (certificadoExistente != null) {
            return certificadoMapper.toResponse(certificadoExistente);
        }

        String codigoValidacao = java.util.UUID.randomUUID().toString();

        Certificado certificado = new Certificado();
        certificado.usuario = usuario;
        certificado.curso = curso;
        certificado.dataEmissao = java.time.LocalDateTime.now();
        certificado.codigoValidacao = codigoValidacao;

        certificadoRepository.persist(certificado);
        return certificadoMapper.toResponse(certificado);
    }
}
