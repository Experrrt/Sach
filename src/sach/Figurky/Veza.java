package sach.Figurky;

import java.awt.Color;
import java.util.ArrayList;
import sach.Figurka;

public class Veza extends Figurka {
    public Veza(int x, int y, Color color, int team) {
        super(x, y, color, team);
    }

    @Override
    public boolean skontrolujPohyb(int newX, int newY, ArrayList<Figurka> listFiguriek) {
        if ((this.x > newX || this.x < newX) && newY == this.y) {
            for (int i = 1; i < ((this.x > newX) ? this.x - newX : newX - this.x); i++) {
                for (int j = 0; j < listFiguriek.size(); j++) {
                    if (listFiguriek.get(j) == this) {
                        continue;
                    } else if (((this.x < newX) ? this.x + i : this.x - i) == listFiguriek.get(j).x
                            && this.y == listFiguriek.get(j).y) {
                        return false;
                    }
                }
            }
            return true;
        } else if ((this.y > newY || this.y < newY) && newX == this.x) {
            for (int i = 1; i < ((this.y > newY) ? this.y - newY : newY - this.y); i++) {
                for (int j = 0; j < listFiguriek.size(); j++) {
                    if (listFiguriek.get(j) == this) {
                        continue;
                    } else if (((this.y < newY) ? this.y + i : this.y - i) == listFiguriek.get(j).y
                            && this.x == listFiguriek.get(j).x) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
