package com.canvas.app.util;

import com.canvas.app.exceptions.CanvasUndefinedException;
import com.canvas.app.exceptions.SemanticsIncorrectException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.collections4.CollectionUtils.size;

@Component
public class RuleEngine {

    @Autowired
    private InputParser inputParser;

    public Request parseUserInput(String userInput, Canvas canvas) throws Exception {

        Request request = inputParser.parseInput(userInput);

        if (!isSemanticallyCorrect(request))
            throw new SemanticsIncorrectException("Incorrect input semantics. Please correct your input");

        if ((request.getRequestType() == RequestType.LINE
                || request.getRequestType() == RequestType.RECTANGLE
                || request.getRequestType() == RequestType.BUCKET_FILL) && isCanvasUndefined(canvas))
            throw new CanvasUndefinedException("Please create a canvas to start drawing!");

        return request;
    }

    private boolean isSemanticallyCorrect(Request request) {

        RequestType requestType = request.getRequestType();

        if (requestType != null)
            if (size(request.getDimensions()) == requestType.getExpectedNumericParams()) {
                int expectedAlphabetParams = requestType.getExpectedAlphabetParams();
                if (expectedAlphabetParams == 0
                        || (expectedAlphabetParams != 0 && request.getColor() != null))
                    return true;
            }

        return false;
    }


    private boolean isCanvasUndefined(Canvas canvas) {
        return canvas == null;
    }

}
