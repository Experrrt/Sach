package sach;

import sach.Figurky.*;

import java.util.ArrayList;

public class HraciePole {

    Figurka[][] hraciePole;
    ArrayList<Figurka> vypadnuteFigurky;
    boolean koniecHry, vyhernyTeam = false;
    private ArrayList<Figurka> figurkyNaPoly;
    Figurka zobrataFigurka;
    boolean teamNaRade = true;
    ArrayList<Vec2> deathZoneTeamC = new ArrayList<Vec2>();;
    ArrayList<Vec2> deathZoneTeamB = new ArrayList<Vec2>();;
    Client client = new Client();
    AI ai = new AI();

    public HraciePole() {
        hraciePole = new Figurka[8][8];
        figurkyNaPoly = new ArrayList<Figurka>();
        vypadnuteFigurky = new ArrayList<Figurka>();
        new HracieMody().nacitajFigurky(hraciePole, figurkyNaPoly);
        // new HracieMody().nacitajFigurky(hraciePole, vypadnuteFigurky);
        prepocitajDeathZone();
    }

    public Figurka skontrolujKliknutie(int klikX, int klikY) {
        if (hraciePole[klikY][klikX] != null && hraciePole[klikY][klikX].team == ((teamNaRade) ? 1 : 0)) {
            zobrataFigurka = hraciePole[klikY][klikX];
            return zobrataFigurka;
        }
        return null;
    }

    public void prepocitajDeathZone() {
        for (int i = 0; i < figurkyNaPoly.size(); i++) {
            figurkyNaPoly.get(i).vypocitajDeathZone(hraciePole);
        }
    }

    public void aiTah() {
        Vec2 newS = new Vec2();
        if (teamNaRade) {
            zobrataFigurka = client.spravTah(hraciePole, newS);
            premiestniFigurku(newS.getX(), newS.getY());
        } else if (!teamNaRade) {
            zobrataFigurka = ai.spravTah(hraciePole, newS, figurkyNaPoly);
            premiestniFigurku(newS.getY(), newS.getX());
        }
    }

    public void skontrolujOdkliknutie(int newX, int newY) {
        if (teamNaRade) {
            premiestniFigurku(newX, newY);
        }
    }

    // && zobrataFigurka.team == ((teamNaRade) ? 1 : 0)
    public void premiestniFigurku(int newX, int newY) {
        if (newX < 8 && newY < 8 && newX >= 0 && newY >= 0
                && zobrataFigurka != null
                && zobrataFigurka.skontrolujPohyb(newX, newY, hraciePole)
                && zobrataFigurka.team == ((teamNaRade) ? 1 : 0)) {//
            if (hraciePole[newY][newX] != null) {
                if (hraciePole[newY][newX].team == zobrataFigurka.team) {
                    zobrataFigurka = null;
                    aiTah();
                    return;
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
            koniecHry = skontrolujKoniecHry();
            teamNaRade = !teamNaRade;
        }
        zobrataFigurka = null;
        aiTah();
    }

    public boolean skontrolujKoniecHry() {
        ArrayList<Figurka> pKon, pKral, pPesiak, pStrelec;
        pKon = new ArrayList<>();
        pKral = new ArrayList<>();
        pPesiak = new ArrayList<>();
        pStrelec = new ArrayList<>();
        deathZoneTeamC.clear();
        deathZoneTeamB.clear();

        for (int i = 0; i < figurkyNaPoly.size(); i++) {
            switch (figurkyNaPoly.get(i).typ) {
                case "Kon":
                    pKon.add(figurkyNaPoly.get(i));
                    break;
                case "Kral":
                    pKral.add(figurkyNaPoly.get(i));
                    break;
                case "Pesiak":
                    pPesiak.add(figurkyNaPoly.get(i));
                    break;
                case "Strelec":
                    pStrelec.add(figurkyNaPoly.get(i));
                    break;
            }
            if (figurkyNaPoly.get(i).team == 0) {
                deathZoneTeamC.addAll(figurkyNaPoly.get(i).deathZone);
            } else {
                deathZoneTeamB.addAll(figurkyNaPoly.get(i).deathZone);
            }
        }

        // remiza
        if (figurkyNaPoly.size() == 2 && pKral.size() == 2) {
            return true;
        } else if (figurkyNaPoly.size() == 3 && (pKon.size() == 1 || pStrelec.size() == 1) && pKral.size() == 2) {
            return true;
        } else if ((figurkyNaPoly.size() == 4 && (pKon.size() <= 2 || pStrelec.size() <= 2) && pKral.size() == 2)
                && (pKon.size() == 2 && pKon.get(0).team != pKon.get(1).team
                        || pKon.size() == 1 && pStrelec.size() == 1 && pKon.get(0).team != pStrelec.get(0).team
                        || pStrelec.size() == 2 && pStrelec.get(0).team != pStrelec.get(1).team)) {
            return true;
        } else if (figurkyNaPoly.size() == 4 && pKon.size() == 2 && pKon.get(0).team == pKon.get(1).team
                && pKral.size() == 2) {
            return true;
        }
        // prehra
        for (int y = 0; y < pKral.size(); y++) {
            int zabratePolicka = 0;
            Figurka kralNaMat = pKral.get(y);
            Boolean mat = false;
            ArrayList<Vec2> deathZoneTeamu = (kralNaMat.team == 1) ? deathZoneTeamC : deathZoneTeamB;
            for (int i = 0; i < kralNaMat.deathZone.size(); i++) {
                for (int j = 0; j < deathZoneTeamu.size(); j++) {
                    if (kralNaMat.deathZone.get(i).getX() == deathZoneTeamu.get(j).getX()
                            && kralNaMat.deathZone.get(i).getY() == deathZoneTeamu.get(j).getY()) {
                        zabratePolicka++;
                        break;
                    }
                    if (kralNaMat.x == deathZoneTeamu.get(j).getX() && kralNaMat.y == deathZoneTeamu.get(j).getY()) {
                        if (kralNaMat.maMat) {
                            return true;
                        } else {
                            mat = true;
                        }
                    }
                }
            }
            kralNaMat.maMat = mat;
            if (zabratePolicka == kralNaMat.deathZone.size() && kralNaMat.deathZone.size() != 0) {
                return true;
            }
        }
        return false;
    }
}
