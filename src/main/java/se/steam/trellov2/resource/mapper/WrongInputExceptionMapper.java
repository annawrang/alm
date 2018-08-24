package se.steam.trellov2.resource.mapper;

import se.steam.trellov2.service.exception.WrongInputException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public final class WrongInputExceptionMapper implements ExceptionMapper<WrongInputException> {
    @Override
    public Response toResponse(WrongInputException e) {
        return Response.status(BAD_REQUEST)
                .entity(Collections.singletonMap("Error 400", e.getMessage()))
                .build();
    }
}
