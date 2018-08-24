package se.steam.trellov2.repository.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity<T extends AbstractEntity<T>> {

    @Id
    @Column(columnDefinition = "binary(16)")
    private final UUID id;
    private final boolean active;

    AbstractEntity() {
        this.id = null;
        this.active = true;
    }

    AbstractEntity(UUID id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public abstract T deactivate();

}