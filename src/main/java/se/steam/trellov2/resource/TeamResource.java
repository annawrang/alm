package se.steam.trellov2.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import se.steam.trellov2.model.*;
import se.steam.trellov2.resource.mapper.Secured;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.resource.parameter.TaskInput;
import se.steam.trellov2.service.IssueService;
import se.steam.trellov2.service.TaskService;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.SERVER_SENT_EVENTS;

@Component
@Path("teams")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public final class TeamResource {

    @Context
    private UriInfo uriInfo;
    @Context
    private Sse sse;

    private final TeamService teamService;
    private final UserService userService;
    private final TaskService taskService;
    private final IssueService issueService;

    private final Map<Team, SseBroadcaster> sseBroadcasters;

    private SseBroadcaster getBroadcaster(Team team) {
        if (!sseBroadcasters.containsKey(team)) {
            sseBroadcasters.put(team, sse.newBroadcaster());
        }
        return sseBroadcasters.get(team);
    }

    @GET
    @Path("{id}/subscribe")
    @Produces(SERVER_SENT_EVENTS)
    @SuppressWarnings(value = "all")
    public void subscribe(@PathParam("id") UUID teamId, @Context SseEventSink eventSink) {
        Team team = teamService.get(teamId);
        getBroadcaster(team).register(eventSink);
        eventSink.send(sse.newEvent(String.format("You are now subscribed to team \"%s\"", team.getName())));
    }

    public TeamResource(TeamService teamService, UserService userService, TaskService taskService, IssueService issueService) {
        this.teamService = teamService;
        this.userService = userService;
        this.taskService = taskService;
        this.issueService = issueService;
        sseBroadcasters = new HashMap<>();
    }

    @POST
    @Secured
    public Response createTeam(Team team) {
        return Response.created(getCreatedToDoUri(uriInfo, teamService.save(team))).build();
    }

    @GET
    @Path("{teamId}")
    public Response getTeam(@PathParam("teamId") UUID id) {
        return Response.ok(teamService.get(id)).build();
    }

    @PUT
    @Secured
    @Path("{teamId}")
    public void updateTeam(@PathParam("teamId") UUID teamId, Team team) {
        teamService.update(new Team(teamId, team.getName()));
    }

    @GET
    public Response getAllTeams() {
        return Response.ok(teamService.getAll()).build();
    }

    @GET
    @Path("{teamId}/users")
    public Response getAllUsersByTeam(@PathParam("teamId") UUID teamId) {
        return Response.ok(userService.getByTeam(teamId)).build();
    }

    @GET
    @Path("{teamId}/issues")
    public Page<Issue> getAllIssuesByTeam(@PathParam("teamId") UUID teamId, @BeanParam PagingInput pagingInput) {
        return issueService.getPage(teamId, pagingInput);
    }

    @GET
    @Path("{teamId}/tasks")
    public Page<Task> getAllTasksByTeam(@PathParam("teamId") UUID teamId,
                                        @BeanParam PagingInput pagingInput,
                                        @BeanParam TaskInput taskInput) {
        return taskService.getByTeamAsPage(teamId, pagingInput, taskInput);
    }

    @POST
    @Secured
    @Path("{teamId}/tasks")
    public Response createTaskByTeam(@PathParam("teamId") UUID teamId, Task task) {
        Pair<Team, Task> pair = taskService.save(teamId, task);
        notify(pair.getFirst(), String.format("task \"%s\" added to team \"%s\"",
                pair.getSecond().getText(),
                pair.getFirst().getName()));
        return Response.created(getCreatedToDoUri(uriInfo, pair.getSecond())).build();
    }

    @PUT
    @Secured
    @Path("{teamId}/users/{userId}")
    public void addUserToTeam(@PathParam("teamId") UUID teamId,
                              @PathParam("userId") UUID userId) {
        Pair<Team, User> pair = teamService.addUserToTeam(teamId, userId);
        notify(pair.getFirst(), String.format("User \"%s\" joined team \"%s\"",
                pair.getSecond().getUsername(),
                pair.getFirst().getName()));
    }

    @DELETE
    @Secured
    @Path("{teamId}/users/{userId}")
    public void leaveTeam(@PathParam("teamId") UUID teamId,
                          @PathParam("userId") UUID userId) {
        userService.leaveTeam(teamId, userId);
    }

    @DELETE
    @Secured
    @Path("{id}")
    public void removeTeam(@PathParam("id") UUID id) {
        teamService.remove(id);
    }

    private URI getCreatedToDoUri(UriInfo uriInfo, AbstractModel entity) {
        return uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build();
    }

    private void notify(Team team, String message) {
        getBroadcaster(team).broadcast(sse.newEvent(message));
    }
}