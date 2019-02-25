package com.canvas.app.util;

import com.canvas.app.exceptions.InvalidRequestTypeException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.model.Shape;
import com.canvas.app.service.IDrawingService;
import com.canvas.app.service.ShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.canvas.app.model.RequestType.*;

@Component
public class RequestProcessor {

    @Autowired
    private ShapeFactory shapeFactory;

    @Autowired
    private IDrawingService paintService;

    public Canvas processRequest(Request request, Canvas canvas) throws InvalidRequestTypeException {

        if (CANVAS == request.getRequestType()) {
            canvas = new Canvas(request);
            paintService.renderCanvas(canvas);
        } else if (LINE == request.getRequestType()
                || RECTANGLE == request.getRequestType()) {
            Shape shape = shapeFactory.createShape(request);
            canvas.addShape(shape);
            paintService.drawShapes(canvas);
            paintService.renderCanvas(canvas);
        } else if (BUCKET_FILL == request.getRequestType()) {
            paintService.bucketFill(canvas, request);
            paintService.renderCanvas(canvas);
        } else {
            throw new InvalidRequestTypeException("Invalid Request received. Please check your request ");
        }

        return canvas;
    }

    public boolean quitProgram(Request request) {
        return QUIT == request.getRequestType();
    }
}
