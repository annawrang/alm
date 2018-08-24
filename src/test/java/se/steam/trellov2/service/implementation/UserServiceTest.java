package se.steam.trellov2.service.implementation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.steam.trellov2.Trellov2ApplicationTests;
import se.steam.trellov2.model.User;
import se.steam.trellov2.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest extends Trellov2ApplicationTests {

    @Autowired
    private UserService userService;

    private List<User> tempUsers;

    @Before
    public void setUp() throws Exception {
        tempUsers = new ArrayList<>();
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

    @After
    public void tearDown() throws Exception {
        tempUsers.forEach(user -> userService.remove(user.getId()));
    }
}