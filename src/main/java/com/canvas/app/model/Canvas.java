package com.canvas.app.model;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class Canvas {

    private List<Shape> shapes;
    private String[][] matrix;
    private int width;
    private int height;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
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
}
