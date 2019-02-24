package com.canvas.app.service;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;

public interface IDrawingService {

    void renderCanvas(Canvas canvas);

    void drawShapes(Canvas canvas);

    void bucketFill(Canvas canvas, Request request);
}
