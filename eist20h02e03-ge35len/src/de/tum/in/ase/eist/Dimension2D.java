package de.tum.in.ase.eist;

public class Dimension2D {

    private int width;
    private int height;

    public Dimension2D(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Dimension2D(double width, double height) {
        this.width = (int) width;
        this.height = (int) height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
