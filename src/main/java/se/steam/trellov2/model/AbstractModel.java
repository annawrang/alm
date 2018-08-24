package se.steam.trellov2.model;

import java.util.UUID;

public abstract class AbstractModel<T extends AbstractModel<T>> {

    private final UUID id;

    AbstractModel(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public abstract T assignId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractModel)) return false;

        AbstractModel<?> that = (AbstractModel<?>) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
