package org.rogeriodesaf.professor.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class ProfessorNaoEncontradoException extends BusinessException {
    public ProfessorNaoEncontradoException(String message) {
        super(message);
    }
}
