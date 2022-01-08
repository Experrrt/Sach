package sach.AI;

import sach.Hrac;
import sach.Vec2;
import sach.Enums.TeamEnum;
import sach.Figurky.*;
import java.util.ArrayList;
import java.util.Random;

public class Bot implements Hrac {
    Random rnd = new Random();

    @Override
    public Figurka spravTah(Figurka[][] hraciePole, Vec2 newS, ArrayList<Figurka> figurkyNaPoly) {
        Figurka zobrataFigurka = hraciePole[1][6];
        newS.set(6, 2);
        // newY = 2;
        for (int i = 0; i < figurkyNaPoly.size(); i++) {
            if (figurkyNaPoly.get(i).team == TeamEnum.CIERNY) {
                for (int j = 0; j < figurkyNaPoly.get(i).deathZone.size(); j++) {
                    if (hraciePole[figurkyNaPoly.get(i).deathZone.get(j).getY()][figurkyNaPoly.get(i).deathZone.get(j)
                            .getX()] != null
                            && hraciePole[figurkyNaPoly.get(i).deathZone.get(j).getY()][figurkyNaPoly.get(i).deathZone
                                    .get(j)
                                    .getX()].hodnota >= figurkyNaPoly.get(i).hodnota
                            && hraciePole[figurkyNaPoly.get(i).deathZone.get(j).getY()][figurkyNaPoly.get(i).deathZone
                                    .get(j)
                                    .getX()].team != figurkyNaPoly.get(i).team) {
                        zobrataFigurka = figurkyNaPoly.get(i);
                        newS.set(figurkyNaPoly.get(i).deathZone
                                .get(j)
                                .getX(), figurkyNaPoly.get(i).deathZone.get(j).getY());
                    }
                    if (zobrataFigurka == null) {
                        int rn;
                        do {
                            rn = rnd.nextInt(figurkyNaPoly.size());
                            if (figurkyNaPoly.get(rn).deathZone.size() != 0) {
                                if (figurkyNaPoly.get(rn) instanceof Pesiak) {
                                    if (!(figurkyNaPoly.get(rn) instanceof Pesiak)) {
                                        continue;
                                    }
                                    Pesiak pesiak = (Pesiak) figurkyNaPoly.get(rn);
                                    int x = pesiak.pesiakPohyby
                                            .get(rnd.nextInt(pesiak.pesiakPohyby.size())).getX();
                                    int y = pesiak.pesiakPohyby
                                            .get(rnd.nextInt(pesiak.pesiakPohyby.size())).getY();
                                    newS.set(x, y);
                                    zobrataFigurka = figurkyNaPoly.get(rn);
                                    break;
                                }
                                int x = figurkyNaPoly.get(rn).deathZone
                                        .get(rnd.nextInt(figurkyNaPoly.get(rn).deathZone.size())).getX();
                                int y = figurkyNaPoly.get(rn).deathZone
                                        .get(rnd.nextInt(figurkyNaPoly.get(rn).deathZone.size())).getY();
                                newS.set(x, y);
                                zobrataFigurka = figurkyNaPoly.get(rn);
                            }
                        } while (figurkyNaPoly.get(rn).team != TeamEnum.CIERNY);
                    }
                }
            }
        }

        return zobrataFigurka;
    }
}
