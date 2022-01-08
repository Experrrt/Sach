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
    Vec2 odKlik;
    ArrayList<Hrac> hraci;
    Server server;
    InyClient client;

    public HraciePole(ArrayList<Hrac> hraci) {
        hraciePole = new Figurka[8][8];
        figurkyNaPoly = new ArrayList<Figurka>();
        vypadnuteFigurky = new ArrayList<Figurka>();
        vyhodnocovac = new Vyhodnocovac();
        new HracieMody().nacitajFigurky(hraciePole, figurkyNaPoly);
        this.hraci = hraci;
        // new HracieMody().nacitajFigurky(hraciePole, vypadnuteFigurky);
        if (hraci.get(0) instanceof Server) {
            server = (Server) hraci.get(0);
            try {
                server.startServer(this);
            } catch (Exception e) {

            }
        } else {
            client = (InyClient) hraci.get(0);
            try {
                client.zapniClienta();
            } catch (Exception e) {

            }
        }
        prepocitajDeathZone();
    }

    public Figurka skontrolujKliknutie(int klikX, int klikY) {
        if (hraciePole[klikY][klikX] != null && hraciePole[klikY][klikX].team == teamNaRade) {
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
                // synchronized (this) {
                // this.notify();
                // }
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
        // if (teamNaRade) {
        // try {
        // synchronized (this) {
        // while (odKlik == null && hraci.get((teamNaRade) ? 0 :
        // 1).getClass().isInstance(new Client())) {
        // this.wait();
        // }

        // if (hraci.get(teamNaRade.teamHodnota) instanceof Bot) {
        // zobrataFigurka = hraci.get(teamNaRade.teamHodnota).spravTah(hraciePole, newS,
        // figurkyNaPoly);
        // premiestniFigurku(newS.getY(), newS.getX());
        // } else if (odKlik != null && hraci.get(teamNaRade.teamHodnota) instanceof
        // Client) {
        // premiestniFigurku(odKlik.getY(), odKlik.getX());
        // }

        if (odKlik != null) {
            premiestniFigurku(odKlik.getY(), odKlik.getX());
            hraci.get(0).spravTah(hraciePole, newS, figurkyNaPoly);
        }
        // if (odKlik != null) {
        // premiestniFigurku(odKlik.getY(), odKlik.getX());
        // }
        // }
        // } catch (InterruptedException e) {
        // }
        odKlik = null;
        zobrataFigurka = null;
        // } else if (!teamNaRade) {
        // zobrataFigurka = ciernyHrac.spravTah(hraciePole, newS, figurkyNaPoly);
        // premiestniFigurku(newS.getY(), newS.getX());
        // }
    }

    // && zobrataFigurka.team == ((teamNaRade) ? 1 : 0)
    public void premiestniFigurku(int newX, int newY) {
        if (newX < 8 && newY < 8 && newX >= 0 && newY >= 0
                && zobrataFigurka != null
                && zobrataFigurka.skontrolujPohyb(newX, newY, hraciePole)
                && zobrataFigurka.team == teamNaRade) {//
            if (hraciePole[newY][newX] != null) {
                if (hraciePole[newY][newX].team == zobrataFigurka.team) {
                    zobrataFigurka = null;
                    spravTah();
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
            posliHraciePoleNaSocket();
            vysledokHry = vyhodnocovac.skontrolujKoniecHry(figurkyNaPoly);
            teamNaRade = (teamNaRade == TeamEnum.BIELY) ? TeamEnum.CIERNY : TeamEnum.BIELY;
        }
        zobrataFigurka = null;
        odKlik = null;
        spravTah();
    }

    private void posliHraciePoleNaSocket() {
        if (hraci.size() == 1 && hraci.get(0) instanceof Server) {
            server.serverPosliPole(hraciePole);
            cakajNaUpdate();
        } else {
            client.posliPole(hraciePole);
        }
    }

    private void cakajNaUpdate() {
        try {
            synchronized (hraciePole) {
                hraciePole.wait();
                System.out.println("No co");
            }
        } catch (Exception e) {

        }
    }

    public abstract void spustiStopky();

    public abstract void zastavStopky();

    public abstract void paint(Graphics2D g);
}
