package org.rogeriodesaf.certificado.dto;

import java.time.LocalDateTime;

public record CertificadoResponseDTO(
        Long id,
        String nomeAluno,
        String tituloCurso,
        LocalDateTime dataEmissao,
        String codigoValidacao
) {
}
