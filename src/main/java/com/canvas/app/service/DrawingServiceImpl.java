package com.canvas.app.service;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import static com.canvas.app.util.DrawingHelper.*;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
public class DrawingServiceImpl implements IDrawingService {

    @Override
    public void renderCanvas(Canvas canvas) {
        if (isBlankMatrix(canvas.getMatrix())) {
            canvas.setMatrix(createAndFillFreshMatrix(canvas));
        }

        paintMatrix(canvas);
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

        colorMatrix(x, y, color, matrix, width, height);
        canvas.setMatrix(matrix);
    }

}
