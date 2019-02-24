package com.canvas.app;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Shape;
import com.canvas.app.service.DrawingServiceImpl;
import com.canvas.app.service.IDrawingService;
import com.canvas.app.service.ShapeFactory;

import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.*;

public class CanvasAppRunner {

    public static void main(String[] args) {
        CanvasAppRunner runner = new CanvasAppRunner();
        runner.run();
    }

    private void run() {

        System.out.println("---Application started successfully !----");
        Canvas canvas = null;
        IDrawingService paintService = null;
        ShapeFactory shapeFactory = null;

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("-->> Command <<--");
                String userInput = scanner.nextLine();

                if (isEmpty(userInput)) {
                    System.out.println("Invalid Input, Please try again.");
                    continue;
                }

                if (equalsAnyIgnoreCase("q", userInput)) {
                    System.out.println("-----Shutting Down, Bye !-------");
                    break;
                }

                String[] split = userInput.trim().split("\\s+");

                if (isCanvasUndefined(canvas)) {
                    if (startsWithIgnoreCase(userInput, "c")) {
                        paintService = new DrawingServiceImpl();
                        canvas = new Canvas(parseInt(split[1]), parseInt(split[2]));
                        paintService.renderCanvas(canvas);
                        continue;
                    } else {
                        System.out.println("Please create a canvas to start drawing! ");
                        continue;
                    }
                }

                if (shapeFactory == null) {
                    shapeFactory = new ShapeFactory();
                }

                if (!startsWithIgnoreCase(userInput, "b")) {
                    Shape shape = shapeFactory.createShape(userInput);
                    canvas.addShape(shape);
                    paintService.drawShapes(canvas);
                    paintService.renderCanvas(canvas);
                } else {
                    paintService.bucketFill(canvas, parseInt(split[1]), parseInt(split[2]), split[3]);
                    paintService.renderCanvas(canvas);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isCanvasUndefined(Canvas canvas) {
        return canvas == null;

    }
}
