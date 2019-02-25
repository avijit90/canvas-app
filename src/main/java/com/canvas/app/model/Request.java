package com.canvas.app.model;

import java.util.Map;

import static com.canvas.app.model.RequestType.getEnumFromIdentifier;

public class Request {

    private RequestType requestType;

    private Map<String, Integer> dimensions;

    private String color;

    public Request(RequestBuilder requestBuilder) {
        this.requestType = requestBuilder.requestType;
        this.color = requestBuilder.color;
        this.dimensions = requestBuilder.dimensions;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public Map<String, Integer> getDimensions() {
        return dimensions;
    }

    public String getColor() {
        return color;
    }

    public static class RequestBuilder {

        private RequestType requestType;
        private Map<String, Integer> dimensions;
        private String color;

        public RequestBuilder requestType(String requestType) {
            this.requestType = getEnumFromIdentifier(requestType);
            return this;
        }

        public RequestBuilder color(String color) {
            this.color = color;
            return this;
        }

        public RequestBuilder dimensions(Map<String, Integer> dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }
}
