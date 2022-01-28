package sach;

import java.awt.*;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;
import sach.AI.Bot;

public class MainMenuGui {
    private int sirka, vyska, offSetX, offSetY;
    private float scale;
    private ArrayList<Button> buttony = new ArrayList<>();
    private BufferedImage pozadieObrazok;

    public MainMenuGui(int sirka, int vyska, int offSetX, int offSetY, float scale) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.scale = scale;
        this.pozadieObrazok = new NacitavacObrazkov().nacitajObrazok("pozadie.png");

        buttony.add(new Button((sirka + offSetX) / 2 - (int) (scale * 150), (vyska + offSetY) / 2 - (int) (200 * scale),
                (int) (300 * scale), (int) (100 * scale),
                "Hrac vs Hrac",
                new HraciePoleGui(
                        new ArrayList<>(Arrays.asList(new Client(), new Client())), sirka, vyska, offSetX,
                        offSetY, false)));
        buttony.add(new Button((sirka + offSetX) / 2 - (int) (scale * 150), (vyska + offSetY) / 2 + (int) (0 * scale),
                (int) (300 * scale), (int) (100 * scale),
                "Hrac vs Bot",
                new HraciePoleGui(
                        new ArrayList<>(Arrays.asList(new Bot(), new Client())), sirka, vyska, offSetX, offSetY,
                        false)));
        try {
            buttony.add(
                    new Button((sirka + offSetX) / 2 - (int) (scale * 150), (vyska + offSetY) / 2 + (int) (200 * scale),
                            (int) (300 * scale), (int) (100 * scale),
                            "Multiplayer Server",
                            new HraciePoleGui(
                                    new ArrayList<>(Arrays.asList(new Server())), sirka, vyska, offSetX, offSetY,
                                    true)));
            buttony.add(
                    new Button((sirka + offSetX) / 2 - (int) (scale * 150), (vyska + offSetY) / 2 + (int) (400 * scale),
                            (int) (300 * scale), (int) (100 * scale),
                            "Multiplayer Join",
                            new HraciePoleGui(
                                    new ArrayList<>(Arrays.asList(new InyClient())), sirka, vyska, offSetX, offSetY,
                                    true)));
        } catch (Exception e) {
        }
    }

    public HraciePoleGui skontrolujOdkliknutie(Vec2 kliknutie) {
        for (Button button : buttony) {
            if (button.contains(kliknutie.getY(), kliknutie.getX())) {
                return button.pole;
            }
        }
        return null;
    }

    public void skontrolujHover(Vec2 kurzor) {
        for (Button button : buttony) {
            if (button.contains(kurzor.getY(), kurzor.getX())) {
                button.farba = new Color(82, 42, 11);
            } else {
                button.farba = new Color(139, 69, 19);
            }
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(pozadieObrazok, 0, 0, null);
        for (Button button : buttony) {
            g2d.setColor(button.farba);
            g2d.fill(button.rec);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, (int) (30 * scale)));
            g2d.drawString(button.text,
                    (int) button.rec.getBounds().getX() + (60 * scale) - ((button.text.length() > 15)
                            ? (button.text.length())
                            : 0),
                    (int) button.rec.getBounds().getY() + (60 * scale));
        }
    }

    private class Button {
        private String text;
        private HraciePoleGui pole;
        private RoundRectangle2D rec;
        private Color farba = new Color(139, 69, 19);

        public Button(int x, int y, int width, int height, String text, HraciePoleGui pole) {
            this.text = text;
            this.pole = pole;
            rec = new RoundRectangle2D.Double(x, y, width, height, 50, 50);
        }

        public boolean contains(double x, double y) {
            return rec.contains(x, y);
        }
    }
}
