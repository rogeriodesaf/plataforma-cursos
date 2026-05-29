package org.rogeriodesaf.usuario.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.usuario.entity.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
   public  Usuario findByEmail(String email){
        return find("email", email).firstResult();
   }

   public Usuario findByResetToken(String token) {
        return find("tokenRecuperacaoSenha", token).firstResult();
   }
}
