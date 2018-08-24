package se.steam.trellov2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.resource.parameter.TaskInput;

import java.util.List;
import java.util.UUID;

public interface TaskService extends Service<Task> {

    Pair<Team, Task> save(UUID teamId, Task entity);

    List<Task> getByUser(UUID userId);

    List<Task> getWithIssue();

    Page<Task> getByTeamAsPage(UUID teamId, PagingInput pagingInput, TaskInput taskInput);

    void dropTask(UUID userId, UUID taskId);

}