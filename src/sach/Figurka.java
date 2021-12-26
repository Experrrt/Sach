package sach;

import java.awt.Color;
import java.util.ArrayList;

public class Figurka {
    public int x, y;
    public Color color;
    public int team;

    public Figurka(int x, int y, Color color, int team) {
        this.y = y;
        this.x = x;
        this.color = color;
        this.team = team;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean skontrolujPohyb(int newX, int newY, ArrayList<Figurka> listFiguriek) {
        return false;
    }
}
