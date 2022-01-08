package sach.Figurky;

import sach.Vec2;
import sach.Enums.TeamEnum;

public class Dama extends Figurka {
    public Dama(int x, int y, TeamEnum team) {
        super(x, y, team);
        super.obrazokFigurkyC = super.nacObrazkov.nacitajObrazok("damaC.png");
        super.obrazokFigurkyB = super.nacObrazkov.nacitajObrazok("damaB.png");
        super.hodnota = 90;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();

        kombinacie = this.generateKomb(new int[] { 1, -1 }, true);
        for (int[] kombinacia : kombinacie) {
            int pX = kombinacia[0], pY = kombinacia[1];
            for (int i = 0; i < 8; i++) {
                if ((this.x + pX * i) >= 8 || (this.x + pX * i) < 0 || this.y + pY * i >= 8 || this.y + pY * i < 0) {
                    break;
                }
                if (hraciePole[this.y + pY * i][this.x + pX * i] == this) {
                    continue;
                }
                if (hraciePole[this.y + pY * i][this.x + pX * i] == null) {
                    deathZone.add(new Vec2(this.y + pY * i, this.x + pX * i));
                } else if (hraciePole[this.y + pY * i][this.x + pX * i].team != this.team) {
                    deathZone.add(new Vec2(this.y + pY * i, this.x + pX * i));
                    break;
                } else {
                    break;
                }
            }
        }

        for (int j = 0; j < 2; j++) {
            for (int i = this.x; ((j % 2 == 0) ? i < 8 : i >= 0); i += ((j % 2 == 0) ? 1 : -1)) {
                if (hraciePole[this.y][i] == this) {
                    continue;
                }
                if (hraciePole[this.y][i] == null) {
                    deathZone.add(new Vec2(this.y, i));
                } else if (hraciePole[this.y][i].team != this.team) {
                    deathZone.add(new Vec2(this.y, i));
                    break;
                } else {
                    break;
                }
            }
        }
        for (int j = 0; j < 2; j++) {
            for (int i = this.y; ((j % 2 == 0) ? i < 8 : i >= 0); i += ((j % 2 == 0) ? 1 : -1)) {
                if (hraciePole[i][this.x] == this) {
                    continue;
                }
                if (hraciePole[i][this.x] == null) {
                    deathZone.add(new Vec2(i, this.x));
                } else if (hraciePole[i][this.x].team != this.team) {
                    deathZone.add(new Vec2(i, this.x));
                    break;
                } else {
                    break;
                }
            }
        }
    }
}
