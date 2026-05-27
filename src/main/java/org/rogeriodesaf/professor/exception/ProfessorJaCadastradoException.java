package org.rogeriodesaf.professor.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class ProfessorJaCadastradoException extends BusinessException {
    public ProfessorJaCadastradoException(String message) {
        super(message);
    }
}
