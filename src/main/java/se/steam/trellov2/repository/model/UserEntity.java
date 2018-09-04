package se.steam.trellov2.repository.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Users")
public final class UserEntity extends AbstractEntity<UserEntity> {

    @Column(nullable = false)
    private final String username, firstName, lastName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "Team")
    private final List<TeamEntity> teamEntities;

    UserEntity() {
        this.username = null;
        this.firstName = null;
        this.lastName = null;
        this.teamEntities = null;
    }

    public UserEntity(UUID id, String username, String firstName, String lastName) {
        super(id, true);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamEntities = new ArrayList<>(3);
    }

    private UserEntity(UUID id, boolean active, String username, String firstName, String lastName) {
        super(id, active);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamEntities = null;
    }

    private UserEntity(UUID id, String username, String firstName, String lastName, List<TeamEntity> teamEntities) {
        super(id, true);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamEntities = teamEntities;
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

    public UserEntity setTeamEntities(TeamEntity teamEntity) {
        if(this.teamEntities != null){
            this.teamEntities.add(teamEntity);
        }
        return new UserEntity(getId(), username, firstName,
                lastName, teamEntities);
    }

    public List<TeamEntity> getTeamEntities() {
        return teamEntities;
    }
}