package org.rogeriodesaf.professor.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.professor.entity.Professor;

import java.util.List;

@ApplicationScoped
public class ProfessorRepository implements PanacheRepository <Professor> {

     public Professor findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<Professor> listAtivos() {
        return list("ativo", true);
    }

}
