package org.rogeriodesaf.curso.exception;


import org.rogeriodesaf.shared.exception.BusinessException;

public class CursoJaCadastradoException extends BusinessException {
    public CursoJaCadastradoException(String message) {
        super(message);
    }
}
