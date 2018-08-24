package se.steam.trellov2.service.exception;

public final class TeamCapacityReachedException extends RuntimeException {

    public TeamCapacityReachedException(String s) {
        super(s);
    }
}
