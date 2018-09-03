package se.steam.trellov2.service.implementation;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.model.User;
import se.steam.trellov2.model.status.TaskStatus;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.service.TaskService;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class TaskServiceImpTest {

    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    TeamService teamService;
    @Autowired
    TaskRepository taskRepository;

    User user;
    Team team;
    Task task;
    Task newTask;

    @Before
    public void setUp() {
        team = this.teamService.save(new Team("BestTeam"));
        user = this.userService.save(new User("Megaman1989", "David", "Dodda"));
        teamService.addUserToTeam(team.getId(), user.getId());
        task = taskService.save(team.getId(), new Task("Make a JUnit test for tasks", null)).getSecond();

        userService.addTaskToUser(user.getId(), task.getId());

    }

    @Test
    public void update() {
        task = task.setStatus(TaskStatus.PENDING);
            taskService.update(task);
            assertTrue(taskRepository.findById(task.getId()).get().getStatus() == TaskStatus.PENDING);
    }

    @After
    public void tearDown() {
    }
}