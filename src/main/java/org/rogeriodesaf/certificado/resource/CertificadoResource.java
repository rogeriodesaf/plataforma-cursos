package org.rogeriodesaf.certificado.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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

    @GET
    @Path("/cursos/{cursoId}/pdf")
    @RolesAllowed("USER")
    @Produces("application/pdf")
    public Response baixarCertificadoPdf(@PathParam("cursoId") Long cursoId) {
        byte[] pdf = certificadoService.gerarPdfPorCurso(cursoId);
        return Response.ok(pdf)
                .header("Content-Disposition", "attachment; filename=\"certificado-curso-" + cursoId + ".pdf\"")
                .build();
    }

    @GET
    @Path("/validacao/{codigo}")
    @PermitAll
    public CertificadoResponseDTO validarCertificado(@PathParam("codigo") String codigo) {
        return certificadoService.validarPorCodigo(codigo);
    }
}
