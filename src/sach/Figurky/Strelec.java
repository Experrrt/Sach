package sach.Figurky;

import java.awt.Color;
import java.util.ArrayList;
import sach.Figurka;

public class Strelec extends Figurka {
    public Strelec(int x, int y, Color color, int team) {
        super(x, y, color, team);
    }

    @Override
    public boolean skontrolujPohyb(int newX, int newY, ArrayList<Figurka> listFiguriek) {
        if (newX > this.x && newY > this.y
                && newX - this.x == newY - this.y) {
            for (int i = 1; i < newX - this.x; i++) {
                for (int j = 0; j < listFiguriek.size(); j++) {
                    if (listFiguriek.get(j) == this) {
                        continue;
                    } else if (this.x + i == listFiguriek.get(j).x && this.y + i == listFiguriek.get(j).y) {
                        return false;
                    }
                }
            }
            return true;
        } else if (newX < this.x && newY < this.y
                && this.x - newX == this.y - newY) {
            for (int i = 1; i < this.x - newX; i++) {
                for (int j = 0; j < listFiguriek.size(); j++) {
                    if (listFiguriek.get(j) == this) {
                        continue;
                    } else if (this.x - i == listFiguriek.get(j).x && this.y - i == listFiguriek.get(j).y) {
                        return false;
                    }
                }
            }
            return true;
        } else if (newX > this.x && newY < this.y
                && newX - this.x == this.y - newY) {
            for (int i = 1; i < newX - this.x; i++) {
                for (int j = 0; j < listFiguriek.size(); j++) {
                    if (listFiguriek.get(j) == this) {
                        continue;
                    } else if (this.x + i == listFiguriek.get(j).x && this.y - i == listFiguriek.get(j).y) {
                        return false;
                    }
                }
            }
            return true;
        } else if (newX < this.x && newY > this.y
                && this.x - newX == newY - this.y) {
            for (int i = 1; i < this.x - newX; i++) {
                for (int j = 0; j < listFiguriek.size(); j++) {
                    if (listFiguriek.get(j) == this) {
                        continue;
                    } else if (this.x - i == listFiguriek.get(j).x && this.y + i == listFiguriek.get(j).y) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
