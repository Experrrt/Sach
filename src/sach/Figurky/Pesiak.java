package sach.Figurky;

import java.util.ArrayList;
import sach.Vec2;
import sach.Enums.TeamEnum;

public class Pesiak extends Figurka {
    public ArrayList<Vec2> pesiakPohyby;

    public Pesiak(int x, int y, TeamEnum team) {
        super(x, y, team);
        super.hodnota = 10;
        this.pesiakPohyby = new ArrayList<Vec2>();
    }

    @Override
    public boolean skontrolujPohyb(int newX, int newY, Figurka[][] hraciePole) {
        for (Vec2 pohyb : pesiakPohyby) {
            if (pohyb.getY() == newY && pohyb.getX() == newX) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();
        pesiakPohyby.clear();
        int team = this.team.teamHodnota;
        if (((team == 1) ? this.y + 1 - 2 * team >= 0 : this.y + 1 - 2 * team < 8) && this.x + 1 < 8
                && (hraciePole[this.y + 1 - 2 * team][this.x + 1] != null
                        && hraciePole[this.y + 1 - 2 * team][this.x + 1].team != this.team)) {
            deathZone.add(new Vec2(this.y + 1 - 2 * team, this.x + 1));
            if (hraciePole[this.y + 1 - 2 * team][this.x + 1].team != this.team) {
                pesiakPohyby.add(new Vec2(this.y + 1 - 2 * team, this.x + 1));
            }
        } else if (((team == 1) ? this.y + 1 - 2 * team >= 0 : this.y + 1 - 2 * team < 8) && this.x - 1 >= 0
                && (hraciePole[this.y + 1 - 2 * team][this.x - 1] != null
                        && hraciePole[this.y + 1 - 2 * team][this.x - 1].team != this.team)) {
            deathZone.add(new Vec2(this.y + 1 - 2 * team, this.x - 1));
            if (hraciePole[this.y + 1 - 2 * team][this.x - 1].team != this.team) {
                pesiakPohyby.add(new Vec2(this.y + 1 - 2 * team, this.x - 1));
            }
        }
        if (this.y + 1 - 2 * team >= 0 && this.y + 1 - 2 * team < 8
                && (hraciePole[this.y + 1 - 2 * team][this.x] == null)) {
            pesiakPohyby.add(new Vec2(this.y + 1 - 2 * team, this.x));
            if (this.y + 2 - 4 * team >= 0 && this.y + 2 - 4 * team < 8 && this.y == 1 +
                    team * 5
                    && hraciePole[this.y + 2 - 4 * team][this.x] == null) {
                pesiakPohyby.add(new Vec2(this.y + 2 - 4 * team, this.x));
            }
        }

    }
}
