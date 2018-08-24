package se.steam.trellov2.resource.mapper;

import se.steam.trellov2.service.exception.InactiveEntityException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class InactiveEntityExceptionMapper implements ExceptionMapper<InactiveEntityException> {

    @Override
    public Response toResponse(InactiveEntityException exception) {
        return Response.status(NOT_FOUND)
                .entity(Collections.singletonMap("Error 404", exception.getMessage()))
                .build();
    }
}