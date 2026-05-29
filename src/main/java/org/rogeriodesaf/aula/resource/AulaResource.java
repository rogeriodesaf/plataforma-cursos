package org.rogeriodesaf.aula.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.aula.dto.AulaRequestDTO;
import org.rogeriodesaf.aula.dto.AulaResponseDTO;
import org.rogeriodesaf.aula.service.AulaService;

import java.net.URI;
import java.util.List;

@Path("/aulas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AulaResource {

    private final AulaService aulaService;

    @Inject
    public AulaResource(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response criar(@Valid AulaRequestDTO aulaRequestDTO) {
        AulaResponseDTO response = aulaService.criar(aulaRequestDTO);

        URI uri = URI.create("/aulas/" + response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response buscarPorId(@PathParam("id") Long id) {
        AulaResponseDTO response = aulaService.buscarPorId(id);
        return Response.ok(response).build();
    }

    @GET
    @Path("/curso/{cursoId}")
    @RolesAllowed("ADMIN")
    public Response listarTodasAulas(@PathParam("cursoId") Long cursoId) {
        List<AulaResponseDTO> response = aulaService.listarAulas(cursoId);
        return Response.ok(response).build();
    }

    @GET
    @Path("/professor/{professorId}")
    @RolesAllowed("ADMIN")
    public Response listarAulasPorProfessor(@PathParam("professorId") Long professorId) {
        List<AulaResponseDTO> response = aulaService.listarAulasPorProfessor(professorId);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}/atualizar")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, @Valid AulaRequestDTO aulaRequestDTO) {
        AulaResponseDTO response = aulaService.atualizar(id, aulaRequestDTO);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/desativar")
    @RolesAllowed("ADMIN")
    public Response desativar(@PathParam("id") Long id) {
        aulaService.desativar(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/ativar")
    @RolesAllowed("ADMIN")
    public Response ativar(@PathParam("id") Long id) {
        AulaResponseDTO response = aulaService.ativar(id);
        return Response.ok(response).build();
    }

    @GET
    @Path("/curso/{cursoId}/ativas")
    @RolesAllowed("USER")
    public Response listarAulasPorCurso(@PathParam("cursoId") Long cursoId) {
        List<AulaResponseDTO> response = aulaService.listarAulasAtivas(cursoId);
        return Response.ok(response).build();
    }
}
