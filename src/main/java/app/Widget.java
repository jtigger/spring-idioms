package app;

public class Widget {

    public String getColor() {
        return color;
    }

    private String color;
    private int x;
    private int y;

    public Widget(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
}
