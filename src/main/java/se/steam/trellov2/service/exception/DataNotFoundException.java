package se.steam.trellov2.service.exception;

public final class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }
}