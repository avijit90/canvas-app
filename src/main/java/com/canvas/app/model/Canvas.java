package com.canvas.app.model;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.List;

public class Canvas {

    private List<Shape> shapes;
    private String[][] matrix;
    private int width;
    private int height;
    private Request request;

    public Canvas(Request request) {
        this.width = MapUtils.getInteger(request.getDimensions(), "x1");
        this.height = MapUtils.getInteger(request.getDimensions(), "y1");
    }

    public void addShape(Shape shape) {
        if (CollectionUtils.isEmpty(shapes))
            shapes = new ArrayList<>();

        shapes.add(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
