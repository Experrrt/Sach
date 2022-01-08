package sach;

import java.io.Serializable;

public class Vec2 implements Serializable {
    public double[] v;

    public Vec2() {
        this(0, 0);
    }

    public Vec2(double x, double y) {
        v = new double[2];
        set(x, y);
    }

    public Vec2(Vec2 src) {
        this(src.v[0], src.v[1]);
    }

    public Vec2 set(double x, double y) {
        v[0] = x;
        v[1] = y;
        return this;
    }

    public int getX() {
        return (int) v[1];
    }

    public int getY() {
        return (int) v[0];
    }

}