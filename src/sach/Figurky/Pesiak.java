package sach.Figurky;

import java.util.ArrayList;

import sach.Vec2;

public class Pesiak extends Figurka {
    public Pesiak(int x, int y, int team) {
        super(x, y, team);
        this.obrazokFigurkyC = this.nacitajObrazok("pesiakC.png");
        this.obrazokFigurkyB = this.nacitajObrazok("pesiakB.png");
        typ = "Pesiak";
        hodnota = 10;
        pesiakPohyb = new ArrayList<Vec2>();
    }

    @Override
    public boolean skontrolujPohyb(int newX, int newY, Figurka[][] hraciePole) {
        for (int i = 0; i < deathZone.size(); i++) {
            if (deathZone.get(i).getY() == newY && deathZone.get(i).getX() == newX
                    && hraciePole[deathZone.get(i).getY()][deathZone.get(i).getX()] != null) {
                return true;
            }
        }
        for (int i = 0; i < pesiakPohyb.size(); i++) {
            if (pesiakPohyb.get(i).getY() == newY && pesiakPohyb.get(i).getX() == newX) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();
        pesiakPohyb.clear();
        if (((team == 1) ? this.y + 1 - 2 * team >= 0 : this.y + 1 - 2 * team < 8) && this.x + 1 < 8
                && (hraciePole[this.y + 1 - 2 * team][this.x + 1] == null
                        || hraciePole[this.y + 1 - 2 * team][this.x + 1].team != this.team)) {
            deathZone.add(new Vec2(this.y + 1 - 2 * team, this.x + 1));
        } else if (((team == 1) ? this.y + 1 - 2 * team >= 0 : this.y + 1 - 2 * team < 8) && this.x - 1 >= 0
                && (hraciePole[this.y + 1 - 2 * team][this.x - 1] == null
                        || hraciePole[this.y + 1 - 2 * team][this.x - 1].team != this.team)) {
            deathZone.add(new Vec2(this.y + 1 - 2 * team, this.x - 1));
        }
        if (this.y + 1 - 2 * team >= 0 && this.y + 1 - 2 * team < 8
                && hraciePole[this.y + 1 - 2 * team][this.x] == null) {
            pesiakPohyb.add(new Vec2(this.y + 1 - 2 * team, this.x));
            if (this.y + 2 - 4 * team >= 0 && this.y + 2 - 4 * team < 8 && this.y == 1 +
                    team * 5
                    && hraciePole[this.y + 2 - 4 * team][this.x] == null) {
                pesiakPohyb.add(new Vec2(this.y + 2 - 4 * team, this.x));
            }
        }

    }
}
