package se.steam.trellov2.service.implementation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.model.User;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.service.TaskService;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.UserService;
import se.steam.trellov2.service.business.Logic;
import se.steam.trellov2.service.exception.WrongInputException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ValidateAddTaskTests{

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private Logic logic;

    private Team team;
    private Task task;
    private User user;
    private Team team2;
    private Task task2;

    @Before
    public void setup(){
        team = teamService.save(new Team("Gangam"));
        user = userService.save(new User("FredrikFredrik","FredrikFredrik","FredrikFredrik"));
        teamService.addUserToTeam(team.getId(),user.getId());
        task = taskService.save(team.getId(),new Task("jobba mera",null)).getSecond();

        team2 = teamService.save(new Team("Bro"));
        task2 = taskService.save(team2.getId(),new Task("jobba hårt",null)).getSecond();
    }

    //TU04 assign valid task to valid user
    /*
        testing function by creating a team, a user and a task. Then i add both the user and task
        to the team, after that i add the task to the user and compare if the user id matches.
    */
    @Test
    public void addTaskToUser() {
        userService.addTaskToUser(user.getId(), task.getId());
        TaskEntity tempTask = logic.validateTask(task.getId());
        assertEquals(tempTask.getUserEntity().getId(), user.getId());
    }

    //TU05 assign valid task to valid user on different team
    /*
        testing the same function, but now i create another team and task, add the task to this team.
        Now i try to add this task to the user on the other team i used before and catch it with our own
        exception, because it will trigger when you try assign a task to a user on different team. If
        the thrown get true in the catch when it didn't work, it will be a successful.
    */
    @Test
    public void addTaskToUserOnDifferentTeam() {
        boolean thrown = false;
        try{
            userService.addTaskToUser(user.getId(), task2.getId());
        }catch (WrongInputException e){
           thrown = true;
        }
        assertTrue(thrown);
    }


    @After
    public void release(){
        taskService.remove(task.getId());
        userService.remove(user.getId());
        teamService.remove(team.getId());
        taskService.remove(task2.getId());
        teamService.remove(team2.getId());
    }
}