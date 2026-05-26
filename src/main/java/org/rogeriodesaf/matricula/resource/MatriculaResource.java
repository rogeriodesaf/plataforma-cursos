package org.rogeriodesaf.matricula.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.matricula.dto.MatriculaResponseDTO;
import org.rogeriodesaf.matricula.service.MatriculaService;

import java.net.URI;

@Path("/matriculas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MatriculaResource {

    private final MatriculaService matriculaService;

    public MatriculaResource(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @POST
    @Path("/cursos/{cursoId}")
    @RolesAllowed("ADMIN")
    public Response matricular(@PathParam("cursoId") Long cursoId) {
        MatriculaResponseDTO response = matriculaService.matricularUsuarioEmCurso(cursoId);
        URI uri = URI.create("/matriculas/" + response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }
}
