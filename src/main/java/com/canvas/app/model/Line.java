package com.canvas.app.model;

public class Line extends Shape {

    public Line(Request request) {
        super(request);
    }

    public void draw(Canvas canvas) {

        String[][] matrix = canvas.getMatrix();

        for (int i = 0; i < canvas.getHeight(); i++) {
            for (int j = 0; j < canvas.getWidth(); j++) {

                if ((j + 1) >= x1 && (j + 1) <= x2 && (i + 1) == y1) {
                    matrix[i][j] = DRAWING_CHAR;
                }

                if ((i + 1) >= y1 && (i + 1) <= y2 && (j + 1) == x1) {
                    matrix[i][j] = DRAWING_CHAR;
                }
            }
        }
    }
}
