package org.rogeriodesaf.certificado.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.certificado.dto.CertificadoResponseDTO;
import org.rogeriodesaf.certificado.service.CertificadoService;

import java.net.URI;

@Path("/certificados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CertificadoResource {

    private final CertificadoService certificadoService;

    public CertificadoResource(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    @POST
    @Path("/cursos/{cursoId}")
    @RolesAllowed("USER")
    public Response gerarCertificado(@PathParam("cursoId") Long cursoId) {

       CertificadoResponseDTO response = certificadoService.obterCertificadoPorCurso(cursoId);
       URI uri = URI.create("/certificados/" + response.id());
       return Response.created(uri)
               .entity(response)
                .build();
    }
}
