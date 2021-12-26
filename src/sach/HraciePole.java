package sach;

import java.awt.Color;
import java.text.BreakIterator;
import java.util.ArrayList;

import sach.Figurky.*;

public class HraciePole {
    Figurka[][] pole;
    private Figurka zobrataFigurka;
    Figurka pesiak2 = new Pesiak(2, 6, Color.GREEN, 1);
    Figurka pesiak = new Pesiak(5, 3, Color.PINK, 0);
    Figurka strelec = new Strelec(3, 1, Color.red, 0);
    Figurka kon = new Kon(6, 4, Color.yellow, 1);
    Figurka veza = new Veza(0, 0, Color.ORANGE, 0);
    ArrayList<Figurka> listFiguriek = new ArrayList<Figurka>();

    public HraciePole() {
        pole = new Figurka[8][8];
        listFiguriek.add(pesiak2);
        listFiguriek.add(pesiak);
        listFiguriek.add(strelec);
        listFiguriek.add(kon);
        listFiguriek.add(veza);
        updatniPole();
    }

    public void skontrolujKliknutie(java.awt.event.MouseEvent e) {
        for (int i = 0; i < pole.length; i++) {
            for (int j = 0; j < pole.length; j++) {
                if (pole[i][j] != null
                        && pole[i][j].x * 100 < e.getX()
                        && (pole[i][j].x * 100 + 100) > e.getX()
                        && (pole[i][j].y * 100) < e.getY()
                        && (pole[i][j].y * 100 + 100) > e.getY()) {
                    zobrataFigurka = pole[i][j];
                }
            }
        }
    }

    public void updatniPole() {
        pole = new Figurka[8][8];
        for (int i = 0; i < listFiguriek.size(); i++) {
            pole[listFiguriek.get(i).y][listFiguriek.get(i).x] = listFiguriek.get(i);
        }
    }

    public void premiestniFigurku(java.awt.event.MouseEvent e) {
        int newX = e.getX() / 100;
        int newY = e.getY() / 100;

        if (newX < 8 && newY < 8 && newX >= 0 && newY >= 0
                && zobrataFigurka != null
                && zobrataFigurka.skontrolujPohyb(newX, newY, listFiguriek)) {
            if (pole[newY][newX] != null) {
                if (pole[newY][newX].team == zobrataFigurka.team) {
                    return;
                }
            }
            for (int i = 0; i < listFiguriek.size(); i++) {
                if (listFiguriek.get(i).x == newX
                        && listFiguriek.get(i).y == newY
                        && listFiguriek.get(i) != zobrataFigurka) {
                    listFiguriek.remove(i);
                    break;
                }
            }
            zobrataFigurka.setX(newX);
            zobrataFigurka.setY(newY);

            updatniPole();
        }
        zobrataFigurka = null;
    }
}
