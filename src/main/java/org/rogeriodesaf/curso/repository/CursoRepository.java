package org.rogeriodesaf.curso.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.curso.entity.Curso;

import java.util.List;

@ApplicationScoped
public class CursoRepository implements PanacheRepository <Curso> {

    public Curso findByTitulo(String titulo){
       return  find("titulo", titulo).firstResult();
    }
    public List<Curso> findByAtivo(){
        return  list("ativo", true);
    }
}
