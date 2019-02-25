package com.canvas.app.model;

import java.util.Optional;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase;

public enum RequestType {

    CANVAS("C", 2, 0),
    LINE("L", 4, 0),
    RECTANGLE("R", 4, 0),
    BUCKET_FILL("B", 2, 1),
    QUIT("Q", 0, 0);

    private final String identifier;
    private final int expectedNumericParams;
    private final int expectedAlphabetParams;

    RequestType(final String identifier, final int expectedNumericParams, final int expectedAlphabetParams) {
        this.identifier = identifier;
        this.expectedNumericParams = expectedNumericParams;
        this.expectedAlphabetParams = expectedAlphabetParams;
    }

    public int getExpectedAlphabetParams() {
        return expectedAlphabetParams;
    }

    public int getExpectedNumericParams() {
        return expectedNumericParams;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static RequestType getEnumFromIdentifier(String input) {
        Optional<RequestType> optionalRequestType = stream(RequestType.values())
                .filter(e -> equalsAnyIgnoreCase(e.getIdentifier(), input)).findFirst();
        return optionalRequestType.isPresent() ? optionalRequestType.get() : null;
    }
}
