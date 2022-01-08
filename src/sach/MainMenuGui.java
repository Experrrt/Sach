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
    private ArrayList<Button> buttony = new ArrayList<>();
    private BufferedImage pozadieObrazok;

    public MainMenuGui(int sirka, int vyska, int offSetX, int offSetY) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.pozadieObrazok = new NacitavacObrazkov().nacitajObrazok("pozadie.png");

        buttony.add(new Button((sirka + offSetX) / 2 - 150, (vyska + offSetY) / 2 - 100, 300, 100, "Hrac vs Hrac",
                new HraciePoleGui(
                        new ArrayList<>(Arrays.asList(new Client(), new Client())), sirka, vyska, offSetX, offSetY)));
        buttony.add(new Button((sirka + offSetX) / 2 - 150, (vyska + offSetY) / 2 + 100, 300, 100, "Hrac vs Bot",
                new HraciePoleGui(
                        new ArrayList<>(Arrays.asList(new Bot(), new Client())), sirka, vyska, offSetX, offSetY)));
        buttony.add(new Button((sirka + offSetX) / 2 - 150, (vyska + offSetY) / 2 + 300, 300, 100, "Multiplayer",
                new HraciePoleGui(
                        new ArrayList<>(Arrays.asList(new Server())), sirka, vyska, offSetX, offSetY)));
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
            g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
            g2d.drawString(button.text,
                    (int) button.rec.getBounds().getX() + 60,
                    (int) button.rec.getBounds().getY() + 60);
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
