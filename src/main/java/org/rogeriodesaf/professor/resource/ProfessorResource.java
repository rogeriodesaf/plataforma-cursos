package org.rogeriodesaf.professor.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.professor.dto.ProfessorRequestDTO;
import org.rogeriodesaf.professor.dto.ProfessorResponseDTO;
import org.rogeriodesaf.professor.service.ProfessorService;

import java.net.URI;
import java.util.List;

@Path("/professores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessorResource {

    private final ProfessorService professorService;

    public ProfessorResource(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response criar(@Valid ProfessorRequestDTO professorRequestDTO) {
        ProfessorResponseDTO response = professorService.criarProfessor(professorRequestDTO);
        URI uri = URI.create("/professores/" + response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }

    @GET
    @RolesAllowed("ADMIN")
    public Response listarProfessoresAdmin() {
        List<ProfessorResponseDTO> response = professorService.listarTodosParaAdmin();
        return Response.ok(response).build();
    }

    @GET
    @Path("/ativos")
    @RolesAllowed({"ADMIN", "USER"})
    public Response listarProfessoresAtivos() {
        List<ProfessorResponseDTO> response = professorService.listarProfessores();
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response listarProfessorPorId(@PathParam("id") Long id) {
        ProfessorResponseDTO response = professorService.listarProfessorPorId(id);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}/atualizar")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, @Valid ProfessorRequestDTO professorRequestDTO) {
        ProfessorResponseDTO response = professorService.atualizarProfessor(id, professorRequestDTO);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/desativar")
    @RolesAllowed("ADMIN")
    public Response desativar(@PathParam("id") Long id) {
        professorService.desativarProfessor(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/ativar")
    @RolesAllowed("ADMIN")
    public Response ativar(@PathParam("id") Long id) {
        ProfessorResponseDTO response = professorService.ativarProfessor(id);
        return Response.ok(response).build();
    }
}
