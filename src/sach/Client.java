package sach;

import sach.Enums.VyhraEnum;
import sach.Figurky.*;
import java.util.ArrayList;

public class Client implements Hrac {
    // Vec2 odKlik, klik;

    // public void klikni(int klikX, int klikY) {
    // klik = new Vec2();
    // klik.set(klikX, klikY);
    // }

    // public void odKlikni(int klikX, int klikY) {
    // odKlik = new Vec2();
    // odKlik.set(klikX, klikY);
    // if (klik != null) {
    // synchronized (this) {
    // this.notify();
    // }
    // }
    // }

    @Override
    public Figurka spravTah(Figurka[][] hraciePole, Vec2 newS, ArrayList<Figurka> figurkyNaPoly,
            ArrayList<Figurka> vypadnuteFigurky, VyhraEnum vysledokHry) {
        // try {
        // synchronized (this) {
        // while (odKlik == null || klik == null) {
        // this.wait();
        // }
        // Figurka zobrataFigurka = hraciePole[klik.getX()][klik.getY()];
        // newS.set(odKlik.getX(), odKlik.getY());
        // odKlik = klik = null;
        // return zobrataFigurka;
        // }
        // } catch (InterruptedException e) {
        // }
        return null;
        // while (klik == null || odKlik == null) {
        // System.out.print("");
        // }
        // System.out.println("Halo");
        // Figurka zobrataFigurka = hraciePole[klik.getX()][klik.getY()];
        // newS.set(odKlik.getX(), odKlik.getY());
        // odKlik = klik = null;
        // return zobrataFigurka;
    }
}
