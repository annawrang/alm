package se.steam.trellov2.resource.parameter;

import se.steam.trellov2.model.status.TaskStatus;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

public class TaskInput {

    private LocalDate startDate;

    private LocalDate endDate;

    public String getText() {
        return text;
    }

    @QueryParam("text")
    @DefaultValue("")
    private String text;

    @QueryParam("status")
    private TaskStatus status;

    @QueryParam("startDate")
    public void setStartDate(String startDate) {
        this.startDate = setDate(startDate);
    }

    @QueryParam("endDate")
    public void setEndDate(String endDate) {
        this.endDate = setDate(endDate);
    }

    private LocalDate setDate(String date) {
        if (Optional.ofNullable(date).isPresent() &&
                Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$").matcher(date).matches()) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

}