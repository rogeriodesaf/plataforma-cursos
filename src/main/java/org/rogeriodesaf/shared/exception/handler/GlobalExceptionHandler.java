package org.rogeriodesaf.shared.exception.handler;


import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.rogeriodesaf.shared.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<BusinessException>{

    @Override
    public Response toResponse(BusinessException exception) {
        Map<String,Object> erro = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "erro", exception.getMessage()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(erro)
                .build();
    }
}
