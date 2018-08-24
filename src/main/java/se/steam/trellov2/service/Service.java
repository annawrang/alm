package se.steam.trellov2.service;

import se.steam.trellov2.model.AbstractModel;

import java.util.UUID;

public interface Service<T extends AbstractModel<T>> {

    T get(UUID entityId);

    void update(T entity);

    void remove(UUID entityId);
}
