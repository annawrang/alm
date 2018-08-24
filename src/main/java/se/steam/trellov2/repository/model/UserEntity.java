package se.steam.trellov2.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "Users")
public final class UserEntity extends AbstractEntity<UserEntity> {

    @Column(nullable = false)
    private final String username, firstName, lastName;
    @ManyToOne
    @JoinColumn(name = "Team")
    private final TeamEntity teamEntity;

    UserEntity() {
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.teamEntity = null;
    }

    public UserEntity(UUID id, String username, String firstName, String lastName) {
        super(id, true);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamEntity = null;
    }

    private UserEntity(UUID id, boolean active, String username, String firstName, String lastName) {
        super(id, active);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamEntity = null;
    }

    private UserEntity(UUID id, String username, String firstName, String lastName, TeamEntity teamEntity) {
        super(id, true);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamEntity = teamEntity;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public UserEntity deactivate() {
        return new UserEntity(getId(), false, username, firstName, lastName);
    }

    public UserEntity leaveTeam() {
        return new UserEntity(getId(), username, firstName, lastName);
    }

    public UserEntity setTeamEntity(TeamEntity teamEntity) {
        return new UserEntity(getId(), username, firstName,
                lastName, teamEntity);
    }

    public TeamEntity getTeamEntity() {
        return teamEntity;
    }
}