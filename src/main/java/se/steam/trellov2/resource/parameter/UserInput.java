package se.steam.trellov2.resource.parameter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class UserInput {

    @QueryParam("firstName")
    @DefaultValue("")
    private String firstname;

    @QueryParam("lastName")
    @DefaultValue("")
    private String lastname;

    @QueryParam("username")
    @DefaultValue("")
    private String username;

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }
}
