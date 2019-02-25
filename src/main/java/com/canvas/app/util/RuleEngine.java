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

    public Request parseAndValidateInput(String userInput, Canvas canvas) throws Exception {
        Request request = InputParser.parseInput(userInput);
        runValidations(canvas, request);
        return request;
    }

    private void runValidations(Canvas canvas, Request request) throws SemanticsIncorrectException, CanvasUndefinedException, CanvasBoundaryBreachedException, InvalidLineCoordinatesException {
        if (!isSemanticallyCorrect(request))
            throw new SemanticsIncorrectException("Incorrect input semantics. Please correct your input");

        if (isRequestFiredWithoutCanvasCreation(canvas, request))
            throw new CanvasUndefinedException("Please create a canvas to start drawing!");

        if (isDimensionIncorrect(request))
            throw new CanvasBoundaryBreachedException("Requested coordinates exceed the canvas boundary. Please correct your input");

        if (isGeometricallyValid(request))
            throw new InvalidLineCoordinatesException("Geometrically incorrect coordinates. Either x or y coordinates must be equal");

        if (isCanvasBoundaryBreached(canvas, request))
            throw new CanvasBoundaryBreachedException("Requested coordinates exceed the canvas boundary. Please correct your input");
    }

    private boolean isCanvasBoundaryBreached(Canvas canvas, Request request) {
        return ((request.getRequestType() == RequestType.LINE || request.getRequestType() == RequestType.RECTANGLE)
                && ((getInteger(request.getDimensions(), "x1") > canvas.getWidth() ||
                getInteger(request.getDimensions(), "x2") > canvas.getWidth())
                || (getInteger(request.getDimensions(), "y1") > canvas.getHeight() ||
                getInteger(request.getDimensions(), "y2") > canvas.getHeight())))
                || (request.getRequestType() == RequestType.BUCKET_FILL
                && ((getInteger(request.getDimensions(), "x1") > canvas.getWidth())
                || (getInteger(request.getDimensions(), "y1") > canvas.getHeight())));
    }

    public boolean isGeometricallyValid(Request request) {
        return request.getRequestType() == RequestType.LINE
                && getInteger(request.getDimensions(), "x1") != getInteger(request.getDimensions(), "x2")
                && getInteger(request.getDimensions(), "y1") != getInteger(request.getDimensions(), "y2");
    }

    private boolean isDimensionIncorrect(Request request) {
        if (request.getRequestType() != RequestType.QUIT) {
            Optional<String> incorrectValue = request.getDimensions().keySet().stream()
                    .filter(k -> (request.getDimensions().get(k) == 0)).findFirst();
            return incorrectValue.isPresent();
        } else {
            return false;
        }
    }

    private boolean isRequestFiredWithoutCanvasCreation(Canvas canvas, Request request) {
        return (request.getRequestType() == RequestType.LINE
                || request.getRequestType() == RequestType.RECTANGLE
                || request.getRequestType() == RequestType.BUCKET_FILL) && isCanvasUndefined(canvas);
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
