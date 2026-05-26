package org.rogeriodesaf.usuario.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class SenhaInvalidaException extends BusinessException {
    public SenhaInvalidaException(String message) {
        super(message);
    }
}
