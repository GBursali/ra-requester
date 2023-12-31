package space.gbsdev.utils;

import com.google.gson.*;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Utility class for working with JSON data using the Gson library.
 */
public class JSONUtils {

    public static final String ERROR_INVALID_JSON = "Provided content is not a valid JSON";

    // Prevent instantiation of the utility class
    protected JSONUtils() {
    }

    public static void setGsonInstance(Gson customGson) {
        gson = customGson;
    }
    private static Gson gson = new Gson();

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
            throw new InvalidJSONException(ERROR_INVALID_JSON);
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
}
