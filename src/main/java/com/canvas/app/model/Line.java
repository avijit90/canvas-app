package com.canvas.app.model;

public class Line extends Shape {


    public Line(String userInput) {
        super(userInput);
    }

    void draw(String input, Canvas canvas) {

        String[][] matrix = canvas.getMatrix();

        for (int i = 0; i < canvas.getHeight(); i++) {
            for (int j = 0; j < canvas.getWidth(); j++) {

                // horizontal line from X1 to X2 (shift +1 to match user input)
                if ((j + 1) >= x1 && (j + 1) <= x2 && (i + 1) == y1) {
                    matrix[i][j] = DEFAULT_COLOR;
                }

                // vertical line from Y1 to Y2 (shift +1 to match user input)
                if ((i + 1) >= y1 && (i + 1) <= y2 && (j + 1) == x1) {
                    matrix[i][j] = DEFAULT_COLOR;
                }
            }
        }

        canvas.setMatrix(matrix);

    }
}
