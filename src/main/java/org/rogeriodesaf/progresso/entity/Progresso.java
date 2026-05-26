package org.rogeriodesaf.progresso.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.rogeriodesaf.aula.entity.Aula;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.usuario.entity.Usuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "progresso")
public class Progresso extends PanacheEntity {

    @ManyToOne
    public Usuario usuario;

    @ManyToOne
    public Aula aula;

    @ManyToOne
    public Curso curso;

    public LocalDateTime dataConclusao;


}
