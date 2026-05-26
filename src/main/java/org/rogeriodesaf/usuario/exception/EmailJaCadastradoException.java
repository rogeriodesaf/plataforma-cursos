package org.rogeriodesaf.usuario.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class EmailJaCadastradoException extends BusinessException {
    public EmailJaCadastradoException(String message) {
        super(message);
    }
}
