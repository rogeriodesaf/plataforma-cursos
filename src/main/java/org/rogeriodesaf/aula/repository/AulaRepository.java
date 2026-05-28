package org.rogeriodesaf.aula.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.aula.entity.Aula;

import java.util.List;

@ApplicationScoped
public class AulaRepository implements PanacheRepository <Aula>{

    public List<Aula> findByCurso(Long cursoId){
        return list("curso.id", cursoId);
    }

    public Long contarAulasPorCurso(Long cursoId){
        return count("curso.id", cursoId);
    }

    public List<Aula> listarProfessor(Long professorId){return list("professor.id = ?1 and ativa = true", professorId);
    }
}
