package org.rogeriodesaf.aula.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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
    public Response criar(@Valid AulaRequestDTO aulaRequestDTO){
        AulaResponseDTO response = aulaService.criar(aulaRequestDTO);

        URI uri = URI.create("/aulas/"+ response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }

    @GET
    @Path("/curso/{id}")
    @RolesAllowed("ADMIN")
    public Response listarTodasAulas(Long id){
        List<AulaResponseDTO> response = aulaService.listarAulas(id);
        return  Response.ok(response).build();
    }

    @GET
    @Path("/teste-admin")
    @RolesAllowed("ADMIN")
    public Response testeAdmin() {
        return Response.ok("Acesso permitido para ADMIN").build();
    }

    @GET
    @Path("/professor/{professorId}")
    @RolesAllowed("ADMIN")
    public Response listarAulasPorProfessor(@PathParam("professorId") Long professorId) {
        List<AulaResponseDTO> response = aulaService.listarAulasPorProfessor(professorId);
        return Response.ok(response).build();
    }
}
