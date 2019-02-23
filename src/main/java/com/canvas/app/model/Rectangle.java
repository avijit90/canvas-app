package com.canvas.app.model;

public class Rectangle extends Shape {


    public Rectangle(String userInput) {
        super(userInput);
    }

    @Override
    void draw(String input, Canvas canvas) {

        String[][] matrix = canvas.getMatrix();

        for (int i = 0; i < canvas.getHeight(); i++) {
            for (int j = 0; j < canvas.getWidth(); j++) {
                // horizontal line from X1 to X2 (shift +1 to match user input)
                // in case we are in the first row(Y1) or in the second row(Y2)
                if ((j + 1) >= x1 && (j + 1) <= x2 && ((i + 1) == y1 || (i + 1) == y2)) {
                    matrix[i][j] = DEFAULT_COLOR;
                }

                // vertical line from Y1 to Y2 (shift +1 to match user input)
                // where we are in the first column(X1) or second column(X2)
                if ((i + 1) >= y1 && (i + 1) <= y2 && ((j + 1) == x1 || (j + 1) == x2)) {
                    matrix[i][j] = DEFAULT_COLOR;
                }
            }
        }

    }
}
