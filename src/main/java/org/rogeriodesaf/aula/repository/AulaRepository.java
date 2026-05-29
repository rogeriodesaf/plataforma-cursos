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

    public List<Aula> findByCursoAtivo(Long cursoId) {
        return list("curso.id = ?1 and ativa = true", cursoId);
    }

    public Long contarAulasPorCurso(Long cursoId){
        return count("curso.id", cursoId);
    }

    public List<Aula> listarProfessor(Long professorId){return list("professor.id = ?1 and ativa = true", professorId);
    }

    public Aula findByCursoAndTitulo(Long cursoId, String titulo) {
        return find("curso.id = ?1 and titulo = ?2", cursoId, titulo).firstResult();
    }

    public Aula buscarAulaPorCursoEOrdem(Long cursoId, Integer ordem){
        return find("curso.id = ?1 and ordem < ?2 and ativa = true", cursoId, ordem).firstResult();
    }

    public Long contarAulasAnteriores(Long cursoId, Integer ordemAtual){
        return count("curso.id = ?1 and ordem < ?2 and ativa = true", cursoId, ordemAtual);
    }
}
