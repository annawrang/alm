package se.steam.trellov2.service.exception;

public final class WrongInputException extends RuntimeException {

    public WrongInputException(String message) {
        super(message);
    }
}
