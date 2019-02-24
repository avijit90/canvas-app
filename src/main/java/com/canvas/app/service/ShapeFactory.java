package com.canvas.app.service;

import com.canvas.app.model.*;

public class ShapeFactory {


    public Shape createShape(Request request) {

        Shape shape = null;

        if (RequestType.LINE == request.getRequestType()) {
            shape = new Line(request);
        } else if (RequestType.RECTANGLE == request.getRequestType()) {
            shape = new Rectangle(request);
        }

        return shape;
    }


}
