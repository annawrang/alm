package se.steam.trellov2.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import se.steam.trellov2.model.User;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.UserRepository;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.repository.model.UserEntity;
import se.steam.trellov2.repository.model.parse.ModelParser;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.resource.parameter.UserInput;
import se.steam.trellov2.service.UserService;
import se.steam.trellov2.service.business.Logic;
import se.steam.trellov2.service.exception.WrongInputException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static se.steam.trellov2.repository.model.parse.ModelParser.fromUserEntity;
import static se.steam.trellov2.repository.model.parse.ModelParser.toUserEntity;

@Service
final class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final Logic logic;

    private UserServiceImp(UserRepository userRepository, TaskRepository taskRepository, Logic logic) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.logic = logic;
    }

    @Override
    public User save(User user) {
        return fromUserEntity(userRepository.save(toUserEntity(logic.validateUsername(user).assignId())));
    }

    @Override
    public User get(UUID userId) {
        return fromUserEntity(logic.validateUser(userId));
    }

    @Override
    public void update(User user) {
        logic.validateUser(user.getId());
        userRepository.save(toUserEntity(user));
    }

    @Override
    public void remove(UUID userId) {
        taskRepository.findByUserEntity(
                userRepository.save(logic.validateUser(userId).deactivate())).stream()
                .map(TaskEntity::dropTask)
                .forEach(taskRepository::save);
    }

    @Override
    public List<User> getByTeam(UUID teamId) {
        return userRepository.findByTeamEntity(logic.validateTeam(teamId))
                .stream()
                .map(ModelParser::fromUserEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> getWithAttributes(UserInput userInput, PagingInput pagingInput) {
        return userRepository.findByFirstNameContainingAndLastNameContainingAndUsernameContainingAndActive(
                userInput.getFirstName(),
                userInput.getLastName(),
                userInput.getUsername(),
                true,
                PageRequest.of(pagingInput.getPage(), pagingInput.getSize()))
                .map(ModelParser::fromUserEntity);
    }

    @Override
    public void addTaskToUser(UUID userId, UUID taskId) {
        taskRepository.save(
                logic.checkIfSameTeam(
                        logic.checkUserCapacity(logic.validateUser(userId)),
                        logic.checkIfTaskIsTaken(logic.validateTask(taskId))
                )
        );
    }

    @Override
    public void leaveTeam(UUID teamId, UUID userId) {
        UserEntity u = logic.validateUser(userId);
        if (u.getTeamEntity() != null &&
                u.getTeamEntity().getId().toString().equals(teamId.toString())) {
            userRepository.save(u.leaveTeam());
        } else {
            throw new WrongInputException("User does not belong to requested Team");
        }
    }
}