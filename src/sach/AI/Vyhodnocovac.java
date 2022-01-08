package sach.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import sach.Figurky.Figurka;
import sach.Figurky.Kral;
import sach.Figurky.Strelec;
import sach.Figurky.Kon;
import sach.Vec2;
import sach.Enums.TeamEnum;
import sach.Enums.VyhraEnum;

public class Vyhodnocovac {
    private HashMap<Class<?>, ArrayList<Figurka>> mapaFiguriek = new HashMap<>();;
    private ArrayList<Vec2> deathZoneTeamC = new ArrayList<Vec2>();;
    private ArrayList<Vec2> deathZoneTeamB = new ArrayList<Vec2>();;

    public VyhraEnum skontrolujKoniecHry(ArrayList<Figurka> figurkyNaPoly) {
        // ArrayList<Figurka> pKon, pKral, pPesiak, pStrelec;
        // pKon = new ArrayList<>();
        // pKral = new ArrayList<>();
        // pPesiak = new ArrayList<>();
        // pStrelec = new ArrayList<>();
        mapaFiguriek.clear();
        deathZoneTeamC.clear();
        deathZoneTeamB.clear();

        for (Figurka figurka : figurkyNaPoly) {
            if (mapaFiguriek.containsKey(figurka.getClass())) {
                mapaFiguriek.get(figurka.getClass()).add(figurka);
                continue;
            }
            mapaFiguriek.put(figurka.getClass(), new ArrayList<>(Arrays.asList(figurka)));

            if (figurka.team == TeamEnum.CIERNY) {
                deathZoneTeamC.addAll(figurka.deathZone);
            } else {
                deathZoneTeamB.addAll(figurka.deathZone);
            }
        }

        // remiza
        if (figurkyNaPoly.size() == 2 && GODEF(Kral.class).size() == 2) {
            return VyhraEnum.REMIZA;
        } else if (figurkyNaPoly.size() == 3 && GODEF(Kon.class).size() == 1
                || GODEF(Strelec.class).size() == 1 && GODEF(Kral.class).size() == 2) {
            return VyhraEnum.REMIZA;
        } else if ((figurkyNaPoly.size() == 4
                && (GODEF(Kon.class).size() <= 2 || GODEF(Strelec.class).size() <= 2)
                && (GODEF(Kral.class).size() == 2
                        && GODEF(Kon.class).get(0).team != GODEF(Kon.class).get(1).team
                        || GODEF(Kon.class).size() == 1 && GODEF(Strelec.class).size() == 1
                                && GODEF(Kon.class).get(0).team != GODEF(Strelec.class).get(0).team
                        || GODEF(Strelec.class).size() == 2
                                && GODEF(Strelec.class).get(0).team != GODEF(Strelec.class)
                                        .get(1).team))) {
            return VyhraEnum.REMIZA;
        } else if (figurkyNaPoly.size() == 4 && GODEF(Kon.class).size() == 2
                && GODEF(Kon.class).get(0).team == GODEF(Kon.class).get(1).team
                && GODEF(Kral.class).size() == 2) {
            return VyhraEnum.REMIZA;
        }
        // prehra
        for (int y = 0; y < GODEF(Kral.class).size(); y++) {
            int zabratePolicka = 0;
            Kral kralNaMat = (Kral) GODEF(Kral.class).get(y);
            Boolean mat = false;
            ArrayList<Vec2> deathZoneTeamu = (kralNaMat.team == TeamEnum.BIELY) ? deathZoneTeamC : deathZoneTeamB;
            for (int i = 0; i < kralNaMat.deathZone.size(); i++) {
                for (int j = 0; j < deathZoneTeamu.size(); j++) {
                    if (kralNaMat.deathZone.get(i).getX() == deathZoneTeamu.get(j).getX()
                            && kralNaMat.deathZone.get(i).getY() == deathZoneTeamu.get(j).getY()) {
                        zabratePolicka++;
                        break;
                    }
                    if (kralNaMat.x == deathZoneTeamu.get(j).getX() && kralNaMat.y == deathZoneTeamu.get(j).getY()) {
                        if (kralNaMat.maMat) {
                            if (kralNaMat.team == TeamEnum.BIELY) {
                                return (kralNaMat.team == TeamEnum.BIELY) ? VyhraEnum.VYHRA_C : VyhraEnum.VYHRA_B;
                            }
                        } else {
                            mat = true;
                        }
                    }
                }
            }
            kralNaMat.maMat = mat;
            if (zabratePolicka == kralNaMat.deathZone.size() && kralNaMat.deathZone.size() != 0) {
                return (kralNaMat.team == TeamEnum.BIELY) ? VyhraEnum.VYHRA_C : VyhraEnum.VYHRA_B;
            }
        }
        return VyhraEnum.NESKONCILA;
    }

    private ArrayList<Figurka> GODEF(Class<?> figurka) {
        return mapaFiguriek.getOrDefault(figurka, new ArrayList<>());
    }
}
