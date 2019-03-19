package com.canvas.app.model;

import static org.apache.commons.collections4.MapUtils.getInteger;

public abstract class Shape {

    public static String DRAWING_CHAR = "X";

    int x1;
    int y1;
    int x2;
    int y2;
    private Request request;

    Shape(Request request) {
        this.request = request;

        this.x1 = getInteger(request.getDimensions(), "x1");
        this.y1 = getInteger(request.getDimensions(), "y1");
        this.x2 = getInteger(request.getDimensions(), "x2");
        this.y2 = getInteger(request.getDimensions(), "y2");
    }

    public Request getRequest() {
        return request;
    }

    public abstract void draw(Canvas canvas);
}
