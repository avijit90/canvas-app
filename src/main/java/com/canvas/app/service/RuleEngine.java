package com.canvas.app.service;

import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import com.canvas.app.util.InputParser;

import static org.apache.commons.collections4.CollectionUtils.size;

public class RuleEngine {

    private InputParser inputParser = new InputParser();

    public void processUserInput(String userInput) throws Exception {

        Request request = inputParser.parseInput(userInput);

        if (!isSemanticallyCorrect(request))
            throw new Exception("");

    }

    private boolean isSemanticallyCorrect(Request request) {

        RequestType requestType = request.getRequestType();

        if (requestType != null)
            if (size(request.getDimensions()) == requestType.getNumericParamsCount()) {
                int alphabetParamsCount = requestType.getAlphabetParamsCount();
                if (alphabetParamsCount == 0
                        || (alphabetParamsCount != 0 && request.getColor() != null))
                    return true;
            }

        return false;
    }

    private boolean isWithinCanvas(){



        return false;
    }

}
