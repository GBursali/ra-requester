package com.gbursali.utils;

import java.nio.file.Path;

/**
 * Exception thrown when an invalid JSON is encountered.
 */
@SuppressWarnings("java:S1144")
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
     * Constructs a new InvalidJSONException with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidJSONException(String message,Path file) {
        super(buildMessage(message,file));
    }


    /**
     * Creates a new message containing additional information
     * about the requested file and its full path.
     *
     * @param message The detail message.
     * @param file The path of the requested file.
     * @return The created InvalidJSONException text.
     */
    private static String buildMessage(String message, Path file) {
        message += "\nRequested file:" + file;
        message += "\nRequested Full path:" + file.toAbsolutePath();
        return message;
    }

}
