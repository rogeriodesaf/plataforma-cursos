package org.rogeriodesaf.usuario.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.usuario.dto.LoginRequestDTO;
import org.rogeriodesaf.usuario.dto.LoginResponseDTO;
import org.rogeriodesaf.usuario.dto.UsuarioRequestDTO;
import org.rogeriodesaf.usuario.dto.UsuarioResponseDTO;
import org.rogeriodesaf.usuario.entity.Usuario;
import org.rogeriodesaf.usuario.enums.Perfil;
import org.rogeriodesaf.usuario.repository.UsuarioRepository;
import org.rogeriodesaf.usuario.service.UsuarioService;

import java.net.URI;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class UsuarioResource {



    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioResource(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @POST
    @Path("/register")
    public Response cadastrar(@Valid UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO response = usuarioService.cadastrar(usuarioRequestDTO);

        URI uri = URI.create("/auth/" + response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequestDTO loginRequestDTO){
        LoginResponseDTO response = usuarioService.login(loginRequestDTO);
        return Response.ok(response).build();
    }

    @POST
    @Path("/register-admin")
    public Response cadastrarAdmin(@Valid UsuarioRequestDTO usuarioRequestDTO) {
       UsuarioResponseDTO response = usuarioService.cadastrar(usuarioRequestDTO, Perfil.ADMIN);

        URI uri = URI.create("/auth/" + response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }


}
