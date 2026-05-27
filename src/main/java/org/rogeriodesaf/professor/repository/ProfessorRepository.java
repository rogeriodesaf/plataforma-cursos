package org.rogeriodesaf.professor.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.professor.entity.Professor;

@ApplicationScoped
public class ProfessorRepository implements PanacheRepository <Professor> {

     public Professor findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
