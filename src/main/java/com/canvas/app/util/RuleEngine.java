package com.canvas.app.util;

import com.canvas.app.exceptions.CanvasBoundaryBreachedException;
import com.canvas.app.exceptions.CanvasUndefinedException;
import com.canvas.app.exceptions.InvalidLineCoordinatesException;
import com.canvas.app.exceptions.SemanticsIncorrectException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.size;
import static org.apache.commons.collections4.MapUtils.getInteger;

@Component
public class RuleEngine {

    public Request parseUserInput(String userInput, Canvas canvas) throws Exception {

        Request request = InputParser.parseInput(userInput);

        if (!isSemanticallyCorrect(request))
            throw new SemanticsIncorrectException("Incorrect input semantics. Please correct your input");

        if ((request.getRequestType() == RequestType.LINE
                || request.getRequestType() == RequestType.RECTANGLE
                || request.getRequestType() == RequestType.BUCKET_FILL) && isCanvasUndefined(canvas))
            throw new CanvasUndefinedException("Please create a canvas to start drawing!");

        if (request.getRequestType() != RequestType.QUIT) {
            Optional<String> incorrectValue = request.getDimensions().keySet().stream()
                    .filter(k -> (request.getDimensions().get(k) == 0)).findFirst();
            if (incorrectValue.isPresent())
                throw new CanvasBoundaryBreachedException("Requested coordinates exceed the canvas boundary. Please correct your input");
        }

        if (request.getRequestType() == RequestType.LINE
                && getInteger(request.getDimensions(), "x1") != getInteger(request.getDimensions(), "x2")
                && getInteger(request.getDimensions(), "y1") != getInteger(request.getDimensions(), "y2"))
            throw new InvalidLineCoordinatesException("Geometrically incorrect coordinates. Either x or y coordinates must be equal");

        if (request.getRequestType() != RequestType.CANVAS && request.getRequestType() != RequestType.QUIT
                && ((getInteger(request.getDimensions(), "x1") > canvas.getWidth() ||
                getInteger(request.getDimensions(), "x2") > canvas.getWidth())
                || (getInteger(request.getDimensions(), "y1") > canvas.getHeight() ||
                getInteger(request.getDimensions(), "y2") > canvas.getHeight())))
            throw new CanvasBoundaryBreachedException("Requested coordinates exceed the canvas boundary. Please correct your input");

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
