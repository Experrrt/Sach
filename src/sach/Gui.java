package sach;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import sach.Enums.VyhraEnum;

public class Gui extends JPanel implements Runnable {
    private JFrame jFrame = new JFrame();
    private HraciePole hraciePole;
    private MainMenuGui mainMenu;
    private Thread GuiThread = new Thread(this);
    private float scale = 1f;
    private int offSetY = 150, offSetX = 0, vyska = 800, sirka = 800;

    public Gui() {
        mainMenu = new MainMenuGui(sirka, vyska,
                offSetX, offSetY, scale);

        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setPreferredSize(
                new Dimension((int) ((2 * offSetX + sirka) * scale), (int) ((2 * offSetY + vyska - 50) * scale)));

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mainMenu != null) {
                    mainMenu.skontrolujHover(new Vec2(e.getX() / scale, e.getY() / scale));
                }
                try {
                    Thread.sleep(50L);
                } catch (Exception ee) {
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (hraciePole != null) {
                    hraciePole.skontrolujKliknutie((int) ((e.getX() - (offSetX * scale)) / (100 * scale)),
                            (int) ((e.getY() - (offSetY * scale)) / (100 * scale)));
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (hraciePole != null) {
                    hraciePole.skontrolujOdkliknutie((int) ((e.getX() - (offSetX * scale)) / (100 * scale)),
                            (int) ((e.getY() - (offSetY * scale)) / (100 * scale)));
                } else {
                    hraciePole = mainMenu
                            .skontrolujOdkliknutie(new Vec2(e.getX() / scale, e.getY() / scale));
                    spustiHru();
                }
                // repaint();
            }

        });
        jFrame.setResizable(false);
        jFrame.add(this, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Števo Šarišský");
    }

    public void spustiGui() {
        jFrame.pack();
        jFrame.setVisible(true);
        GuiThread.start();
    }

    private void spustiHru() {
        if (hraciePole != null) {
            hraciePole.skusSpustitOnline();
            hraciePole.spravTah();
            hraciePole.spustiStopky();
        }
    }

    @Override
    public void run() {
        do {
            if (hraciePole != null) {
                hraciePole.skusUpdate();
            }
            repaint();
            try {
                Thread.sleep(100L);
            } catch (Exception e) {
                System.out.println("Neni dobre");
            }
        } while (mainMenu != null || (hraciePole != null && hraciePole.vysledokHry == VyhraEnum.NESKONCILA));
        // repaint();
        try {
            GuiThread.join();
        } catch (Exception e) {
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);

        g2d.scale(scale, scale);
        if (hraciePole != null) {
            hraciePole.paint(g2d);
        } else {
            mainMenu.paint(g2d);
        }
    }
}