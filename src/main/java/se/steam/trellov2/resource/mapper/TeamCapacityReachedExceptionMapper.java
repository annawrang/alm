package se.steam.trellov2.resource.mapper;

import se.steam.trellov2.service.exception.TeamCapacityReachedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

@Provider
public final class TeamCapacityReachedExceptionMapper implements ExceptionMapper<TeamCapacityReachedException> {
    @Override
    public Response toResponse(TeamCapacityReachedException e) {
        return Response.status(NOT_ACCEPTABLE)
                .entity(Collections.singletonMap("Error 406", e.getMessage()))
                .build();
    }
}
