package com.gbursali.utils.json_validator;

import com.google.gson.JsonObject;
import dev.harrel.jsonschema.Dialects;
import dev.harrel.jsonschema.Error;
import dev.harrel.jsonschema.Validator;
import dev.harrel.jsonschema.ValidatorFactory;
import dev.harrel.jsonschema.providers.GsonNode;
import com.gbursali.utils.JSONUtils;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Validates JSON instances against a JSON schema.
 */
@SuppressWarnings("java:S1144")
public class JSONValidator {

    /**
     * ValidatorFactory instance for creating JSON validators.
     */
    private final ValidatorFactory factory = new ValidatorFactory()
            .withJsonNodeFactory(new GsonNode.Factory())
            .withDialect(new Dialects.Draft2020Dialect());

    /**
     * JSON schema to be used for validation.
     */
    private final JsonObject schema;

    /**
     * Constructs a new JSONValidator with the specified JSON schema.
     *
     * @param schema The JSON schema for validation.
     */
    protected JSONValidator(JsonObject schema) {
        this.schema = schema;
    }

    /**
     * Validates a JSON instance against the stored schema.
     *
     * @param instance The JSON instance to validate.
     * @throws JsonValidationException If the validation fails, containing details of the validation errors.
     */
    public void validate(String instance) {
        Validator.Result result = factory.validate(JSONUtils.stringify(schema), instance);
        if (!result.isValid())
            throw new JsonValidationException("Schema validation failed", result.getErrors().toArray(new Error[0]));
        Logger.getAnonymousLogger().fine("Schema validated");
    }

    /**
     * Creates a new JSONValidator instance from a JSON schema file.
     *
     * @param schemaFile The path to the JSON schema file.
     * @return The created JSONValidator instance.
     */
    public static JSONValidator fromFile(Path schemaFile) {
        return fromJson(JSONUtils.readJsonFile(schemaFile, "Can't find schema file"));
    }

    /**
     * Creates a new JSONValidator instance from a JSON schema string.
     *
     * @param schema The JSON schema as a string.
     * @return The created JSONValidator instance.
     */
    public static JSONValidator fromString(String schema) {
        return fromJson(JSONUtils.jsonify(schema).getAsJsonObject());
    }

    /**
     * Creates a new JSONValidator instance from a JsonObject representing the JSON schema.
     *
     * @param schema The JsonObject representing the JSON schema.
     * @return The created JSONValidator instance.
     */
    public static JSONValidator fromJson(JsonObject schema) {
        return new JSONValidator(schema);
    }
}
