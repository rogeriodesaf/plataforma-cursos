package org.rogeriodesaf.progresso.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.rogeriodesaf.aula.entity.Aula;
import org.rogeriodesaf.aula.exception.AulaJaConcluidaException;
import org.rogeriodesaf.aula.exception.AulaNaoEncontradaException;
import org.rogeriodesaf.aula.repository.AulaRepository;
import org.rogeriodesaf.aula.service.AulaService;
import org.rogeriodesaf.progresso.dto.ProgressoResponseDTO;
import org.rogeriodesaf.progresso.entity.Progresso;
import org.rogeriodesaf.progresso.repository.ProgressoRepository;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.exception.UsuarioNaoEncontradoException;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

@ApplicationScoped
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final UsuarioRepository usuarioRepository;
   private final JsonWebToken jsonWebToken;
    private final AulaRepository aulaRepository;

    public ProgressoService(ProgressoRepository progressoRepository, UsuarioRepository usuarioRepository, UsuarioRepository usuarioRepository1, JsonWebToken jsonWebToken, AulaRepository aulaRepository) {
        this.progressoRepository = progressoRepository;
        this.usuarioRepository = usuarioRepository1;
        this.jsonWebToken = jsonWebToken;
        this.aulaRepository = aulaRepository;
    }

    @Transactional
    public void concluirAula(Long aulaId) {

        String email = jsonWebToken.getSubject();

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }


        Aula aula = aulaRepository.findById(aulaId);
        if (aula == null) {
            throw new AulaNaoEncontradaException("Aula não encontrada");
        }

        Progresso progressoExistente = progressoRepository.buscaPorUsuarioEAula(usuario.id, aulaId);
        if (progressoExistente != null){
           throw new AulaJaConcluidaException("Aula já concluída por este usuário");
        }

        Progresso progresso = new Progresso();
        progresso.usuario = usuario;
        progresso.aula = aula;
        progresso.curso = aula.curso;
        progresso.dataConclusao = java.time.LocalDateTime.now();

        progressoRepository.persist(progresso);

    }

    @Transactional
    public ProgressoResponseDTO consultarProgresso(Long cursoId) {
        String email = jsonWebToken.getSubject();

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        Long aulasConcluidas = progressoRepository.contarAulasConcluidasPorUsuarioECurso(usuario.id, cursoId);
        Long totalAulas = aulaRepository.contarAulasPorCurso(cursoId);

        double percentualConclusao = totalAulas > 0 ?
                (aulasConcluidas.doubleValue() / totalAulas.doubleValue()) * 100
                : 0;

        return new ProgressoResponseDTO(
                cursoId,
                totalAulas,
                aulasConcluidas,
                percentualConclusao
        );
    }

}
