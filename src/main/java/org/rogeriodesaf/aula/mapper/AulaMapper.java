package org.rogeriodesaf.aula.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.aula.dto.AulaRequestDTO;
import org.rogeriodesaf.aula.dto.AulaResponseDTO;
import org.rogeriodesaf.aula.entity.Aula;

@ApplicationScoped
public class AulaMapper {

    public Aula toEntity (AulaRequestDTO aulaRequestDTO){
        Aula aula = new Aula();
        aula.titulo = aulaRequestDTO.titulo();
        aula.descricao = aulaRequestDTO.descricao();
        aula.urlVideo = aulaRequestDTO.urlVideo();
        aula.duracaoMinutos = aulaRequestDTO.duracaoMinutos();


        return aula;
    }

    public AulaResponseDTO toResponse (Aula aula){
        return new AulaResponseDTO(
                aula.id,
                aula.titulo,
                aula.descricao,
                aula.urlVideo,
                aula.ordem,
                aula.duracaoMinutos,
                aula.ativa,
                aula.curso.id,
                aula.curso.titulo
        );
    }

}
