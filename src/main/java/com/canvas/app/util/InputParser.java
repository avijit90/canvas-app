package com.canvas.app.util;

import com.canvas.app.model.Request;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.regex.Pattern.matches;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.CollectionUtils.size;

@Component
public class InputParser {

    public Request parseInput(String userInput) {
        String[] inputTokens = userInput.split(" ");
        List<String> numericTokens = getNumericTokensFromInput(inputTokens);
        List<String> alphabetTokens = getAlphabetTokensFromInput(inputTokens);

        Request.RequestBuilder requestBuilder = new Request.RequestBuilder();
        requestBuilder.requestType(inputTokens[0]);
        requestBuilder.dimensions(getDimensions(numericTokens));
        requestBuilder.color(isNotEmpty(alphabetTokens) ? alphabetTokens.get(0) : null);
        return requestBuilder.build();
    }

    private Map<String, Integer> getDimensions(List<String> numericTokens) {

        Map<String, Integer> dimensions;
        if (size(numericTokens) == 2) {
            dimensions = new HashMap<>(2);
            dimensions.put("x1", Integer.parseInt(numericTokens.get(0)));
            dimensions.put("y1", Integer.parseInt(numericTokens.get(1)));
            return dimensions;
        } else if (size(numericTokens) == 4) {
            dimensions = new HashMap<>(4);
            dimensions.put("x1", Integer.parseInt(numericTokens.get(0)));
            dimensions.put("y1", Integer.parseInt(numericTokens.get(1)));
            dimensions.put("x2", Integer.parseInt(numericTokens.get(2)));
            dimensions.put("y2", Integer.parseInt(numericTokens.get(3)));
            return dimensions;
        }

        return null;
    }

    private List<String> getAlphabetTokensFromInput(String[] inputTokens) {
        if (matches("[a-zA-Z]+", inputTokens[inputTokens.length - 1])) {
            return singletonList(inputTokens[inputTokens.length - 1]);
        }
        return null;
    }

    private List<String> getNumericTokensFromInput(String[] inputTokens) {
        return Arrays.stream(inputTokens)
                .filter(token -> !matches("[a-zA-Z]+", token)).collect(toList());
    }
}
