package org.rogeriodesaf.curso.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class CursoNaoEncontradoException extends BusinessException {
    public CursoNaoEncontradoException(String message) {
        super(message);
    }
}
