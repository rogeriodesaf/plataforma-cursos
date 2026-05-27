package org.rogeriodesaf.aula.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.professor.entity.Professor;

@Entity
@Table(name = "aulas")
public class Aula extends PanacheEntity {

    public String titulo;

    public String descricao;

    public String urlVideo;

    public Integer ordem = 0;

    public Integer duracaoMinutos;

    public Boolean ativa;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    public Curso curso;

    @ManyToOne
    public Professor professor;


}
