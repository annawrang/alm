package se.steam.trellov2.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity(name = "Teams")
public final class TeamEntity extends AbstractEntity<TeamEntity> {

    @Column(nullable = false)
    private final String name;

    TeamEntity() {
        this.name = null;
    }

    public TeamEntity(UUID id, String name) {
        super(id, true);
        this.name = name;
    }

    private TeamEntity(UUID id, boolean active, String name) {
        super(id, active);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public TeamEntity deactivate() {
        return new TeamEntity(getId(), false, name);
    }
}