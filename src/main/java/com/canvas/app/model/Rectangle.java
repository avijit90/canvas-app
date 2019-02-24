package com.canvas.app.model;

public class Rectangle extends Shape {


    public Rectangle(String userInput) {
        super(userInput);
    }

    public Rectangle(Request request) {
        super(request);
    }

    @Override
    public void draw(String input, Canvas canvas) {

        String[][] matrix = canvas.getMatrix();

        for (int i = 0; i < canvas.getHeight(); i++) {
            for (int j = 0; j < canvas.getWidth(); j++) {

                if ((j + 1) >= x1 && (j + 1) <= x2 && ((i + 1) == y1 || (i + 1) == y2)) {
                    matrix[i][j] = DEFAULT_COLOR;
                }

                if ((i + 1) >= y1 && (i + 1) <= y2 && ((j + 1) == x1 || (j + 1) == x2)) {
                    matrix[i][j] = DEFAULT_COLOR;
                }
            }
        }

    }
}
