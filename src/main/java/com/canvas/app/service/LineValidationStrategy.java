package com.canvas.app.service;

import com.canvas.app.model.Coordinates;
import com.canvas.app.model.Line;

public class LineValidationStrategy implements ValidationStrategy {

    public boolean isValid(Line line) {

        Coordinates startCoordinates = line.getStartCoordinates();
        Coordinates endCoordinates = line.getEndCoordinates();

        if (startCoordinates.getX() != line.getEndCoordinates().getX()
                || startCoordinates.getY() != endCoordinates.getY())
            return true;

        return false;
    }
}
