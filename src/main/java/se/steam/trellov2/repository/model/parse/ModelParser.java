package se.steam.trellov2.repository.model.parse;

import se.steam.trellov2.model.Issue;
import se.steam.trellov2.model.Task;
import se.steam.trellov2.model.Team;
import se.steam.trellov2.model.User;
import se.steam.trellov2.repository.model.IssueEntity;
import se.steam.trellov2.repository.model.TaskEntity;
import se.steam.trellov2.repository.model.TeamEntity;
import se.steam.trellov2.repository.model.UserEntity;

public final class ModelParser {

    private ModelParser() {
    }

    public static User fromUserEntity(UserEntity u) {
        return new User(u.getId(), u.getUsername(), u.getFirstName(),
                u.getLastName());
    }

    public static UserEntity toUserEntity(User u) {
        return new UserEntity(u.getId(), u.getUsername(),
                u.getFirstName(), u.getLastName());
    }

    public static Task fromTaskEntity(TaskEntity t) {
        return new Task(t.getId(), t.getText(), t.getStatus(), t.getDate());
    }

    public static TaskEntity toTaskEntity(Task t) {
        return new TaskEntity(t.getId(), t.getText(), t.getStatus(), t.getDate());
    }

    public static Team fromTeamEntity(TeamEntity t) {
        return new Team(t.getId(), t.getName());
    }

    public static TeamEntity toTeamEntity(Team t) {
        return new TeamEntity(t.getId(), t.getName());
    }

    public static Issue fromIssueEntity(IssueEntity i) {
        return new Issue(i.getId(), i.getDescription());
    }

    public static IssueEntity toIssueEntity(Issue i) {
        return new IssueEntity(i.getId(), i.getDescription());
    }
}