package org.rogeriodesaf.professor.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.professor.dto.ProfessorRequestDTO;
import org.rogeriodesaf.professor.dto.ProfessorResponseDTO;
import org.rogeriodesaf.professor.service.ProfessorService;

import java.net.URI;

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
    public Response criar(@Valid ProfessorRequestDTO professorRequestDTO){
        ProfessorResponseDTO response = professorService.criarProfessor(professorRequestDTO);
        URI uri = URI.create("/professores/" + response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }
}
