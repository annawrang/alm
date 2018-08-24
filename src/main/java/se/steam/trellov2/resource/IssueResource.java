package se.steam.trellov2.resource;

import org.springframework.stereotype.Component;
import se.steam.trellov2.model.Issue;
import se.steam.trellov2.resource.mapper.Secured;
import se.steam.trellov2.service.IssueService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("issues")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public final class IssueResource {

    private final IssueService issueService;

    @Context
    private UriInfo uriInfo;

    public IssueResource(IssueService issueService) {
        this.issueService = issueService;
    }

    @PUT
    @Secured
    @Path("{id}")
    public void updateIssue(@PathParam("id") UUID id, Issue issue) {
        issueService.update(new Issue(id, issue.getDescription()));
    }

    @DELETE
    @Secured
    @Path("{id}")
    public void deleteIssue(@PathParam("id") UUID id) {
        issueService.delete(id);
    }
}
