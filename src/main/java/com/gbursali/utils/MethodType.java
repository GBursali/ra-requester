package com.gbursali.utils;
/**
 * Enumeration representing common HTTP methods.
 * Each constant corresponds to a specific HTTP method used in RESTful API communication.
 */
public enum MethodType {

    /**
     * HTTP GET method.
     */
    GET,

    /**
     * HTTP POST method.
     */
    POST,

    /**
     * HTTP HEAD method.
     */
    HEAD,

    /**
     * HTTP PUT method.
     */
    PUT,

    /**
     * HTTP DELETE method.
     */
    DELETE,

    /**
     * HTTP CONNECT method.
     */
    CONNECT,

    /**
     * HTTP OPTIONS method.
     */
    OPTIONS,

    /**
     * HTTP TRACE method.
     */
    TRACE,

    /**
     * HTTP PATCH method.
     */
    PATCH;

    /**
     * Returns the MethodType constant that corresponds to the specified string, ignoring case.
     *
     * @param value The string representation of the HTTP method.
     * @return The MethodType constant matching the given string.
     * @throws IllegalArgumentException If no constant with the specified name is found.
     */
    public static MethodType valueOfIgnoreCase(String value) {
        for (MethodType method : values()) {
            if (method.name().equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("No constant with name " + value + " found");
    }
}
