package sach.Figurky;

import sach.Vec2;

public class Kral extends Figurka {

    public Kral(int x, int y, int team) {
        super(x, y, team);
        this.obrazokFigurkyC = this.nacitajObrazok("kralC.png");
        this.obrazokFigurkyB = this.nacitajObrazok("kralB.png");
        typ = "Kral";
        maMat = false;
        hodnota = 900;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();

        kombinacie = this.generateKomb(new int[] { 1, 0, -1 }, true);
        for (int i = 0; i < kombinacie.size(); i++) {
            if (this.y + kombinacie.get(i)[0] < 8 && this.y + kombinacie.get(i)[0] >= 0
                    && this.x + kombinacie.get(i)[1] < 8 && this.x + kombinacie.get(i)[1] >= 0
                    && (hraciePole[this.y + kombinacie.get(i)[0]][this.x + kombinacie.get(i)[1]] == null
                            || hraciePole[this.y + kombinacie.get(i)[0]][this.x
                                    + kombinacie.get(i)[1]].team != this.team)) {
                deathZone.add(new Vec2(this.y + kombinacie.get(i)[0], this.x + kombinacie.get(i)[1]));
            }
        }
        // if (this.x + 1 < 8 && (hraciePole[this.y][this.x + 1] == null
        // || hraciePole[this.y][this.x + 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y, this.x + 1));
        // }
        // if (this.y - 1 >= 0 && this.x + 1 < 8 && (hraciePole[this.y - 1][this.x + 1]
        // == null
        // || hraciePole[this.y - 1][this.x + 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y - 1, this.x + 1));
        // }
        // if (this.y + 1 < 8
        // && (hraciePole[this.y + 1][this.x] == null || hraciePole[this.y +
        // 1][this.x].team != this.team)) {
        // deathZone.add(new Vec2(this.y + 1, this.x));
        // }
        // if (this.y + 1 < 8 && this.x - 1 >= 0 && (hraciePole[this.y + 1][this.x - 1]
        // == null
        // || hraciePole[this.y + 1][this.x - 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y + 1, this.x - 1));
        // }
        // if (this.x - 1 >= 0
        // && (hraciePole[this.y][this.x - 1] == null || hraciePole[this.y][this.x -
        // 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y, this.x - 1));
        // }
        // if (this.y - 1 >= 0
        // && (hraciePole[this.y - 1][this.x] == null || hraciePole[this.y -
        // 1][this.x].team != this.team)) {
        // deathZone.add(new Vec2(this.y - 1, this.x));
        // }
        // if (this.y - 1 >= 0 && this.x - 1 >= 0 && (hraciePole[this.y - 1][this.x - 1]
        // == null
        // || hraciePole[this.y - 1][this.x - 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y - 1, this.x - 1));
        // }
        deathZone.add(new Vec2(this.y, this.x));
    }
}
