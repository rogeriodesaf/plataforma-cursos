package org.rogeriodesaf.curso.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class CursoNaoConcluidoException extends BusinessException {
    public CursoNaoConcluidoException(String message) {
        super(message);
    }
}
