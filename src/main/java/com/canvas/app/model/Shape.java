package com.canvas.app.model;

import java.util.List;

public class Shape {

    List<HorizontalLine> horizontalLines;
    List<VerticalLine> verticalLines;

    public List<HorizontalLine> getHorizontalLines() {
        return horizontalLines;
    }

    public void setHorizontalLines(List<HorizontalLine> horizontalLines) {
        this.horizontalLines = horizontalLines;
    }

    public List<VerticalLine> getVerticalLines() {
        return verticalLines;
    }

    public void setVerticalLines(List<VerticalLine> verticalLines) {
        this.verticalLines = verticalLines;
    }
}
