package org.rogeriodesaf.dev;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.enums.Perfil;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

@ApplicationScoped
public class DevDataLoader {

    private final UsuarioRepository usuarioRepository;

    public DevDataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    void onStart(@Observes StartupEvent event) {
        if (LaunchMode.current() != LaunchMode.DEVELOPMENT) {
            return;
        }

        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario admin = new Usuario();
        admin.nome = "Administrador";
        admin.email = "admin@plataforma.com";
        admin.senha = BcryptUtil.bcryptHash("admin123");
        admin.perfil = Perfil.ADMIN;
        usuarioRepository.persist(admin);

        Usuario aluno = new Usuario();
        aluno.nome = "Aluno Demo";
        aluno.email = "aluno@plataforma.com";
        aluno.senha = BcryptUtil.bcryptHash("aluno123");
        aluno.perfil = Perfil.USER;
        usuarioRepository.persist(aluno);
    }
}
