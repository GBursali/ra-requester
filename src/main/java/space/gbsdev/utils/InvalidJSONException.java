package space.gbsdev.utils;

import java.nio.file.Path;

/**
 * Exception thrown when an invalid JSON is encountered.
 */
public class InvalidJSONException extends RuntimeException {

    /**
     * Constructs a new InvalidJSONException with no detail message.
     */
    public InvalidJSONException() {
        super();
    }

    /**
     * Constructs a new InvalidJSONException with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidJSONException(String message) {
        super(message);
    }


    /**
     * Creates a new InvalidJSONException with a formatted message containing additional information
     * about the requested file and its full path.
     *
     * @param message The detail message.
     * @param file The path of the requested file.
     * @return The created InvalidJSONException instance.
     */
    public static InvalidJSONException make(String message, Path file) {
        message += "\nRequested file:" + file;
        message += "\nRequested Full path:" + file.toAbsolutePath();
        return new InvalidJSONException(message);
    }
}
