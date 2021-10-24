package com.asgla.util;

public class Vector2 {

    public double x;
    public double y;

    public Vector2() {
        this.x = 0f;
        this.y = 0f;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }

    public static double distance(double scale, Vector2 a, Vector2 b) {
        double v0 = b.x/scale - a.x/scale;
        double v1 = b.y/scale - a.y/scale;
        return Math.sqrt(v0*v0 + v1*v1);
    }

}
