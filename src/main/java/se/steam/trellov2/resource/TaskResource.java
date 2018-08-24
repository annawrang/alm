package se.steam.trellov2.resource;

import org.springframework.stereotype.Component;
import se.steam.trellov2.model.Issue;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.resource.mapper.Secured;
import se.steam.trellov2.service.IssueService;
import se.steam.trellov2.service.TaskService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("tasks")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public final class TaskResource {

    private final TaskService taskService;
    private final IssueService issueService;

    @Context
    private UriInfo uriInfo;

    public TaskResource(TaskService taskService, IssueService issueService) {
        this.taskService = taskService;
        this.issueService = issueService;
    }

    @PUT
    @Secured
    @Path("{id}")
    public void updateTask(@PathParam("id") UUID id, Task task) {
        taskService.update(new Task(id, task.getText(), task.getStatus(), task.getDate()));
    }

    @GET
    public List<Task> getTasksWithIssue() {
        return taskService.getWithIssue();
    }

    @POST
    @Secured
    @Path("{id}/issues")
    public Response createIssue(@PathParam("id") UUID id, Issue issue) {
        return Response.created(uriInfo.getAbsolutePathBuilder().path(issueService.save(id, issue).getId().toString()).build()).build();
    }

    @DELETE
    @Secured
    @Path("{id}")
    public void removeTask(@PathParam("id") UUID id) {
        taskService.remove(id);
    }
}
