package org.rogeriodesaf.curso.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rogeriodesaf.curso.dto.CursoRequestDTO;
import org.rogeriodesaf.curso.dto.CursoResponseDTO;
import org.rogeriodesaf.curso.service.CursoService;

import java.net.URI;

@Path("/cursos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CursoResource {

    private final CursoService cursoService;

    public CursoResource(CursoService cursoService) {
        this.cursoService = cursoService;
    }


    @POST
   // @RolesAllowed("ADMIN")
    public Response cadastrar(@Valid CursoRequestDTO cursoRequestDTO){
        CursoResponseDTO response = cursoService.cadastrar(cursoRequestDTO);
        URI uri = URI.create("/cursos/"+ response.id());
        return Response.created(uri)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id")Long id){
        CursoResponseDTO response = cursoService.buscarPorId(id);
        return Response.ok(response).build();
    }

    @GET
    public Response listarTodos() {
        return Response.ok(cursoService.listarTodos()).build();
    }

    @PATCH
    @Path("/{id}/desativar")
    public Response desativarCurso(@PathParam("id") Long id) {
        cursoService.desativarCurso(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/ativar")
    public Response reativarCurso(@PathParam("id") Long id) {
        cursoService.ativarCurso(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id")Long id, @Valid CursoRequestDTO cursoRequestDTO){
        CursoResponseDTO response = cursoService.atualizarCurso(id,cursoRequestDTO);
        return Response.ok(response).build();
    }
}
