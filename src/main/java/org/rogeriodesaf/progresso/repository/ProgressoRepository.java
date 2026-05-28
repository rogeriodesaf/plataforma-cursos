package org.rogeriodesaf.progresso.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.progresso.entity.Progresso;

@ApplicationScoped
public class ProgressoRepository implements PanacheRepository<Progresso> {

    public Progresso buscaPorUsuarioEAula(Long usuarioId, Long aulaId) {
        return find(
                "usuario.id = ?1 and aula.id = ?2", usuarioId, aulaId)
            .firstResult();
    }

    public Long contarAulasConcluidasPorUsuarioECurso(Long usuarioId, Long cursoId) {
        return count(
                "usuario.id = ?1 and curso.id = ?2", usuarioId, cursoId);
    }

    public Long contarAulasConcluidasPorCurso(
            Long usuarioId,
            Long cursoId,
            Integer ordemAtual) {
        return count(
                "usuario.id = ?1 and curso.id ?2 and aula.ordem < ?3",
                usuarioId,
                cursoId,
                ordemAtual);
    }

    public Long contarAulasAnterioresConcluidas(Long usuarioId, Long cursoId, Integer ordemAtual) {
        return count(
                "usuario.id = ?1 and curso.id = ?2 and aula.ordem < ?3",
                usuarioId,
                cursoId,
                ordemAtual);
    }
}
