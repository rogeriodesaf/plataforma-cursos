package org.rogeriodesaf.usuario.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.rogeriodesaf.seguranca.JwtService;
import org.rogeriodesaf.usuario.dto.LoginRequestDTO;
import org.rogeriodesaf.usuario.dto.LoginResponseDTO;
import org.rogeriodesaf.usuario.dto.UsuarioRequestDTO;
import org.rogeriodesaf.usuario.dto.UsuarioResponseDTO;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.enums.Perfil;
import org.rogeriodesaf.usuario.exception.SenhaInvalidaException;
import org.rogeriodesaf.usuario.exception.UsuarioNaoEncontradoException;
import org.rogeriodesaf.usuario.mapper.UsuarioMapper;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Inject
    public UsuarioService(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioRequestDTO.email());
        if (usuario != null) {
            throw new RuntimeException("Email já cadastrado");
        }

        usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        usuario.perfil = Perfil.ADMIN;
        usuario.senha = BcryptUtil.bcryptHash(usuarioRequestDTO.senha());

        usuarioRepository.persist(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequestDTO.email());
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        boolean senhaValida = BcryptUtil.matches(loginRequestDTO.senha(), usuario.senha);
        if (!senhaValida) {
            throw new SenhaInvalidaException("Senha inválida");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponseDTO(token);

    }
}
