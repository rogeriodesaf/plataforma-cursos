package org.rogeriodesaf.certificado.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class CertificadoNaoEncontradoException extends BusinessException {

    public CertificadoNaoEncontradoException(String message) {
        super(message);
    }
}
