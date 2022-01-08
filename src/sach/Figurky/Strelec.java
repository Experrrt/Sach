package sach.Figurky;

import sach.Vec2;
import sach.Enums.TeamEnum;

public class Strelec extends Figurka {
    public Strelec(int x, int y, TeamEnum team) {
        super(x, y, team);
        super.obrazokFigurkyC = super.nacObrazkov.nacitajObrazok("strelecC.png");
        super.obrazokFigurkyB = super.nacObrazkov.nacitajObrazok("strelecB.png");
        super.hodnota = 30;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();
        kombinacie = this.generateKomb(new int[] { 1, -1 }, true);

        // for (int[] combination : kombinacie) {
        // System.out.println(Arrays.toString(combination));
        // }
        // System.out.println("-----");

        // for (int j = 0; j < hraciePole.length; j++) {

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
        // for (int i = 0; i < 8; i++) {
        // if (this.x + 1 * i >= 8 || this.y - 1 * i < 0) {
        // break;
        // }
        // if (hraciePole[this.y - 1 * i][this.x + 1 * i] == this) {
        // continue;
        // }
        // if (hraciePole[this.y - 1 * i][this.x + 1 * i] == null) {
        // deathZone.add(new Vec2(this.y - 1 * i, this.x + 1 * i));
        // } else if (hraciePole[this.y - 1 * i][this.x + 1 * i].team != this.team) {
        // deathZone.add(new Vec2(this.y - 1 * i, this.x + 1 * i));
        // break;
        // } else {
        // break;
        // }
        // }
        // for (int i = 0; i < 8; i++) {
        // if (this.x - 1 * i < 0 || this.y - 1 * i < 0) {
        // break;
        // }
        // if (hraciePole[this.y - 1 * i][this.x - 1 * i] == this) {
        // continue;
        // }
        // if (hraciePole[this.y - 1 * i][this.x - 1 * i] == null) {
        // deathZone.add(new Vec2(this.y - 1 * i, this.x - 1 * i));
        // } else if (hraciePole[this.y - 1 * i][this.x - 1 * i].team != this.team) {
        // deathZone.add(new Vec2(this.y - 1 * i, this.x - 1 * i));
        // break;
        // } else {
        // break;
        // }
        // }
        // for (int i = 0; i < 8; i++) {
        // if (this.x - 1 * i < 0 || this.y + 1 * i >= 8) {
        // break;
        // }
        // if (hraciePole[this.y + 1 * i][this.x - 1 * i] == this) {
        // continue;
        // }
        // if (hraciePole[this.y + 1 * i][this.x - 1 * i] == null) {
        // deathZone.add(new Vec2(this.y + 1 * i, this.x - 1 * i));
        // } else if (hraciePole[this.y + 1 * i][this.x - 1 * i].team != this.team) {
        // deathZone.add(new Vec2(this.y + 1 * i, this.x - 1 * i));
        // break;
        // } else {
        // break;
        // }
        // }
    }
}
