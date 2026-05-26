package org.rogeriodesaf.matricula.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.matricula.entity.Matricula;

@ApplicationScoped
public class MatriculaRepository implements PanacheRepository<Matricula> {
    public Matricula buscarPorUsuarioECurso(Long usuarioId, Long cursoId) {
        return find(
                "usuario.id = ?1 and curso.id = ?2",
                usuarioId,
                cursoId
        ).firstResult();
    }
}
