package sach.Figurky;

import sach.Vec2;
import sach.Enums.TeamEnum;

public class Kral extends Figurka {
    public Boolean maMat;

    public Kral(int x, int y, TeamEnum team) {
        super(x, y, team);
        super.obrazokFigurkyC = super.nacObrazkov.nacitajObrazok("kralC.png");
        super.obrazokFigurkyB = super.nacObrazkov.nacitajObrazok("kralB.png");
        super.hodnota = 900;
        this.maMat = false;
    }

    @Override
    public void vypocitajDeathZone(Figurka[][] hraciePole) {
        deathZone.clear();
        Boolean mozesPridat = true;

        kombinacie = this.generateKomb(new int[] { 1, 0, -1 }, true);
        for (int[] kombinacia : kombinacie) {
            if (this.y + kombinacia[0] < 8 && this.y + kombinacia[0] >= 0
                    && this.x + kombinacia[1] < 8 && this.x + kombinacia[1] >= 0
                    && (hraciePole[this.y + kombinacia[0]][this.x + kombinacia[1]] == null
                            || hraciePole[this.y + kombinacia[0]][this.x
                                    + kombinacia[1]].team != this.team)) {
                for (Figurka[] rad : hraciePole) {
                    for (Figurka figurka : rad) {
                        if (figurka != null && figurka.team != this.team) {
                            for (Vec2 deathZona : figurka.deathZone) {
                                if (deathZona.getX() == this.x + kombinacia[1]
                                        && deathZona.getY() == this.y + kombinacia[0]) {
                                    mozesPridat = false;
                                }
                            }
                        }
                    }
                }
                if (mozesPridat) {
                    deathZone.add(new Vec2(this.y + kombinacia[0], this.x + kombinacia[1]));
                }
                mozesPridat = true;
            }
        }
        deathZone.add(new Vec2(this.y, this.x));
    }
}
