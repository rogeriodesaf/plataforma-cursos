package org.rogeriodesaf.matricula.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.usuario.entity.Usuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "matricula")
public class Matricula extends PanacheEntity {

    @ManyToOne
    public Usuario usuario;

    @ManyToOne
    public Curso curso;

    public LocalDateTime dataMatricula;

    public boolean ativa;


}
