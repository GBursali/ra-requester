package space.gbsdev.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class JSONUtils {
    protected JSONUtils(){}
    private static Gson gson = new Gson();

    public static void doIfJsonHas(JsonObject object, String key, Consumer<JsonElement> action){
        if(!object.has(key))
            return;
        action.accept(object.get(key));
    }
    public static void doIfJsonHasObject(JsonObject object, String key, Consumer<JsonObject> action){
        doIfJsonHas(object,key,str->action.accept(str.getAsJsonObject()));

    }
    public static void doIfJsonHasString(JsonObject object, String key, Consumer<String> action){
        doIfJsonHas(object,key,str->action.accept(str.getAsString()));
    }

    public static JsonElement jsonify(String content){
        return JsonParser.parseString(content);
    }

    public static String stringify(JsonElement content){
        return gson.toJson(content);
    }
    public static Map<String,String> objToStringMap(JsonObject object){
        return object
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        x->x.getValue().getAsString()
                ));
    }
}
