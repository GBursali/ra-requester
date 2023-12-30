package space.gbsdev.endpoint;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;
import space.gbsdev.utils.InvalidJSONException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class EndpointBase {
    private RequestSpecification requestSpecification;
    private Path jsonBasePath;

    protected EndpointBase(String baseURL) {
        this.requestSpecification = given().baseUri(baseURL);
    }

    public static EndpointBase builder(String baseURL) {
        return new EndpointBase(baseURL);
    }

    public Endpoint makeWithPath(String path) {
        return Endpoint.fromBase(this).withPath(path);
    }

    public EndpointBase withCookie(Cookie cookie) {
        this.requestSpecification = requestSpecification.cookie(cookie);
        return this;
    }

    public EndpointBase withJsonBasePath(Path jsonBasePath) {
        this.jsonBasePath = jsonBasePath;
        return this;
    }

    public RequestSpecification getRawRequest() {
        return this.requestSpecification;
    }

    public Endpoint makeWithJson(Path jsonPath) {
        if (jsonBasePath != null)
            jsonPath = jsonBasePath.resolve(jsonPath);
        File jsonFile = jsonPath.toFile();
        if (!jsonFile.exists())
            throw InvalidJSONException.make("JSON file not found", jsonPath);
        try {
            String contents = Files.readString(jsonPath);
            return Endpoint.fromJson(this, contents);
        } catch (IOException e) {
            throw InvalidJSONException.make("Malformed JSON file supplied", jsonPath);
        }
    }

    public Endpoint makeWithJson(String jsonPath) {
        return makeWithJson(Path.of(jsonPath));
    }

    protected JsonElement jsonify(String content) {
        return JsonParser.parseString(content);
    }
}
