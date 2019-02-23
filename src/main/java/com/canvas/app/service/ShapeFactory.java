package com.canvas.app.service;

import com.canvas.app.model.Line;
import com.canvas.app.model.Rectangle;
import com.canvas.app.model.Shape;

import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

public class ShapeFactory {


    public Shape createShape(String userInput){

        Shape shape = null;

        if (startsWithIgnoreCase(userInput, "l")) {
            shape = new Line(userInput);
        } else if (startsWithIgnoreCase(userInput, "r")) {
            shape = new Rectangle(userInput);
        }

        return shape;
    }




}
