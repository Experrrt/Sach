package sach;

import sach.Enums.TeamEnum;
import sach.Figurky.*;
import java.util.ArrayList;

public class HracieMody {
    // private Figurka[][] hraciePole;

    public void nacitajFigurky(Figurka[][] hraciePole, ArrayList<Figurka> figurkyNaPoly) {
        // hraciePole = new Figurka[8][8];

        for (int i = 0; i < 2; i++) {
            // pesiaci
            for (int y = 0; y < 8; y++) {
                Pesiak pesiak = new Pesiak(y, 1 + 5 * i, (i == 0) ? TeamEnum.CIERNY : TeamEnum.BIELY);
                hraciePole[1 + 5 * i][y] = pesiak;
                figurkyNaPoly.add(pesiak);
            }
            // veze
            for (int y = 0; y < 2; y++) {
                Veza veza = new Veza(7 * y, 7 * i, (i == 0) ? TeamEnum.CIERNY : TeamEnum.BIELY);
                hraciePole[7 * i][7 * y] = veza;
                figurkyNaPoly.add(veza);
            }
            // kone
            for (int y = 0; y < 2; y++) {
                Kon kon = new Kon(1 + 5 * y, 7 * i, (i == 0) ? TeamEnum.CIERNY : TeamEnum.BIELY);
                hraciePole[7 * i][1 + 5 * y] = kon;
                figurkyNaPoly.add(kon);
            }
            // strelci
            for (int y = 0; y < 2; y++) {
                Strelec strelec = new Strelec(2 + 3 * y, 7 * i, (i == 0) ? TeamEnum.CIERNY : TeamEnum.BIELY);
                hraciePole[7 * i][2 + 3 * y] = strelec;
                figurkyNaPoly.add(strelec);
            }
            // krali
            Kral kral = new Kral(4, 7 * i, (i == 0) ? TeamEnum.CIERNY : TeamEnum.BIELY);
            hraciePole[7 * i][4] = kral;
            figurkyNaPoly.add(kral);
            // // damy
            Dama dama = new Dama(3, 7 * i, (i == 0) ? TeamEnum.CIERNY : TeamEnum.BIELY);
            hraciePole[7 * i][3] = dama;
            figurkyNaPoly.add(dama);
        }
        // return hraciePole;
    }
}
