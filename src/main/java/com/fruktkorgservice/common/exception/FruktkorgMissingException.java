package com.fruktkorgservice.common.exception;

import lombok.Data;

@Data
public class FruktkorgMissingException extends Exception {
    private long fruktkorgId;

    public FruktkorgMissingException(String message, long fruktkorgId) {
        super(message);
        this.fruktkorgId = fruktkorgId;
    }

}
