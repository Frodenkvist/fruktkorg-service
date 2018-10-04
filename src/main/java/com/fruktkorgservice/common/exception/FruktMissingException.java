package com.fruktkorgservice.common.exception;

import lombok.Data;

@Data
public class FruktMissingException extends Exception {
    long id;
    public FruktMissingException(String message, long id) {
        super(message);
        this.id = id;
    }
}
