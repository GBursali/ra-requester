package com.gbursali.utils;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Utility class for working with JSON data using the Gson library.
 */
@SuppressWarnings("java:S1144")
public class JSONUtils {

    /**
     * Gson instance used for JSON serialization and deserialization.
     */
    private static Gson gson = new Gson();

    /**
     * Prevent instantiation of the utility class.
     */
    private JSONUtils() {
    }

    /**
     * Sets a custom Gson instance to be used for JSON serialization and deserialization.
     *
     * @param customGson The custom Gson instance.
     */
    public static void setGsonInstance(Gson customGson) {
        gson = customGson;
    }

    /**
     * Performs the specified action if the given JSON object has the specified key.
     *
     * @param object The JSON object.
     * @param key    The key to check in the JSON object.
     * @param action The action to perform on the JSON element associated with the key.
     */
    public static void doIfJsonHas(JsonObject object, String key, Consumer<JsonElement> action) {
        if (!object.has(key))
            return;
        action.accept(object.get(key));
    }

    /**
     * Performs the specified action if the given JSON object has the specified key and the associated value is an object.
     *
     * @param object The JSON object.
     * @param key    The key to check in the JSON object.
     * @param action The action to perform on the JSON object associated with the key.
     */
    public static void doIfJsonHasObject(JsonObject object, String key, Consumer<JsonObject> action) {
        doIfJsonHas(object, key, str -> action.accept(str.getAsJsonObject()));
    }

    /**
     * Performs the specified action if the given JSON object has the specified key and the associated value is a string.
     *
     * @param object The JSON object.
     * @param key    The key to check in the JSON object.
     * @param action The action to perform on the string associated with the key.
     */
    public static void doIfJsonHasString(JsonObject object, String key, Consumer<String> action) {
        doIfJsonHas(object, key, str -> action.accept(str.getAsString()));
    }

    /**
     * Parses the given content and returns a JsonElement.
     *
     * @param content The JSON content to parse.
     * @return The parsed JsonElement.
     */
    public static JsonElement jsonify(String content) {
        try{
            return JsonParser.parseString(content);
        }
        catch (JsonParseException e){
            throw new InvalidJSONException("Provided content is not a valid JSON");
        }
    }

    /**
     * Converts a JsonElement to its string representation.
     *
     * @param content The JsonElement to stringify.
     * @return The string representation of the JsonElement.
     */
    public static String stringify(JsonElement content) {
        return gson.toJson(content);
    }

    /**
     * Converts a JsonObject to a map of strings.
     *
     * @param object The JsonObject to convert.
     * @return The map of strings.
     */
    public static Map<String, String> objToStringMap(JsonObject object) {
        return object.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().isJsonPrimitive())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        x -> x.getValue().getAsString()
                ));
    }

    /**
     * Reads the content of a JSON file located at the specified path and returns it as a JsonObject.
     *
     * @param filePath The path to the JSON file.
     * @return The JsonObject representing the content of the JSON file.
     * @throws InvalidJSONException If the file is not found or contains invalid JSON content.
     */
    public static JsonObject readJsonFile(Path filePath) {
        return readJsonFile(filePath, "File not found or contains invalid JSON content");
    }

    /**
     * Reads the content of a JSON file located at the specified path and returns it as a JsonObject.
     *
     * @param filePath     The path to the JSON file.
     * @param errorMessage The error message to use if the file is not found or contains invalid JSON content.
     * @return The JsonObject representing the content of the JSON file.
     * @throws InvalidJSONException If the file is not found or contains invalid JSON content.
     */
    public static JsonObject readJsonFile(Path filePath, String errorMessage) {
        try {
            return jsonify(Files.readString(filePath)).getAsJsonObject();
        } catch (IOException e) {
            throw new InvalidJSONException(errorMessage, filePath);
        }
    }

}
