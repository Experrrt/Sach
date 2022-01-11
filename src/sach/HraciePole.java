package sach;

import sach.AI.Bot;
import sach.AI.Vyhodnocovac;
import sach.Enums.TeamEnum;
import sach.Enums.VyhraEnum;
import sach.Figurky.*;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class HraciePole {

    Figurka[][] hraciePole;
    ArrayList<Figurka> vypadnuteFigurky;
    VyhraEnum vysledokHry = VyhraEnum.NESKONCILA;
    private ArrayList<Figurka> figurkyNaPoly;
    private Vyhodnocovac vyhodnocovac;
    Figurka zobrataFigurka;
    TeamEnum teamNaRade = TeamEnum.BIELY;
    Boolean hraJeOnline, somNaRadeOnline = true;
    private Vec2 odKlik;
    private ArrayList<Hrac> hraci;

    public HraciePole(ArrayList<Hrac> hraci, Boolean onlineHra) {
        hraciePole = new Figurka[8][8];
        figurkyNaPoly = new ArrayList<Figurka>();
        vypadnuteFigurky = new ArrayList<Figurka>();
        vyhodnocovac = new Vyhodnocovac();
        new HracieMody().nacitajFigurky(hraciePole, figurkyNaPoly);
        this.hraci = hraci;
        this.hraJeOnline = onlineHra;
        prepocitajDeathZone();
    }

    public void skusSpustitOnline() {
        if (!hraJeOnline)
            return;
        try {
            if (hraci.get(0) instanceof Server) {
                ((Server) hraci.get(0)).start();
            } else if (hraci.get(0) instanceof InyClient) {
                ((InyClient) hraci.get(0)).start();
                teamNaRade = TeamEnum.CIERNY;
                somNaRadeOnline = false;
            }
        } catch (Exception e) {
        }
    }

    public Figurka skontrolujKliknutie(int klikX, int klikY) {
        if (hraciePole[klikY][klikX] != null && hraciePole[klikY][klikX].team == teamNaRade && somNaRadeOnline) {
            zobrataFigurka = hraciePole[klikY][klikX];
            return zobrataFigurka;
        }
        return null;
    }

    public void skontrolujOdkliknutie(int klikX, int klikY) {
        if (true) {
            if (zobrataFigurka != null) {
                odKlik = new Vec2();
                odKlik.set(klikX, klikY);
                spravTah();
            }
        }
    }

    public void prepocitajDeathZone() {
        for (int i = 0; i < figurkyNaPoly.size(); i++) {
            figurkyNaPoly.get(i).vypocitajDeathZone(hraciePole);
        }
    }

    public void spravTah() {
        Vec2 newS = new Vec2();

        if (!hraJeOnline && hraci.get(teamNaRade.teamHodnota) instanceof Bot) {
            zobrataFigurka = hraci.get(teamNaRade.teamHodnota).spravTah(hraciePole, newS,
                    figurkyNaPoly, vypadnuteFigurky, vysledokHry);
            skusPremiestnitFigurku(newS.getY(), newS.getX());
        } else if (!hraJeOnline && odKlik != null && hraci.get(teamNaRade.teamHodnota) instanceof Client) {
            skusPremiestnitFigurku(odKlik.getY(), odKlik.getX());
        }

        if (hraJeOnline && odKlik != null && somNaRadeOnline
                && skusPremiestnitFigurku(odKlik.getY(), odKlik.getX())) {
            hraci.get(0).spravTah(hraciePole, newS, figurkyNaPoly, vypadnuteFigurky, vysledokHry);
            somNaRadeOnline = false;
        }
        odKlik = null;
        zobrataFigurka = null;
    }

    private void prepocitajFigurky() {
        figurkyNaPoly.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (hraciePole[i][j] != null) {
                    figurkyNaPoly.add(hraciePole[i][j]);
                }
            }
        }
    }

    public boolean skusPremiestnitFigurku(int newX, int newY) {
        if (newX < 8 && newY < 8 && newX >= 0 && newY >= 0
                && zobrataFigurka != null
                && zobrataFigurka.skontrolujPohyb(newX, newY, hraciePole)
                && zobrataFigurka.team == teamNaRade
                && somNaRadeOnline) {//
            if (hraciePole[newY][newX] != null) {
                if (hraciePole[newY][newX].team == zobrataFigurka.team) {
                    zobrataFigurka = null;
                    return false;
                } else {
                    vypadnuteFigurky.add(hraciePole[newY][newX]);
                    for (int i = 0; i < figurkyNaPoly.size(); i++) {
                        if (newX == figurkyNaPoly.get(i).x && newY == figurkyNaPoly.get(i).y) {
                            figurkyNaPoly.remove(i);
                            break;
                        }
                    }
                }
            }
            hraciePole[newY][newX] = zobrataFigurka;
            hraciePole[zobrataFigurka.y][zobrataFigurka.x] = null;
            zobrataFigurka.setX(newX);
            zobrataFigurka.setY(newY);
            prepocitajDeathZone();
            vysledokHry = vyhodnocovac.skontrolujKoniecHry(figurkyNaPoly);
            if (!hraJeOnline) {
                teamNaRade = (teamNaRade == TeamEnum.BIELY) ? TeamEnum.CIERNY : TeamEnum.BIELY;
            }
            return true;
        }
        zobrataFigurka = null;
        odKlik = null;
        return false;
    }

    public void skusUpdate() {
        if (hraJeOnline) {
            ServerSprava docPole = (hraci.get(0) instanceof Server) ? ((Server) hraci.get(0)).novePole()
                    : ((InyClient) hraci.get(0)).novePole();
            if (docPole != null) {
                hraciePole = docPole.getSpravaPole();
                vypadnuteFigurky = docPole.getSpravaFigurky();
                vysledokHry = docPole.getVysledokHry();
                prepocitajFigurky();
                prepocitajDeathZone();
                somNaRadeOnline = true;
            }
        }
    }

    public abstract void spustiStopky();

    public abstract void zastavStopky();

    public abstract void paint(Graphics2D g);
}
