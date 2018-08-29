package se.steam.trellov2.service.business;

import org.springframework.stereotype.Component;
import se.steam.trellov2.model.User;
import se.steam.trellov2.repository.IssueRepository;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.TeamRepository;
import se.steam.trellov2.repository.UserRepository;
import se.steam.trellov2.repository.model.*;
import se.steam.trellov2.service.exception.*;

import java.util.UUID;

import static se.steam.trellov2.model.status.TaskStatus.DONE;

@Component
public final class Logic {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final IssueRepository issueRepository;

    public Logic(TeamRepository teamRepository, UserRepository userRepository, TaskRepository taskRepository, IssueRepository issueRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.issueRepository = issueRepository;
    }


    public TeamEntity checkTeamMaxCap(TeamEntity team) {
        if (userRepository.findByTeamEntity(team).size() >= 10)
            throw new TeamCapacityReachedException("Max users in team reached!");
        return team;
    }

    public UserEntity checkUserTeamAvailability(UserEntity user) {
        if (user.getTeamEntity() != null)
            throw new UserBelongingToTeamException("Cant add user to team! User already belongs to a team.");
        return user;
    }

    public User validateUsername(User user) {
        if (user.getUsername().length() < 10) {
            throw new WrongInputException("Username must contain at least 10 characters");
        }
        return user;
    }

    public UserEntity checkUserCapacity(UserEntity userEntity) {
        if (taskRepository.findByUserEntity(userEntity).size() < 5) {
            return userEntity;
        }
        throw new WrongInputException("User cannot have more than 5 tasks at once");
    }

    public TaskEntity checkIfTaskIsTaken(TaskEntity taskEntity) {
        if (taskEntity.getUserEntity() == null) {
            return checkIfActive(taskEntity);
        }
        throw new WrongInputException("Task is already taken by " + taskEntity.getUserEntity().getUsername());
    }

    public TaskEntity checkIfSameTeam(UserEntity userEntity, TaskEntity taskEntity) {
        if (userEntity.getTeamEntity() != null &&
                userEntity.getTeamEntity().getId().toString().equals(taskEntity.getTeamEntity().getId().toString())) {
            return taskEntity.setUserEntity(userEntity);
        }
        throw new WrongInputException("Task and User do not belong to the same Team");
    }

    private <T extends AbstractEntity> T checkIfActive(T entity) {
        if (entity.isActive()) {
            return entity;
        }
        throw new InactiveEntityException("Inactive " +
                entity.getClass().getSimpleName().replace("Entity", ""));
    }

    public TaskEntity checkIfDone(TaskEntity taskEntity) {
        if (taskEntity.getStatus() == DONE) {
            return taskEntity;
        }
        throw new WrongInputException("Task is not done");
    }

    public TeamEntity validateTeam(UUID teamId) {
        return teamRepository.findById(teamId)
                .map(this::checkIfActive)
                .orElseThrow(() -> new DataNotFoundException("Team with id [" + teamId + "] not found"));
    }

    public UserEntity validateUser(UUID userId) {
        return userRepository.findById(userId)
                .map(this::checkIfActive)
                .orElseThrow(() -> new DataNotFoundException("User with id [" + userId + "] not found"));
    }

    public TaskEntity validateTask(UUID taskId) {
        return taskRepository.findById(taskId)
                .map(this::checkIfActive)
                .orElseThrow(() -> new DataNotFoundException("Task with id [" + taskId + "] not found"));
    }

    public IssueEntity validateIssue(UUID issueId) {
        return issueRepository.findById(issueId)
                .map(this::checkIfActive)
                .orElseThrow(() -> new DataNotFoundException("Issue with id [" + issueId + "] not found"));
    }
}