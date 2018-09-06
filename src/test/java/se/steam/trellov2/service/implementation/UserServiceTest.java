package se.steam.trellov2.service.implementation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.steam.trellov2.Trellov2ApplicationTests;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.model.User;
import se.steam.trellov2.repository.TaskRepository;
import se.steam.trellov2.service.TaskService;
import se.steam.trellov2.service.TeamService;
import se.steam.trellov2.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest extends Trellov2ApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TaskRepository taskRepository;

    private List<User> tempUsers;
    private Team postedTeam;
    private Pair<Team, Task> postedPairTeamTask;
    private User postedUser;
    private User postedHelperUser ;

    @Before
    public void setUp() throws Exception {
        tempUsers = new ArrayList<>();
        postedTeam = null;
        postedPairTeamTask = null;
        postedUser = null;
        postedHelperUser = null;

    }

    @Test
    public void getUsers() {
        tempUsers.add(userService.save(new User("JakobJakob", "JakobJakob", "LindahlLindahl")));
        User testUser = userService.get(tempUsers.get(0).getId());

        assertTrue(testUser.getUsername().equals(tempUsers.get(0).getUsername()) &&
                testUser.getFirstName().equals(tempUsers.get(0).getFirstName()) &&
                testUser.getLastName().equals(tempUsers.get(0).getLastName()));
    }

    @Test
    public void postUser() {
        User postedUser = userService.save(new User("AlexAlexAlex", "AlexAlexAlex", "lelelelel"));
        tempUsers.add(postedUser);
        User checkUser = userService.get(postedUser.getId());

        assertTrue(postedUser.getUsername().equals(checkUser.getUsername()) &&
                postedUser.getFirstName().equals(checkUser.getFirstName()) &&
                postedUser.getLastName().equals(checkUser.getLastName()));

    }

    @Test
    public void updateUserName() {
        User postedUser = userService.save(new User("KalleAnka1", "Kalle", "Anka"));
        tempUsers.add(postedUser);
        User updatedUser = new User(postedUser.getId(), "AnkaKalle2", "Kalle", "Anka");
        userService.update(updatedUser);

        assertEquals(postedUser.getId(), updatedUser.getId());
        assertNotEquals(postedUser.getUsername(), updatedUser.getUsername());
        assertEquals(postedUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(postedUser.getLastName(), updatedUser.getLastName());
} 
  
    @Test
    public void addUserAsHelperToTask() {
        postedTeam = teamService.save(new Team("TestTeam"));
        postedPairTeamTask = taskService.save(postedTeam.getId(), new Task("TestTask", null));
        postedUser = userService.save(new User("TestUserusername", "TestUserFirstName", "TestUserLastName"));
        teamService.addUserToTeam(postedTeam.getId(), postedUser.getId());
        userService.addTaskToUser(postedUser.getId(), postedPairTeamTask.getSecond().getId());
        postedHelperUser = userService.save(new User("TestHelperusername", "TestHelperFirstName", "TestHelperLastName"));
        userService.addHelperUserToTask(postedHelperUser.getId(), postedPairTeamTask.getSecond().getId());

        assertTrue(postedHelperUser.getId().equals(taskRepository.getTaskEntitiesById(
                postedPairTeamTask.getSecond().getId()).getTaskHelper().getId()));
    }

    @After
    public void tearDown() throws Exception {
        tempUsers.forEach(user -> userService.remove(user.getId()));


    }
}