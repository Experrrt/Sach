package sach;

import java.awt.Color;
import sach.Figurky.Figurka;
import sach.Figurky.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import sach.Enums.TeamEnum;
import sach.Enums.VyhraEnum;

public class HraciePoleGui extends HraciePole {
    private int sirka, vyska, offSetX, offSetY;
    private Stopky stopky;
    HashMap<Class<?>, BufferedImage> mapaObrazkovB = new HashMap<>();
    HashMap<Class<?>, BufferedImage> mapaObrazkovC = new HashMap<>();

    public HraciePoleGui(ArrayList<Hrac> hraci, int sirka, int vyska, int offSetX, int offSetY) {
        super(hraci);
        this.sirka = sirka;
        this.vyska = vyska;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.stopky = new Stopky();
        nacitajObrazky();
    }

    private void nacitajObrazky() {
        NacitavacObrazkov nacitavac = new NacitavacObrazkov();
        mapaObrazkovB.put(Pesiak.class, nacitavac.nacitajObrazok("pesiakB.png"));
        mapaObrazkovC.put(Pesiak.class, nacitavac.nacitajObrazok("pesiakC.png"));
        mapaObrazkovB.put(Strelec.class, nacitavac.nacitajObrazok("strelecB.png"));
        mapaObrazkovC.put(Strelec.class, nacitavac.nacitajObrazok("strelecC.png"));
        mapaObrazkovB.put(Kon.class, nacitavac.nacitajObrazok("konB.png"));
        mapaObrazkovC.put(Kon.class, nacitavac.nacitajObrazok("konC.png"));
        mapaObrazkovB.put(Dama.class, nacitavac.nacitajObrazok("damaB.png"));
        mapaObrazkovC.put(Dama.class, nacitavac.nacitajObrazok("damaC.png"));
        mapaObrazkovB.put(Veza.class, nacitavac.nacitajObrazok("vezaB.png"));
        mapaObrazkovC.put(Veza.class, nacitavac.nacitajObrazok("vezaC.png"));
        mapaObrazkovB.put(Kral.class, nacitavac.nacitajObrazok("kralB.png"));
        mapaObrazkovC.put(Kral.class, nacitavac.nacitajObrazok("kralC.png"));
    }

    @Override
    public void spustiStopky() {
        stopky.start();
    }

    @Override
    public void zastavStopky() {
        stopky.stop();
    }

    @Override
    public void paint(Graphics2D g) {
        Figurka figurkaNaRender;
        // Render hornej listy
        g.setColor(new Color(238, 238, 213));
        g.fillRect(0, 0, 800, 1100);
        g.setColor(new Color(184, 115, 51));
        g.setStroke(new java.awt.BasicStroke(5));
        g.drawRect(2, 2, offSetX + sirka - 5, offSetY - 5);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString(stopky.getFormatovanyCas(), (offSetX + sirka) / 2 - 45, 50);
        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        g.drawString("Na rade je: " + ((teamNaRade == TeamEnum.BIELY) ? "Biely" : "Čierny"),
                25, 40);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        if (vysledokHry == VyhraEnum.VYHRA_B) {
            g.drawString("Vyhral Biely", sirka / 2 - 90, 100);
            stopky.stop();
        } else if (vysledokHry == VyhraEnum.VYHRA_C) {
            g.drawString("Vyhral Cierny", sirka / 2 - 90, 100);
            stopky.stop();
        } else if (vysledokHry == VyhraEnum.REMIZA) {
            g.drawString("Remiza", sirka / 2 - 70, 100);
            stopky.stop();
        }

        // Render sachoveho pola
        for (int i = 0; i < hraciePole.length; i++) {
            for (int j = 0; j < hraciePole[i].length; j++) {
                figurkaNaRender = hraciePole[i][j];
                if ((i + j) % 2 != 0) {
                    g.setColor(new Color(125, 148, 93));
                } else {
                    g.setColor(new Color(238, 238, 213));
                }
                g.fillRect(offSetX + j * 100, offSetY + i * 100, 100, 100);

                if (figurkaNaRender != null) {
                    g.drawImage(
                            (figurkaNaRender.team == TeamEnum.BIELY) ? mapaObrazkovB.get(figurkaNaRender.getClass())
                                    : mapaObrazkovC.get(figurkaNaRender.getClass()),
                            offSetX + figurkaNaRender.x * 100 + 20,
                            offSetY + figurkaNaRender.y * 100 + 20,
                            null);

                    // g.drawImage(
                    // ((figurkaNaRender.team == TeamEnum.BIELY) ? figurkaNaRender.obrazokFigurkyB
                    // : figurkaNaRender.obrazokFigurkyC),
                    // offSetX + figurkaNaRender.x * 100 + 20,
                    // offSetY + figurkaNaRender.y * 100 + 20,
                    // null);
                }
            }
        }
        if (zobrataFigurka != null) {
            g.setColor(new Color(0, 0, 0, 100));
            for (Vec2 moveSpot : (zobrataFigurka instanceof Pesiak)
                    ? ((Pesiak) zobrataFigurka).pesiakPohyby
                    : zobrataFigurka.deathZone) {
                g.fillRect(offSetX + moveSpot.getX() * 100 + 20,
                        offSetY + moveSpot.getY() * 100 + 20, 60, 60);
                // g.drawImage(
                // ((1 == 0) ? figurkaNaRender.obrazokFigurkyB
                // : figurkaNaRender.obrazokFigurkyC),
                // offSetX + hraciePole.zobrataFigurka.deathZone.get(j2).getX() * 100 + 20,
                // offSetY + hraciePole.zobrataFigurka.deathZone.get(j2).getY() * 100 + 20,
                // null);
            }
        }
        // Render spodnej listy
        g.setColor(new Color(184, 115, 51));
        g.setStroke(new java.awt.BasicStroke(5));
        g.drawRect(2, vyska + offSetY, offSetX + sirka - 5, offSetY - 52);
        for (int i = 0; i < vypadnuteFigurky.size(); i++) {
            figurkaNaRender = vypadnuteFigurky.get(i);
            if (figurkaNaRender != null) {
                g.drawImage(
                        (figurkaNaRender.team == TeamEnum.BIELY) ? mapaObrazkovB.get(figurkaNaRender.getClass())
                                : mapaObrazkovC.get(figurkaNaRender.getClass()),
                        ((i < 16) ? offSetX + i * 45 + 20 : offSetX + (i - 16) * 45 + 25),
                        ((i < 16) ? vyska + offSetY : vyska + offSetY + 25), null);
            }
        }
    }

}
