package com.canvas.app.service;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
public class DrawingServiceImpl implements IDrawingService {

    @Override
    public void renderCanvas(Canvas canvas) {

        if (isBlankMatrix(canvas.getMatrix())) {
            canvas.setMatrix(createAndFillFreshMatrix(canvas.getHeight(), canvas.getWidth()));
        }

        paintHeader(canvas.getWidth());
        paintLeftAndRightBorders(canvas.getHeight(), canvas.getWidth(), canvas.getMatrix());
        paintFooter(canvas.getWidth());

    }

    @Override
    public void drawShapes(Canvas canvas) {
        if (isNotEmpty(canvas.getShapes()))
            canvas.getShapes().stream().forEach(shape -> shape.draw(canvas));
    }

    @Override
    public void bucketFill(Canvas canvas, Request request) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        String[][] matrix = canvas.getMatrix();
        int x = MapUtils.getInteger(request.getDimensions(), "x1");
        int y = MapUtils.getInteger(request.getDimensions(), "y1");
        String color = request.getColor();

        fillSinglePixelAndExpand(x, y, color, matrix, width, height);
        canvas.setMatrix(matrix);
    }

    private void fillSinglePixelAndExpand(int x, int y, String color, String[][] matrix, int width, int height) {

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }

        if (matrix[y][x].equals("X") || matrix[y][x].equals(color)) {
            return;
        }

        matrix[y][x] = color;

        fillSinglePixelAndExpand(x + 1, y, color, matrix, width, height);
        fillSinglePixelAndExpand(x, y + 1, color, matrix, width, height);
        fillSinglePixelAndExpand(x - 1, y, color, matrix, width, height);
        fillSinglePixelAndExpand(x, y - 1, color, matrix, width, height);
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
