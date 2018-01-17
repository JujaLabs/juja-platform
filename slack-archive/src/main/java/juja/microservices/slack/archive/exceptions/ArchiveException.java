package juja.microservices.slack.archive.exceptions;

/**
 * @author Vadim Dyachenko
 */

public class ArchiveException extends RuntimeException {

    public ArchiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArchiveException(String message) {
        super(message);
    }
}
