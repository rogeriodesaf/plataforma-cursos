package org.rogeriodesaf.aula.exception;

import org.rogeriodesaf.shared.exception.BusinessException;


public class AulaJaExisteException extends BusinessException {
    public AulaJaExisteException(String message) {
        super(message);
    }
}
