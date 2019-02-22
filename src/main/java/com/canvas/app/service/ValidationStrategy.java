package com.canvas.app.service;

import com.canvas.app.model.Line;

public interface ValidationStrategy {

    boolean isValid(Line line);
}
