package com.canvas.app.model;

import static org.apache.commons.collections4.MapUtils.getInteger;

public abstract class Shape {

    private String input;
    static String DEFAULT_COLOR = "X";

    int x1;
    int y1;
    int x2;
    int y2;

    Request request;

    Shape(Request request) {
        this.request = request;

        this.x1 = getInteger(request.getDimensions(), "x1");
        this.y1 = getInteger(request.getDimensions(), "y1");
        this.x2 = getInteger(request.getDimensions(), "x2");
        this.y2 = getInteger(request.getDimensions(), "y2");
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public abstract void draw(String input, Canvas canvas);
}
