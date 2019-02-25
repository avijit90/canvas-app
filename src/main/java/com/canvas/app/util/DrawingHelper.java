package com.canvas.app.util;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Shape;

public class DrawingHelper {

    public static void colorMatrix(int x, int y, String color, String[][] matrix, int width, int height) {

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }

        if (matrix[y][x].equals(Shape.DRAWING_CHAR) || matrix[y][x].equals(color)) {
            return;
        }

        matrix[y][x] = color;

        colorMatrix(x + 1, y, color, matrix, width, height);
        colorMatrix(x, y + 1, color, matrix, width, height);
        colorMatrix(x - 1, y, color, matrix, width, height);
        colorMatrix(x, y - 1, color, matrix, width, height);
    }

    public static String[][] createAndFillFreshMatrix(Canvas canvas) {
        String[][] freshMatrix = new String[canvas.getHeight()][canvas.getWidth()];
        fillMatrixWithBlanks(canvas, freshMatrix);
        return freshMatrix;
    }

    public static boolean isBlankMatrix(String[][] matrix) {
        return matrix == null;
    }

    public static void paintMatrix(Canvas canvas) {
        paintHeader(canvas.getWidth());
        paintLeftAndRightBorders(canvas.getHeight(), canvas.getWidth(), canvas.getMatrix());
        paintFooter(canvas.getWidth());
    }

    private static void paintFooter(int width) {

        for (int j = 0; j < width + 2; j++) {
            System.out.print("-");
        }

        System.out.println("\n");
    }

    private static void paintHeader(int width) {

        for (int j = 0; j < width + 2; j++) {
            System.out.print("-");
        }

        System.out.println();
    }

    private static void paintLeftAndRightBorders(int height, int width, String[][] matrix) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width + 2; j++) {
                if (j == 0 || j == width + 1) {
                    System.out.print("|");
                } else {
                    System.out.print(matrix[i][j - 1]);
                }
            }
            System.out.println();
        }
    }

    private static void fillMatrixWithBlanks(Canvas canvas, String[][] matrix) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = " ";
            }
        }
    }

}
