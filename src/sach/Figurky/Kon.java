package sach.Figurky;

import sach.Vec2;

public class Kon extends Figurka {
    public Kon(int x, int y, int team) {
        super(x, y, team);
        this.obrazokFigurkyC = this.nacitajObrazok("konC.png");
        this.obrazokFigurkyB = this.nacitajObrazok("konB.png");
        typ = "Kon";
        hodnota = 30;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();

        kombinacie = this.generateKomb(new int[] { -2, -1, 1, 2 }, false);
        // for (int[] combination : kombinacie) {
        // System.out.println(Arrays.toString(combination));
        // }
        // System.out.println("-----");
        for (int i = 0; i < kombinacie.size(); i++) {
            if (this.y + kombinacie.get(i)[0] < 8 && this.y + kombinacie.get(i)[0] >= 0
                    && this.x + kombinacie.get(i)[1] < 8 && this.x + kombinacie.get(i)[1] >= 0
                    && (hraciePole[this.y + kombinacie.get(i)[0]][this.x + kombinacie.get(i)[1]] == null
                            || hraciePole[this.y + kombinacie.get(i)[0]][this.x
                                    + kombinacie.get(i)[1]].team != this.team)) {
                deathZone.add(new Vec2(this.y + kombinacie.get(i)[0], this.x + kombinacie.get(i)[1]));
            }
        }

        // if (this.y + kombinacie < 8 && this.x + 1 < 8 && (hraciePole[this.y +
        // 2][this.x + 1] == null
        // || hraciePole[this.y + 2][this.x + 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y + 2, this.x + 1));
        // }
        // if (this.y + 2 < 8 && this.x - 1 >= 0 && (hraciePole[this.y + 2][this.x - 1]
        // == null
        // || hraciePole[this.y + 2][this.x - 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y + 2, this.x - 1));
        // }
        // if (this.y - 2 >= 0 && this.x + 1 < 8 && (hraciePole[this.y - 2][this.x + 1]
        // == null
        // || hraciePole[this.y - 2][this.x + 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y - 2, this.x + 1));
        // }
        // if (this.y - 2 >= 0 && this.x - 1 >= 0 && (hraciePole[this.y - 2][this.x - 1]
        // == null
        // || hraciePole[this.y - 2][this.x - 1].team != this.team)) {
        // deathZone.add(new Vec2(this.y - 2, this.x - 1));
        // }
        // if (this.y + 1 < 8 && this.x + 2 < 8 && (hraciePole[this.y + 1][this.x + 2]
        // == null
        // || hraciePole[this.y + 1][this.x + 2].team != this.team)) {
        // deathZone.add(new Vec2(this.y + 1, this.x + 2));
        // }
        // if (this.y + 1 < 8 && this.x - 2 >= 0 && (hraciePole[this.y + 1][this.x - 2]
        // == null
        // || hraciePole[this.y + 1][this.x - 2].team != this.team)) {
        // deathZone.add(new Vec2(this.y + 1, this.x - 2));
        // }
        // if (this.y - 1 >= 0 && this.x + 2 < 8 && (hraciePole[this.y - 1][this.x + 2]
        // == null
        // || hraciePole[this.y - 1][this.x + 2].team != this.team)) {
        // deathZone.add(new Vec2(this.y - 1, this.x + 2));
        // }
        // if (this.y - 1 >= 0 && this.x - 2 >= 0 && (hraciePole[this.y - 1][this.x - 2]
        // == null
        // || hraciePole[this.y - 1][this.x - 2].team != this.team)) {
        // deathZone.add(new Vec2(this.y - 1, this.x - 2));
        // }
    }
}
