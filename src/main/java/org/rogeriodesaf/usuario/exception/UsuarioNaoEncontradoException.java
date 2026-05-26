package org.rogeriodesaf.usuario.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class UsuarioNaoEncontradoException extends BusinessException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}
