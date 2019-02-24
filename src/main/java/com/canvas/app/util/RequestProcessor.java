package com.canvas.app.util;

import com.canvas.app.exceptions.InvalidRequestTypeException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import com.canvas.app.model.Shape;
import com.canvas.app.service.DrawingServiceImpl;
import com.canvas.app.service.IDrawingService;
import com.canvas.app.service.ShapeFactory;

public class RequestProcessor {

    ShapeFactory shapeFactory = new ShapeFactory();
    IDrawingService paintService = new DrawingServiceImpl();

    public Canvas processRequest(Request request, Canvas canvas) throws InvalidRequestTypeException {

        if (RequestType.QUIT == request.getRequestType()) {
            System.exit(200);
        } else if (RequestType.CANVAS == request.getRequestType()) {
            canvas = new Canvas(request);
            paintService.renderCanvas(canvas);
        } else if (RequestType.LINE == request.getRequestType()) {
            Shape shape = shapeFactory.createShape(request);
            canvas.addShape(shape);
            paintService.drawShapes(canvas);
            paintService.renderCanvas(canvas);
        } else if (RequestType.RECTANGLE == request.getRequestType()) {
            Shape shape = shapeFactory.createShape(request);
            canvas.addShape(shape);
            paintService.drawShapes(canvas);
            paintService.renderCanvas(canvas);
        } else if (RequestType.BUCKET_FILL == request.getRequestType()) {
            paintService.bucketFill(canvas, request);
            paintService.renderCanvas(canvas);
        } else {
            throw new InvalidRequestTypeException("Invalid Request received. Please check your request ");
        }

        return canvas;
    }
}
