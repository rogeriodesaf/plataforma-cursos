package org.rogeriodesaf.usuario;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntity {

    public String nome;

    public String email;

    public String senha;

    public String role;
}
