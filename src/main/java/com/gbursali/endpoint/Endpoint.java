package com.gbursali.endpoint;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import com.gbursali.utils.InvalidJSONException;
import com.gbursali.utils.JSONUtils;
import com.gbursali.utils.MethodType;
import com.gbursali.utils.json_validator.JSONValidator;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents an API endpoint and provides methods to configure and send HTTP requests.
 */
@SuppressWarnings("java:S1144")
public class Endpoint {

    private MethodType type = MethodType.GET;
    private JSONValidator jsonValidator;
    private final EndpointBase base;
    private String url;

    /**
     * Private constructor to create an Endpoint instance from a given EndpointBase.
     * Use {@link #fromBase(EndpointBase)} to create an instance.
     *
     * @param base The base endpoint configuration.
     */
    protected Endpoint(EndpointBase base) {
        this.base = base;
    }

    /**
     * Creates a new Endpoint instance from a given EndpointBase.
     *
     * @param base The base endpoint configuration.
     * @return The created Endpoint instance.
     */
    public static Endpoint fromBase(EndpointBase base) {
        return new Endpoint(base);
    }

    /**
     * Creates an Endpoint instance from a JSON representation.
     *
     * @param endpointBase The base endpoint configuration.
     * @param object The JSON representation of the endpoint.
     * @return The created Endpoint instance.
     */
    public static Endpoint fromJson(EndpointBase endpointBase, JsonObject object) {
        var instance = new Endpoint(endpointBase);
        JSONUtils.doIfJsonHasObject(object, "settings", instance::pullSettings);
        JSONUtils.doIfJsonHasObject(object, "params", instance::addParam);
        JSONUtils.doIfJsonHasObject(object, "body", body -> instance.setBody(body.toString()));
        return instance;
    }

    /**
     * Sets the JSON schema validator for the response based on the provided schema information.
     * If the schema includes a file reference, it reads the schema from the file; otherwise, it uses the provided JSON object.
     *
     * @param schema The JSON representation of the schema information.
     *               If the schema includes a "file" attribute, it is treated as a file reference; otherwise, it is used directly.
     * @throws InvalidJSONException If the file specified in the schema cannot be read or is invalid.
     */
    private void setSchema(JsonObject schema){
        if(schema.has("file")){
            Path file = Path.of(schema.get("file").getAsString());
            schema = JSONUtils.readJsonFile(file);
        }
        withValidator(JSONUtils.stringify(schema));

    }

    /**
     * Sets the body of the endpoint.
     *
     * @param body The body of the endpoint.
     * @return The current Endpoint instance.
     */
    public Endpoint setBody(String body) {
        this.base.getRawRequest().body(body);
        return this;
    }

    /**
     * Adds parameters to the request.
     *
     * @param params The parameters to add.
     */
    public void addParam(Map<String, String> params) {
        this.base.getRawRequest().params(params);
    }

    /**
     * Adds parameters to the request from a JSON object.
     *
     * @param params The JSON object containing parameters.
     */
    private void addParam(JsonObject params) {
        addParam(JSONUtils.objToStringMap(params));
    }

    /**
     * Pulls settings from a JSON object and applies them to the endpoint.
     *
     * @param settings The JSON object containing settings.
     */
    private void pullSettings(JsonObject settings) {
        JSONUtils.doIfJsonHasString(settings, "path", this::withPath);
        JSONUtils.doIfJsonHasString(settings, "method", this::withType);
    }

    /**
     * Sets the path of the endpoint.
     *
     * @param path The path of the endpoint.
     * @return The current Endpoint instance.
     */
    public Endpoint withPath(String path) {
        this.url = path;
        return this;
    }

    /**
     * Sets the HTTP method type of the request.
     *
     * @param type The HTTP method type.
     * @return The current Endpoint instance.
     */
    public Endpoint withType(MethodType type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the HTTP method type of the request from a string representation.
     *
     * @param type The string representation of the HTTP method type.
     * @return The current Endpoint instance.
     */
    public Endpoint withType(String type) {
        this.type = MethodType.valueOf(type);
        return this;
    }

    /**
     * Sets the JSON schema validator for the response.
     *
     * @param schema The JSON schema as a string.
     * @return The current Endpoint instance.
     */
    public Endpoint withValidator(String schema) {
        this.jsonValidator = JSONValidator.fromString(schema);
        return this;
    }

    /**
     * Sends the configured HTTP request and returns the response.
     *
     * @return The response of the HTTP request.
     */
    public Response send() {
        Response result = base.getRawRequest()
                .request(type.toString(), url)
                .thenReturn();
        if(Objects.nonNull(jsonValidator)){
            jsonValidator.validate(result.asPrettyString());
        }

        return result;
    }
}
