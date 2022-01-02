package sach;

import sach.Figurky.*;

public class Client {
    Vec2 odKlik, klik;

    public void klikni(int klikX, int klikY) {
        klik = new Vec2();
        klik.set(klikX, klikY);
    }

    public void odKlikni(int klikX, int klikY) {
        odKlik = new Vec2();
        odKlik.set(klikX, klikY);
        if (klik != null) {
            synchronized (this) {
                this.notify();
            }
        }
    }

    public Figurka spravTah(Figurka[][] hraciePole, Vec2 newS) {
        try {
            synchronized (this) {
                while (odKlik == null || klik == null) {
                    this.wait();
                }
                Figurka zobrataFigurka = hraciePole[klik.getX()][klik.getY()];
                newS.set(odKlik.getX(), odKlik.getY());
                odKlik = klik = null;
                return zobrataFigurka;
            }
        } catch (InterruptedException e) {
        }
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
