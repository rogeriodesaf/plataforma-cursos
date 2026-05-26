package org.rogeriodesaf.progresso.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.progresso.entity.Progresso;

@ApplicationScoped
public class ProgressoRepository implements PanacheRepository<Progresso> {

    public Progresso buscaPorUsuarioEAula(Long usuarioId, Long aulaId) {
        return find(
                "usuarioId = ?1 and aulaId = ?2", usuarioId, aulaId)
            .firstResult();
    }

    public Long contarAulasConcluidasPorUsuarioECurso(Long usuarioId, Long cursoId) {
        return count(
                "usuarioId = ?1 and cursoId = ?2", usuarioId, cursoId);
    }
}
