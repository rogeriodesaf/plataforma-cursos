package org.rogeriodesaf.certificado.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.rogeriodesaf.certificado.dto.CertificadoResponseDTO;
import org.rogeriodesaf.certificado.entity.Certificado;

@ApplicationScoped
public class CertificadoMapper {

    public CertificadoResponseDTO toResponse(Certificado certificado) {
        return new CertificadoResponseDTO(
                certificado.id,
                certificado.usuario.nome,
                certificado.curso.titulo,
                certificado.dataEmissao,
                certificado.codigoValidacao
        );
    }
}
