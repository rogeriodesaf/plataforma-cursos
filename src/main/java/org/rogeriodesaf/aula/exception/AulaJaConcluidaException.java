package org.rogeriodesaf.aula.exception;

import org.rogeriodesaf.shared.exception.BusinessException;

public class AulaJaConcluidaException extends BusinessException {
  public AulaJaConcluidaException(String message) {
    super(message);
  }
}
