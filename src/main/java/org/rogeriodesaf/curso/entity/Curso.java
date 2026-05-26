package org.rogeriodesaf.curso.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso extends PanacheEntity {


    public String titulo;
    public String descricao;
    public Boolean ativo = true;

}
