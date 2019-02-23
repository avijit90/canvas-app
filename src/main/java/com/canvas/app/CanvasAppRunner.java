package com.canvas.app;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Shape;
import com.canvas.app.service.PaintServiceImpl;
import com.canvas.app.service.IPaintService;
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
        IPaintService paintService = null;
        ShapeFactory shapeFactory;

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
                        paintService = new PaintServiceImpl();
                        canvas = new Canvas(parseInt(split[1]), parseInt(split[2]));
                        paintService.paintCanvas(canvas);
                        continue;
                    } else {
                        System.out.println("Please create a canvas to start drawing! ");
                        continue;
                    }
                }

                shapeFactory = new ShapeFactory();
                Shape shape = shapeFactory.createShape(userInput);
                canvas.addShapeToCanvas(shape);
                paintService.paintCanvas(canvas);

            }
        }
    }

    private boolean isCanvasUndefined(Canvas canvas) {
        return canvas == null;

    }
}
