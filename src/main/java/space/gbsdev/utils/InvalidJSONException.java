package space.gbsdev.utils;

import java.nio.file.Path;

public class InvalidJSONException extends RuntimeException {

    public InvalidJSONException() {
        super();
    }

    public InvalidJSONException(String message) {
        super(message);
    }

    public static InvalidJSONException make(String message,Path file) {
        message += "\nRequested file:"+file;
        message += "\nRequested Full path:"+file.toAbsolutePath();
        return new InvalidJSONException(message);
    }
}
