package org.rogeriodesaf.matricula.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.matricula.dto.MatriculaResponseDTO;
import org.rogeriodesaf.matricula.entity.Matricula;

@ApplicationScoped
public class MatriculaMapper {

    public MatriculaResponseDTO toResponse(Matricula matricula) {
      return new MatriculaResponseDTO(
            matricula.id,
            matricula.usuario.id,
            matricula.usuario.nome,
            matricula.curso.id,
            matricula.curso.titulo,
              matricula.ativa,
            matricula.dataMatricula
      );
    }
}
