package se.steam.trellov2.service.implementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.model.User;
import se.steam.trellov2.model.status.TaskStatus;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.service.TaskService;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.steam.trellov2.model.Issue;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.service.IssueService;
import se.steam.trellov2.service.TaskService;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.exception.InactiveEntityException;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaskServiceImpTest {

    @Autowired
    UserServiceImp userService;
    @Autowired
    TaskServiceImp taskService;
    @Autowired
    TeamServiceImp teamService;
    @Autowired
    TaskRepository taskRepository;

    User user;
    Team team;
    Task task;

    @Before
    public void setUp() {
        team = teamService.save(new Team("WorldLeadingTeam"));
        user = userService.save(new User("Megaman1989", "David", "Dodda"));
        teamService.addUserToTeam(team.getId(), user.getId());
        task = taskService.save(team.getId(), new Task("Make a JUnit test for tasks", null)).getSecond();
        userService.addTaskToUser(user.getId(), task.getId());
    }

    @Test
    public void update() {
        //Sets task status as pending.
        task = task.setStatus(TaskStatus.PENDING);
        taskService.update(task);
        assertTrue(taskRepository.findById(task.getId()).get().getStatus() == TaskStatus.PENDING);
        //Tries to set status to DONE from PENDING. Will result in status staying the same
        task = task.setStatus(TaskStatus.DONE);
        assertTrue(taskRepository.findById(task.getId()).get().getStatus() == TaskStatus.PENDING);
    }

    @After
    public void tearDown() {
    }
}