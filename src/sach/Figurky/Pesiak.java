package sach.Figurky;

import java.awt.Color;
import java.util.ArrayList;

import sach.Figurka;

public class Pesiak extends Figurka {
    public Pesiak(int x, int y, Color color, int team) {
        super(x, y, color, team);
    }

    @Override
    public boolean skontrolujPohyb(int newX, int newY, ArrayList<Figurka> listFiguriek) {
        if ((x == newX && y + 2 - 4 * team == newY && this.y == 1 + team * 5)
                || (x == newX && y + 1 - 2 * team == newY)) {
            for (int i = 0; i < listFiguriek.size(); i++) {
                if (listFiguriek.get(i).x == newX && listFiguriek.get(i).y == newY) {
                    return false;
                }
            }
            return true;
        } else if (x + 1 == newX && y + 1 - 2 * team == newY || x - 1 == newX && y + 1 - 2 * team == newY) {
            for (int i = 0; i < listFiguriek.size(); i++) {
                if (listFiguriek.get(i) == this) {
                    continue;
                } else if (listFiguriek.get(i).x == newX && listFiguriek.get(i).y == newY) {
                    return true;
                }
            }
            return false;
        } else
            return false;
    }
}
