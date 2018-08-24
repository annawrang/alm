package se.steam.trellov2.service;

import org.springframework.data.domain.Page;
import se.steam.trellov2.model.User;
import se.steam.trellov2.resource.parameter.PagingInput;
import se.steam.trellov2.resource.parameter.UserInput;

import java.util.List;
import java.util.UUID;

public interface UserService extends Service<User> {

    User save(User user);

    List<User> getByTeam(UUID teamId);

    Page<User> getWithAttributes(UserInput userInput, PagingInput pagingInput);

    void addTaskToUser(UUID userId, UUID taskId);

    void leaveTeam(UUID teamId, UUID userId);
}