package se.steam.trellov2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import se.steam.trellov2.model.status.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

import static se.steam.trellov2.model.status.TaskStatus.UNSTARTED;

public final class Task extends AbstractModel<Task> {

    private final String text;
    private final TaskStatus status;
    private final LocalDate date;

    @JsonCreator
    public Task(String text, TaskStatus status) {
        super(null);
        this.text = text;
        this.status = status == null ? UNSTARTED : status;
        this.date = LocalDate.now();
    }

    public Task(UUID id, String text, TaskStatus status, LocalDate date) {
        super(id);
        this.text = text;
        this.status = status;
        this.date = date;
    }

    @Override
    public Task assignId() {
        return new Task(UUID.randomUUID(), text, status, date);
    }

    public String getText() {
        return text;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public Task setStatus(TaskStatus status) {
        return new Task(getId(), text, status, LocalDate.now());
    }
}
