package se.steam.trellov2.service.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.model.User;
import se.steam.trellov2.service.exception.InactiveEntityException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamServiceImp teamService;
    @Autowired
    private UserServiceImp userService;

    @Test
    public void remove() {
        //TU03 inactivation of valid team
        /*
            testing function by creating a new team and inactivating it and then
            catching an exception thats thrown if object is set as inactive.
         */
        boolean thrown = false;
        Team t = teamService.save(new Team("testing"));
        teamService.remove(t.getId());
        try {
            teamService.get(t.getId());
        } catch (InactiveEntityException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void addUserToTeam() {
        //TU07 add valid user to valid team
        /*
            testing function by creating a new team and a new user, adding the user to a team and then
            fetching the teams users to compare it with the newly added member.
         */
        Team t = teamService.save(new Team("addUserTest"));
        User u = userService.save(new User("12345testing", "12345testing", "12345testing"));

        teamService.addUserToTeam(t.getId(), u.getId());
        User uTesting = userService.getByTeam(t.getId()).stream().filter(user -> user.equals(u)).findAny().orElse(null);
        assertNotNull(uTesting);

        userService.remove(u.getId());
        teamService.remove(t.getId());
    }
}