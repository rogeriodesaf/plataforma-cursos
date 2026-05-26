package org.rogeriodesaf.matricula.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.curso.dto.CursoResponseDTO;
import org.rogeriodesaf.matricula.dto.MatriculaResponseDTO;
import org.rogeriodesaf.matricula.entity.Matricula;
import org.rogeriodesaf.matricula.service.MatriculaService;

import java.net.URI;
import java.util.List;

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
    @RolesAllowed("USER")
    public Response matricular(@PathParam("cursoId") Long cursoId) {
        MatriculaResponseDTO response = matriculaService.matricularUsuarioEmCurso(cursoId);
        URI uri = URI.create("/matriculas/" + response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }

    @GET
    @Path("/minhas")
    @RolesAllowed("USER")
    public Response listarMinhasMatriculas() {
        List<MatriculaResponseDTO> response = matriculaService.listarMinhasMatriculas();
        return Response.ok(response).build();
    }

    @GET
    @Path("/cursos/{cursoId}/alunos")
    @RolesAllowed("ADMIN")
    public Response listarAlunosPorCurso(@PathParam("cursoId") Long cursoId){
        List<MatriculaResponseDTO> response =
                matriculaService.listarAlunosPorCurso(cursoId);
        return Response.ok().build();
    }

    @GET
    @Path("/usuarios/{usuarioId}/cursos")
    @RolesAllowed("ADMIN")
    public Response listarCursosPorUsuario(@PathParam("usuarioId") Long usuarioId){
        List<MatriculaResponseDTO> response = matriculaService.listarCursosPorUsuario(usuarioId);

        return Response.ok(response).build();
    }
}
