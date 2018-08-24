package se.steam.trellov2.repository.model;

import se.steam.trellov2.model.status.TaskStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

import static se.steam.trellov2.model.status.TaskStatus.STARTED;
import static se.steam.trellov2.model.status.TaskStatus.UNSTARTED;

@Entity(name = "Tasks")
public final class TaskEntity extends AbstractEntity<TaskEntity> {

    @Column(nullable = false)
    private final String text;
    @Enumerated
    @Column(nullable = false)
    private final TaskStatus status;
    @Column(nullable = false)
    private final LocalDate date;
    @ManyToOne
    @JoinColumn(name = "User")
    private final UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "Team", nullable = false, updatable = false)
    private final TeamEntity teamEntity;

    TaskEntity() {
        this.text = null;
        this.status = null;
        this.date = null;
        this.userEntity = null;
        this.teamEntity = null;
    }

    public TaskEntity(UUID id, String text, TaskStatus status, LocalDate date) {
        super(id, true);
        this.text = text;
        this.status = status;
        this.date = date;
        this.userEntity = null;
        this.teamEntity = null;
    }

    private TaskEntity(UUID id, boolean active, String text, TaskStatus status, LocalDate date) {
        super(id, active);
        this.text = text;
        this.date = date;
        this.status = status;
        this.userEntity = null;
        this.teamEntity = null;
    }

    private TaskEntity(UUID id, String text, TaskStatus status, LocalDate date, UserEntity userEntity, TeamEntity teamEntity) {
        super(id, true);
        this.text = text;
        this.status = status;
        this.date = date;
        this.userEntity = userEntity;
        this.teamEntity = teamEntity;
    }

    public String getText() {
        return text;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public LocalDate getDate() {
        return date;
    }

    public TeamEntity getTeamEntity() {
        return teamEntity;
    }

    public TaskEntity setUserEntity(UserEntity userEntity) {
        return new TaskEntity(getId(), text, STARTED, date, userEntity, teamEntity);
    }

    public TaskEntity setTeamEntity(TeamEntity teamEntity) {
        return new TaskEntity(getId(), text, status, date, userEntity, teamEntity);
    }

    public TaskEntity dropTask() {
        return new TaskEntity(getId(), text, UNSTARTED, LocalDate.now(), null, teamEntity);
    }

    @Override
    public TaskEntity deactivate() {
        return new TaskEntity(getId(), false, text, status, date);
    }
}