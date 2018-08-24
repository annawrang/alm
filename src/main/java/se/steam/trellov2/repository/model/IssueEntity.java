package se.steam.trellov2.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "Issues")
public final class IssueEntity extends AbstractEntity<IssueEntity> {

    @Column(nullable = false)
    private final String description;
    @ManyToOne
    @JoinColumn(name = "Task", nullable = false)
    private final TaskEntity taskEntity;

    protected IssueEntity() {
        this.taskEntity = null;
        this.description = null;
    }

    public IssueEntity(UUID id, String description) {
        super(id, true);
        this.description = description;
        this.taskEntity = null;
    }

    private IssueEntity(UUID id, boolean active, String description, TaskEntity taskEntity) {
        super(id, active);
        this.description = description;
        this.taskEntity = taskEntity;
    }

    private IssueEntity(UUID id, String description, TaskEntity taskEntity) {
        super(id, true);
        this.description = description;
        this.taskEntity = taskEntity;
    }

    public TaskEntity getTaskEntity() {
        return taskEntity;
    }

    public String getDescription() {
        return description;
    }

    public IssueEntity setTaskEnitity(TaskEntity taskEntity) {
        return new IssueEntity(getId(), getDescription(), taskEntity);
    }

    @Override
    public IssueEntity deactivate() {
        return new IssueEntity(getId(), false, description, taskEntity);
    }
}