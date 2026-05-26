package org.rogeriodesaf.seguranca;


import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.usuario.entity.Usuario;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {

  public String gerarToken(Usuario usuario){
      return Jwt.issuer("plataforma-cursos")
              .subject(usuario.email)
              .groups(Set.of(usuario.perfil.name()))
              .expiresIn(Duration.ofHours(2))
              .sign();
  }
}
