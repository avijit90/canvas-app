package com.canvas.app.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
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
    private final int numericParamsCount;
    private final int alphabetParamsCount;

    RequestType(final String identifier, final int numericParamsCount, final int alphabetParamsCount) {
        this.identifier = identifier;
        this.numericParamsCount = numericParamsCount;
        this.alphabetParamsCount = alphabetParamsCount;
    }

    public int getAlphabetParamsCount() {
        return alphabetParamsCount;
    }

    public int getNumericParamsCount() {
        return numericParamsCount;
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
