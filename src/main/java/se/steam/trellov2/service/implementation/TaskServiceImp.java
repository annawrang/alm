package se.steam.trellov2.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.repository.IssueRepository;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.model.AbstractEntity;
import se.steam.trellov2.repository.model.IssueEntity;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.repository.model.parse.ModelParser;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.resource.parameter.TaskInput;
import se.steam.trellov2.service.TaskService;
import se.steam.trellov2.service.business.Logic;
import se.steam.trellov2.service.exception.WrongInputException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static se.steam.trellov2.repository.model.parse.ModelParser.*;

@Service
final class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final IssueRepository issueRepository;
    private final Logic logic;

    private TaskServiceImp(TaskRepository taskRepository, IssueRepository issueRepository, Logic logic) {
        this.taskRepository = taskRepository;
        this.issueRepository = issueRepository;
        this.logic = logic;
    }

    @Override
    public Pair<Team, Task> save(UUID teamId, Task task) {
        TaskEntity taskEntity = taskRepository.save(toTaskEntity(task.assignId())
                .setTeamEntity(logic.validateTeam(teamId)));
        return Pair.of(fromTeamEntity(taskEntity.getTeamEntity()), fromTaskEntity(taskEntity));
    }

    @Override
    public Task get(UUID entityId) {
        return fromTaskEntity(logic.validateTask(entityId));
    }

    @Override
    public void update(Task task) {
        logic.validateTask(task.getId());
        taskRepository.save(toTaskEntity(task));
    }

    @Override
    public void remove(UUID taskId) {
        TaskEntity taskEntity = logic.validateTask(taskId);
        issueRepository.findByTaskEntity(taskEntity).forEach(x -> issueRepository.save(x.deactivate()));
        taskRepository.save(taskEntity.deactivate());
    }

    @Override
    public List<Task> getByUser(UUID userId) {
        return taskRepository.findByUserEntity(logic.validateUser(userId))
                .stream()
                .map(ModelParser::fromTaskEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getWithIssue() {
        return issueRepository.findAll().stream().filter(AbstractEntity::isActive)
                .map(IssueEntity::getTaskEntity)
                .filter(TaskEntity::isActive)
                .map(ModelParser::fromTaskEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Task> getByTeamAsPage(UUID teamId, PagingInput pagingInput, TaskInput taskInput) {
        return taskRepository.findByTeam(
                logic.validateTeam(teamId),
                true,
                taskInput.getText(),
                taskInput.getStartDate(),
                taskInput.getEndDate(),
                taskInput.getStatus(),
                PageRequest.of(
                        pagingInput.getPage(),
                        pagingInput.getSize()))
                .map(ModelParser::fromTaskEntity);
    }

    @Override
    public void dropTask(UUID userId, UUID taskId) {
        TaskEntity t = logic.validateTask(taskId);
        if (t.getUserEntity() != null && t.getUserEntity().getId().toString().equals(userId.toString())) {
            taskRepository.save(t.dropTask());
        } else {
            throw new WrongInputException("Task does not belong to User");
        }
    }
}