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
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImpTest {

    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    TeamService teamService;

    User user;
    Task task;
    @Before
    public void setUp() {
        Team team = this.teamService.save(new Team("Team1"));
        user = this.userService.save(new User("UsernameUsername", "firstnameFirstname", "LastnameLastname"));
        teamService.addUserToTeam(team.getId(), user.getId());
        task = taskService.save(team.getId(),new Task("DAFUQ", null)).getSecond();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addTaskToUser() {
        userService.addTaskToUser(user.getId(), task.getId());
    }
}