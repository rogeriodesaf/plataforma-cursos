package org.rogeriodesaf.aula.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class AulaAnteriorNaoConcluida extends BusinessException {
    public AulaAnteriorNaoConcluida(String message) {
        super(message);
    }
}
