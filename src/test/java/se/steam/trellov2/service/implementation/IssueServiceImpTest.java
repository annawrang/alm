package se.steam.trellov2.service.implementation;

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

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IssueServiceImpTest {

    @Autowired
    TeamService teamService;
    @Autowired
    TaskService taskService;
    @Autowired
    IssueService issueService;

    private List<Team> teams;
    private List<Task> tasks;

    @Before
    public void setUp() throws Exception {
        teams = new ArrayList<>();
        tasks = new ArrayList<>();
        teams.add(teamService.save(new Team("TempTeam")));
        tasks.add((taskService.save(teams.get(0).getId(), new Task("hello", null))).getSecond());
    }

    @Test(expected = InactiveEntityException.class)
    public void save() {
        //Remove task for failing test
        taskService.remove(tasks.get(0).getId());
        issueService.save(tasks.get(0).getId(), new Issue("Faling issue"));

    }

    @After
    public void tearDown() throws Exception {
        teams.forEach(team -> teamService.remove(team.getId()));
    }
}