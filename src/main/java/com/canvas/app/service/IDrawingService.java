package com.canvas.app.service;

import com.canvas.app.model.Canvas;

public interface IDrawingService {

    void renderCanvas(Canvas canvas);

    void drawShapes(Canvas canvas);

    void bucketFill(Canvas canvas, int x, int y, String color);
}
