package sach.Figurky;

import sach.Figurka;
import java.awt.Color;
import java.util.ArrayList;

public class Kon extends Figurka {
    public Kon(int x, int y, Color color, int team) {
        super(x, y, color, team);
    }

    @Override
    public boolean skontrolujPohyb(int newX, int newY, ArrayList<Figurka> listFiguriek) {
        if (this.x + 2 == newX && this.y + 1 == newY
                || this.x + 1 == newX && this.y + 2 == newY
                || this.x - 2 == newX && this.y - 1 == newY
                || this.x - 1 == newX && this.y - 2 == newY
                || this.x - 1 == newX && this.y + 2 == newY
                || this.x - 2 == newX && this.y + 1 == newY
                || this.x + 1 == newX && this.y - 2 == newY
                || this.x + 2 == newX && this.y - 1 == newY) {
            return true;
        }
        return false;
    }
}
