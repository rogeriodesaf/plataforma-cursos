package org.rogeriodesaf.matricula.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.matricula.entity.Matricula;

import java.util.List;

@ApplicationScoped
public class MatriculaRepository implements PanacheRepository<Matricula> {
    public Matricula buscarPorUsuarioECurso(Long usuarioId, Long cursoId) {
        return find(
                "usuario.id = ?1 and curso.id = ?2",
                usuarioId,
                cursoId
        ).firstResult();
    }

    public List<Matricula> listarPorUsuario(Long usuarioId) {
        return list("usuario.id = ?1 and ativa = true", usuarioId);
    }

    //admin listar alunos matriculados em um curso
    public List<Matricula> listarPorCurso(Long cursoId) {
        return list("curso.id = ?1 and ativa = true", cursoId);
    }


}
