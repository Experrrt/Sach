package sach;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import sach.Figurky.Figurka;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class Gui extends JPanel implements Runnable {
    private JFrame jFrame;
    private HraciePole hraciePole;
    private Stopky stopky;
    private Thread t;
    private Figurka kliknutaFigurka;
    private int offSetY, offSetX, vyska, sirka;

    public Gui() {
        jFrame = new JFrame();
        hraciePole = new HraciePole();
        stopky = new Stopky();
        t = new Thread(this);
        offSetY = 150;
        offSetX = 0;
        vyska = sirka = 800;

        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setPreferredSize(new Dimension(2 * offSetX + sirka, 2 * offSetY + vyska - 50));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                hraciePole.client.klikni((e.getX() - offSetX) / 100, (e.getY() - offSetY) / 100);
                kliknutaFigurka = hraciePole.skontrolujKliknutie((e.getX() - offSetX) / 100,
                        (e.getY() - offSetY) / 100);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                hraciePole.client.odKlikni((e.getX() - offSetX) / 100, (e.getY() - offSetY) / 100);
                kliknutaFigurka = null;
                // repaint();
            }

        });
        jFrame.setResizable(false);
        jFrame.add(this, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Števo Šarišký");
    }

    public void spustiHru() {
        jFrame.pack();
        jFrame.setVisible(true);
        stopky.start();
        t.start();
        hraciePole.aiTah();
    }

    @Override
    public void run() {
        do {
            repaint();
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
                System.out.println("Neni dobre");
            }
        } while (!hraciePole.koniecHry);
        repaint();
        stopky.pause();
        try {
            t.join();
        } catch (Exception e) {
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Figurka figurkaNaRender;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);

        // Render hornej listy
        g.setColor(new Color(238, 238, 213));
        g.fillRect(0, 0, 800, 1100);
        g.setColor(new Color(184, 115, 51));
        g2d.setStroke(new java.awt.BasicStroke(5));
        g.drawRect(2, 2, offSetX + sirka - 5, offSetY - 5);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString(stopky.getFormatovanyCas(), (offSetX + sirka) / 2 - 45, 50);
        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        g.drawString("Na rade je: " + ((hraciePole.teamNaRade) ? "Biely" : "Čierny"), 25, 40);

        // Render sachoveho pola
        for (int i = 0; i < hraciePole.hraciePole.length; i++) {
            for (int j = 0; j < hraciePole.hraciePole[i].length; j++) {
                figurkaNaRender = hraciePole.hraciePole[i][j];
                if ((i + j) % 2 != 0) {
                    g.setColor(new Color(125, 148, 93));
                } else {
                    g.setColor(new Color(238, 238, 213));
                }
                g.fillRect(offSetX + j * 100, offSetY + i * 100, 100, 100);

                if (figurkaNaRender != null) {
                    g.drawImage(
                            ((figurkaNaRender.team == 1) ? figurkaNaRender.obrazokFigurkyB
                                    : figurkaNaRender.obrazokFigurkyC),
                            offSetX + figurkaNaRender.x * 100 + 20,
                            offSetY + figurkaNaRender.y * 100 + 20,
                            null);
                }
            }
        }
        if (kliknutaFigurka != null) {
            ArrayList<Vec2> moveSpot = (kliknutaFigurka.typ == "Pesiak")
                    ? kliknutaFigurka.pesiakPohyb
                    : kliknutaFigurka.deathZone;
            g.setColor(new Color(0, 0, 0, 100));
            for (int j2 = 0; j2 < moveSpot.size(); j2++) {
                g.fillRect(offSetX + moveSpot.get(j2).getX() * 100 + 20,
                        offSetY + moveSpot.get(j2).getY() * 100 + 20, 60, 60);
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
        g2d.setStroke(new java.awt.BasicStroke(5));
        g.drawRect(2, vyska + offSetY, offSetX + sirka - 5, offSetY - 52);
        for (int i = 0; i < hraciePole.vypadnuteFigurky.size(); i++) {
            figurkaNaRender = hraciePole.vypadnuteFigurky.get(i);
            if (figurkaNaRender != null) {
                g.drawImage(
                        ((figurkaNaRender.team == 1) ? figurkaNaRender.obrazokFigurkyB
                                : figurkaNaRender.obrazokFigurkyC),
                        ((i < 16) ? offSetX + i * 45 + 20 : offSetX + (i - 16) * 45 + 25),
                        ((i < 16) ? vyska + offSetY : vyska + offSetY + 25), null);
            }
        }
    }
}