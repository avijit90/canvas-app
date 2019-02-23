package com.canvas.app.service;

import com.canvas.app.model.Canvas;

public class PaintServiceImpl implements IPaintService {


    @Override
    public void paintCanvas(Canvas canvas) {

        if (isBlankMatrix(canvas.getMatrix())) {
            canvas.setMatrix(createAndFillFreshMatrix(canvas.getHeight(), canvas.getWidth()));
        }

        paintHeader(canvas.getWidth());
        paintLeftAndRightBorders(canvas.getHeight(), canvas.getWidth(), canvas.getMatrix());
        paintFooter(canvas.getWidth());

    }

    private String[][] createAndFillFreshMatrix(int height, int width) {
        String[][] freshMatrix = new String[height][width];
        fillMatrixWithBlanks(height, width, freshMatrix);
        return freshMatrix;
    }

    private boolean isBlankMatrix(String[][] matrix) {
        return matrix == null;
    }

    private void paintFooter(int width) {

        for (int j = 0; j < width + 2; j++) {
            System.out.print("-");
        }

        System.out.println("\n");
    }

    private void paintHeader(int width) {

        for (int j = 0; j < width + 2; j++) {
            System.out.print("-");
        }

        System.out.println();
    }

    private void paintLeftAndRightBorders(int height, int width, String[][] matrix) {
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

    private void fillMatrixWithBlanks(int height, int width, String[][] matrix) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = " ";
            }
        }
    }
}
