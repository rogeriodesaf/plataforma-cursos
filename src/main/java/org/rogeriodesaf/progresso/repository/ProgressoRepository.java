package org.rogeriodesaf.progresso.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.progresso.entity.Progresso;

@ApplicationScoped
public class ProgressoRepository implements PanacheRepository<Progresso> {
}
