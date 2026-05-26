package org.rogeriodesaf.curso.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.curso.dto.CursoRequestDTO;
import org.rogeriodesaf.curso.dto.CursoResponseDTO;
import org.rogeriodesaf.curso.entity.Curso;

@ApplicationScoped
public class CursoMapper {

    public Curso toEntity (CursoRequestDTO cursoRequestDTO){
        Curso curso = new Curso();
        curso.titulo = cursoRequestDTO.titulo();
        curso.descricao = cursoRequestDTO.descricao();

        return curso;
    }

    public CursoResponseDTO toResponse (Curso curso){
        return new CursoResponseDTO(
                curso.id,
                curso.titulo,
                curso.descricao,
                curso.ativo
        );
    }
}
