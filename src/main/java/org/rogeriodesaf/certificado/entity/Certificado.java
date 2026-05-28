package org.rogeriodesaf.certificado.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.rogeriodesaf.curso.entity.Curso;
import org.rogeriodesaf.usuario.entity.Usuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificados")
public class Certificado extends PanacheEntity {

    @ManyToOne
    public Curso curso;

    @ManyToOne
    public Usuario usuario;

    public LocalDateTime dataEmissao;

    public String codigoValidacao;
}
