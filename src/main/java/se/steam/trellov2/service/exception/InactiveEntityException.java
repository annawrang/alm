package se.steam.trellov2.service.exception;

public final class InactiveEntityException extends RuntimeException {

    public InactiveEntityException(String message) {
        super(message);
    }
}