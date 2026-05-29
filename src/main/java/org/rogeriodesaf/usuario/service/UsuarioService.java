package org.rogeriodesaf.usuario.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.rogeriodesaf.seguranca.JwtService;
import org.rogeriodesaf.usuario.dto.ForgotPasswordRequestDTO;
import org.rogeriodesaf.usuario.dto.ForgotPasswordResponseDTO;
import org.rogeriodesaf.usuario.dto.LoginRequestDTO;
import org.rogeriodesaf.usuario.dto.LoginResponseDTO;
import org.rogeriodesaf.usuario.dto.ResetPasswordRequestDTO;
import org.rogeriodesaf.usuario.dto.UsuarioRequestDTO;
import org.rogeriodesaf.usuario.dto.UsuarioResponseDTO;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.enums.Perfil;
import org.rogeriodesaf.usuario.exception.SenhaInvalidaException;
import org.rogeriodesaf.usuario.exception.TokenRecuperacaoInvalidoException;
import org.rogeriodesaf.usuario.exception.UsuarioNaoEncontradoException;
import org.rogeriodesaf.usuario.mapper.UsuarioMapper;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
            throw new RuntimeException("Email ja cadastrado");
        }

        usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        usuario.perfil = Perfil.USER;
        usuario.senha = BcryptUtil.bcryptHash(usuarioRequestDTO.senha());

        usuarioRepository.persist(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequestDTO.email());
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario nao encontrado");
        }

        boolean senhaValida = BcryptUtil.matches(loginRequestDTO.senha(), usuario.senha);
        if (!senhaValida) {
            throw new SenhaInvalidaException("Senha invalida");
        }

        String token = jwtService.gerarToken(usuario);
        return new LoginResponseDTO(token);
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO usuarioRequestDTO, Perfil perfil) {
        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        usuario.perfil = perfil;
        usuario.senha = BcryptUtil.bcryptHash(usuarioRequestDTO.senha());

        usuarioRepository.persist(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        var usuarios = usuarioRepository.listAll();
        if (usuarios.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Nenhum usuario encontrado");
        }
        return usuarios.stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    public UsuarioResponseDTO listarUsuarioPorId(Long id) {
        var usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario nao encontrado com o id: " + id);
        }
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public ForgotPasswordResponseDTO solicitarRecuperacaoSenha(ForgotPasswordRequestDTO request, String baseUrl) {
        Usuario usuario = usuarioRepository.findByEmail(request.email());
        if (usuario == null) {
            return new ForgotPasswordResponseDTO(
                    "Se o email estiver cadastrado, um link de redefinicao sera disponibilizado.",
                    null,
                    null
            );
        }

        String token = UUID.randomUUID().toString();
        usuario.tokenRecuperacaoSenha = token;
        usuario.tokenRecuperacaoExpiraEm = LocalDateTime.now().plusMinutes(30);

        String link = baseUrl + "/redefinir-senha/" + token;
        return new ForgotPasswordResponseDTO(
                "Link de redefinicao gerado com sucesso. Em uma proxima etapa, ele podera ser enviado por email.",
                token,
                link
        );
    }

    @Transactional
    public void redefinirSenha(ResetPasswordRequestDTO request) {
        Usuario usuario = usuarioRepository.findByResetToken(request.token());
        if (usuario == null || usuario.tokenRecuperacaoExpiraEm == null) {
            throw new TokenRecuperacaoInvalidoException("Token de recuperacao invalido.");
        }

        if (usuario.tokenRecuperacaoExpiraEm.isBefore(LocalDateTime.now())) {
            throw new TokenRecuperacaoInvalidoException("Token de recuperacao expirado.");
        }

        usuario.senha = BcryptUtil.bcryptHash(request.novaSenha());
        usuario.tokenRecuperacaoSenha = null;
        usuario.tokenRecuperacaoExpiraEm = null;
    }
}
