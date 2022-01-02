package sach.Figurky;

import sach.Vec2;

public class Veza extends Figurka {
    public Veza(int x, int y, int team) {
        super(x, y, team);
        this.obrazokFigurkyC = this.nacitajObrazok("vezaC.png");
        this.obrazokFigurkyB = this.nacitajObrazok("vezaB.png");
        typ = "Veza";
        hodnota = 50;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();
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

        // for (int i = this.x; i < 8; i++) {
        // if (hraciePole[this.y][i] == this) {
        // continue;
        // }
        // if (hraciePole[this.y][i] == null) {
        // deathZone.add(new Vec2(this.y, i));
        // } else if (hraciePole[this.y][i].team != this.team) {
        // deathZone.add(new Vec2(this.y, i));
        // break;
        // } else {
        // break;
        // }
        // }
        // for (int i = this.x; i >= 0; i--) {
        // if (hraciePole[this.y][i] == this) {
        // continue;
        // }
        // if (hraciePole[this.y][i] == null) {
        // deathZone.add(new Vec2(this.y, i));
        // } else if (hraciePole[this.y][i].team != this.team) {
        // deathZone.add(new Vec2(this.y, i));
        // break;
        // } else {
        // break;
        // }
        // }
        // for (int i = this.y; i < 8; i++) {
        // if (hraciePole[i][this.x] == this) {
        // continue;
        // }
        // if (hraciePole[i][this.x] == null) {
        // deathZone.add(new Vec2(i, this.x));
        // } else if (hraciePole[i][this.x].team != this.team) {
        // deathZone.add(new Vec2(i, this.x));
        // break;
        // } else {
        // break;
        // }
        // }
        // for (int i = this.y; i >= 0; i--) {
        // if (hraciePole[i][this.x] == this) {
        // continue;
        // }
        // if (hraciePole[i][this.x] == null) {
        // deathZone.add(new Vec2(i, this.x));
        // } else if (hraciePole[i][this.x].team != this.team) {
        // deathZone.add(new Vec2(i, this.x));
        // break;
        // } else {
        // break;
        // }
        // }
    }
}
