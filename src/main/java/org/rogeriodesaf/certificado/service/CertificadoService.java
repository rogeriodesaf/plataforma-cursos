package org.rogeriodesaf.certificado.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.rogeriodesaf.aula.repository.AulaRepository;
import org.rogeriodesaf.certificado.dto.CertificadoResponseDTO;
import org.rogeriodesaf.certificado.entity.Certificado;
import org.rogeriodesaf.certificado.exception.CertificadoNaoEncontradoException;
import org.rogeriodesaf.certificado.mapper.CertificadoMapper;
import org.rogeriodesaf.certificado.repository.CertificadoRepository;
import org.rogeriodesaf.certificado.util.CertificadoPdfGenerator;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.curso.exception.CursoNaoConcluidoException;
import org.rogeriodesaf.curso.exception.CursoNaoEncontradoException;
import org.rogeriodesaf.curso.repository.CursoRepository;
import org.rogeriodesaf.matricula.repository.MatriculaRepository;
import org.rogeriodesaf.progresso.exception.AlunoNaoMatriculadoException;
import org.rogeriodesaf.progresso.repository.ProgressoRepository;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.exception.UsuarioNaoEncontradoException;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public CertificadoService(
            CertificadoRepository certificadoRepository,
            JsonWebToken jsonWebToken,
            UsuarioRepository usuarioRepository,
            CertificadoMapper certificadoMapper,
            MatriculaRepository matriculaRepository,
            AulaRepository aulaRepository,
            ProgressoRepository progressoRepository,
            CursoRepository cursoRepository
    ) {
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
        return certificadoMapper.toResponse(obterOuEmitirCertificado(cursoId));
    }

    @Transactional
    public byte[] gerarPdfPorCurso(Long cursoId) {
        CertificadoResponseDTO certificado = certificadoMapper.toResponse(obterOuEmitirCertificado(cursoId));
        return CertificadoPdfGenerator.generate(certificado);
    }

    public CertificadoResponseDTO validarPorCodigo(String codigoValidacao) {
        Certificado certificado = certificadoRepository.buscarPorCodigoValidacao(codigoValidacao);
        if (certificado == null) {
            throw new CertificadoNaoEncontradoException("Certificado nao encontrado para o codigo informado.");
        }

        return certificadoMapper.toResponse(certificado);
    }

    private Certificado obterOuEmitirCertificado(Long cursoId) {
        Usuario usuario = buscarUsuarioAutenticado();
        Curso curso = cursoRepository.findById(cursoId);
        if (curso == null) {
            throw new CursoNaoEncontradoException("Curso nao encontrado.");
        }

        var matricula = matriculaRepository.buscarPorUsuarioECurso(usuario.id, cursoId);
        if (matricula == null || !matricula.ativa) {
            throw new AlunoNaoMatriculadoException("Usuario nao esta matriculado ou a matricula nao esta ativa.");
        }

        long totalAulas = aulaRepository.count("curso.id", cursoId);
        long aulasConcluidas = progressoRepository.contarAulasConcluidasPorUsuarioECurso(usuario.id, cursoId);

        if (totalAulas == 0 || totalAulas != aulasConcluidas) {
            throw new CursoNaoConcluidoException("Usuario nao concluiu todas as aulas do curso.");
        }

        Certificado certificadoExistente = certificadoRepository.buscarPorUsuarioECurso(usuario.id, cursoId);
        if (certificadoExistente != null) {
            return certificadoExistente;
        }

        Certificado certificado = new Certificado();
        certificado.usuario = usuario;
        certificado.curso = curso;
        certificado.dataEmissao = LocalDateTime.now();
        certificado.codigoValidacao = UUID.randomUUID().toString();

        certificadoRepository.persist(certificado);
        return certificado;
    }

    private Usuario buscarUsuarioAutenticado() {
        String email = jsonWebToken.getSubject();
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario nao encontrado.");
        }
        return usuario;
    }
}
