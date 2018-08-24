package se.steam.trellov2.resource.mapper;

import se.steam.trellov2.service.exception.DataNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public final class DataNotFoundMapper implements ExceptionMapper<DataNotFoundException> {
    @Override
    public Response toResponse(DataNotFoundException e) {
        return Response.status(NOT_FOUND)
                .entity(Collections.singletonMap("Error 404", e.getMessage()))
                .build();
    }
}
