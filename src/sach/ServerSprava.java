package sach;

import java.io.Serializable;
import java.util.ArrayList;

import sach.Enums.VyhraEnum;
import sach.Figurky.Figurka;

class ServerSprava implements Serializable {
    private Figurka[][] hraciePole;
    private ArrayList<Figurka> vypadnuteFigurky;
    private VyhraEnum vysledokHry;

    public ServerSprava(Figurka[][] hraciePole, ArrayList<Figurka> vypadnuteFigurky, VyhraEnum vysledokHry) {
        this.vypadnuteFigurky = vypadnuteFigurky;
        this.hraciePole = hraciePole;
        this.vysledokHry = vysledokHry;
    }

    public Figurka[][] getSpravaPole() {
        return this.hraciePole;
    }

    public ArrayList<Figurka> getSpravaFigurky() {
        return this.vypadnuteFigurky;
    }

    public VyhraEnum getVysledokHry() {
        return this.vysledokHry;
    }
}
