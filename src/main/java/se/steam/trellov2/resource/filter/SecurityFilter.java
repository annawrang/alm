package se.steam.trellov2.resource.filter;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import se.steam.trellov2.resource.mapper.Secured;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.Priorities.AUTHENTICATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.status;

@Component
@Secured
@Provider
@Priority(AUTHENTICATION)
public final class SecurityFilter implements ContainerRequestFilter {

    private Environment environment;

    public SecurityFilter(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void filter(ContainerRequestContext request) {
        if (!environment.getProperty("trellov2.authtoken").equalsIgnoreCase(request.getHeaderString("key")))
            request.abortWith(status(UNAUTHORIZED)
                    .entity(singletonMap("Error 401", "Missing/Invalid api key"))
                    .build());
    }
}

