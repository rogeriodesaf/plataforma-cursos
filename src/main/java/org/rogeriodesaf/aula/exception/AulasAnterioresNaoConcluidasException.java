package org.rogeriodesaf.aula.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class AulasAnterioresNaoConcluidasException extends BusinessException {
    public AulasAnterioresNaoConcluidasException(String message) {
        super(message);
    }
}
