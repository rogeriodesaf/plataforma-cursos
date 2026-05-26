package org.rogeriodesaf.aula.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class AulaNaoEncontradaException extends BusinessException {
    public AulaNaoEncontradaException(String message) {
        super(message);
    }
}
