package org.rogeriodesaf.usuario.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class TokenRecuperacaoInvalidoException extends BusinessException {
    public TokenRecuperacaoInvalidoException(String message) {
        super(message);
    }
}
