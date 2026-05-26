package org.rogeriodesaf.progresso.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class AlunoNaoMatriculadoException extends BusinessException {
    public AlunoNaoMatriculadoException(String message) {
        super(message);
    }
}
