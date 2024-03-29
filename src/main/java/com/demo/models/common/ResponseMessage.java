package com.demo.models.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 165139
 */
@Getter
@Setter
public class ResponseMessage<T> {
    @JsonProperty("isError")
    private boolean isError;
    private String message;
    private T data;

    public ResponseMessage<T> ok(T data) {
        this.data = data;
        return this;
    }

    public ResponseMessage<T> error(String message) {
        this.message = message;
        this.isError = true;
        return this;
    }
}
