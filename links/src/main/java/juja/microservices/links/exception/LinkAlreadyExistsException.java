package juja.microservices.links.exception;

public class LinkAlreadyExistsException extends RuntimeException {

    public LinkAlreadyExistsException(String message) {
        super(message);
    }
}
