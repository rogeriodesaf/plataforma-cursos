package org.rogeriodesaf.usuario.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.rogeriodesaf.usuario.enums.Perfil;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntity {

    public String nome;

    public String email;

    public String senha;

    @Enumerated(EnumType.STRING)
    public Perfil perfil;

    public String tokenRecuperacaoSenha;

    public LocalDateTime tokenRecuperacaoExpiraEm;
}

