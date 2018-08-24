package se.steam.trellov2.resource.parameter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class PagingInput {

    @QueryParam("page")
    @DefaultValue("0")
    private int page;
    @QueryParam("size")
    @DefaultValue("5")
    private int size;

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
