package com.canvas.app.model;

public abstract class Shape {

    private String input;
    static String DEFAULT_COLOR = "X";

    int x1;
    int y1;
    int x2;
    int y2;

    Shape(String userInput) {

        String[] split = userInput.trim().split("\\s+");

        this.x1 = Integer.parseInt(split[1]);
        this.y1 = Integer.parseInt(split[2]);
        this.x2 = Integer.parseInt(split[3]);
        this.y2 = Integer.parseInt(split[4]);

    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public abstract void draw(String input, Canvas canvas);
}
