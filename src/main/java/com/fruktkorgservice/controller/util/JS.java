package com.fruktkorg.controller.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Simple utility helper for converting stuff to json.
 */
public class JS {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new Jdk8Module());
    }

    private JS() {
        //NO create, only static...
    }

    public static ResponseEntity<JsonNode> message(HttpStatus status, String message, Object... args) {
        return ResponseEntity.status(status).body(JS.message(String.format(message, args)));
    }

    public static ResponseEntity<JsonNode> message(HttpStatus status, Optional<?> message) {
        return message.isPresent() ? ResponseEntity.status(status).body(JS.parse(message.get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(JS.parse("Not Present"));
    }

    public static ResponseEntity<JsonNode> message(HttpStatus status, Object o) {
        if (o instanceof String) {
            o = message((String) o);
        }
        return ResponseEntity.status(status).body(JS.parse(o));
    }

    public static JsonNode message(String message) {
        return parse(new JsonMessage(message));
    }

    public static JsonNode parse(Object o) {
        return mapper.valueToTree(o);
    }

    public static class JsonMessage {
        private String message;

        public JsonMessage() {
        }

        public JsonMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}