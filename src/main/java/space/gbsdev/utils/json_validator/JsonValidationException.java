package space.gbsdev.utils.json_validator;

import dev.harrel.jsonschema.Error;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Exception thrown when JSON validation fails.
 */
public class JsonValidationException extends RuntimeException {
    /**
     * Constructs a new JSON validation exception with the specified detail message and validation errors.
     *
     * @param message The detail message.
     * @param errors  The validation errors.
     */
    public JsonValidationException(String message, Error... errors) {
        super(buildMessage(message, errors));
    }

    /**
     * Builds a detailed error message by combining the provided message and validation errors.
     *
     * @param message The detail message.
     * @param errors  The validation errors.
     * @return The detailed error message.
     */
    private static String buildMessage(String message, Error... errors) {
        String errorMessage = Arrays.stream(errors)
                .map(JsonValidationException::extractMessage)
                .collect(Collectors.joining("\n\n"));
        return message + "\n" + errorMessage;
    }

    /**
     * Extracts a formatted message from a validation error.
     *
     * @param error The validation error.
     * @return The formatted error message.
     */
    private static String extractMessage(Error error) {
        return String.format(
                "Path: %s%nVariable: %s%nError: %s",
                error.getInstanceLocation(), error.getKeyword(), error.getError());
    }
}
