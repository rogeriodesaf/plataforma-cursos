package org.rogeriodesaf.professor.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "professores")
public class Professor extends PanacheEntity {
    public String nome;
    public String email;
    public String especialidade;
    public boolean ativo;
}
