package org.rogeriodesaf.progresso.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.progresso.service.ProgressoService;

@Path("/progresso")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProgressoResource
{

    private final ProgressoService progressoService;

    public ProgressoResource(ProgressoService progressoService) {
        this.progressoService = progressoService;
    }

    @POST
    @Path("/aulas/{aulaId}/concluir")
    @RolesAllowed("ADMIN")
    public Response concluirAula(@PathParam("aulaId") Long aulaId) {{
        progressoService.concluirAula(aulaId);
    }
        return Response.ok("Aula concluida com sucesso").build();
    }
}
